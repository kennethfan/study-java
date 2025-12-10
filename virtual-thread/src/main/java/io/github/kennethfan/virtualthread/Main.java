package io.github.kennethfan.virtualthread;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class Main {
    // 创建者ID数组 (完整列表)
    private static final List<Long> CREATOR_IDS = new ArrayList<>();

    // 配置常量
    private static final String HOST = System.getProperty("host", "localhost");
    private static final int THREAD_COUNT = Integer.parseInt(System.getProperty("threadCount", "3000"));
    private static final String CREATOR_IDS_PATH = System.getProperty("creatorIdsPath");
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);
    private static final int WORKERS_PERHTTP_CLIENT = Integer.parseInt(System.getProperty("workersPerHttpClient", "50"));

    // 统计计数器
    private static final AtomicLong totalRequests = new AtomicLong(0);
    private static final AtomicLong successfulRequests = new AtomicLong(0);
    private static final AtomicLong failedRequests = new AtomicLong(0);
    private static final List<HttpClient> SHARED_CLIENT_LIST = new ArrayList<>();

    // 日期格式化器
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        System.setProperty("jdk.httpclient.connectionPoolSize", "500");
        loadCreatorIds();
        initHttpClients();

        log.info("🚀 开始虚拟线程并发基准测试");
        log.info("📊 配置: " + THREAD_COUNT + " 个虚拟线程");
        log.info("🎯 目标主机: " + HOST);
        log.info("📈 监控日志: benchmark_api.log");

        // 启动统计信息线程
        startStatisticsReporter();

        // 使用虚拟线程执行器
        try (ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor()) {

            // 创建虚拟线程执行任务
            for (int i = 0; i < THREAD_COUNT; i++) {
                final int workerId = i + 1;
                virtualThreadExecutor.submit(() -> workerTask(workerId));
            }

            // 让程序持续运行（实际使用时可以添加退出条件）
            Thread.sleep(Long.MAX_VALUE);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("⏹️  程序被中断", e);
        } catch (Exception e) {
            log.error("❌ 发生错误: ", e);
        } finally {
            closeHttpClients();
            printFinalStatistics();
        }
    }

    private static void loadCreatorIds() {
        if (StringUtils.isBlank(CREATOR_IDS_PATH)) {
            log.error("未指定创建者ID文件路径");
            System.exit(1);
        }

        try {
            List<String> lines = Files.readAllLines(Path.of(CREATOR_IDS_PATH), StandardCharsets.UTF_8);
            for (String line : lines) {
                CREATOR_IDS.add(Long.parseLong(StrUtil.trim(line)));
            }
        } catch (Exception e) {
            log.error("无法加载创建者ID文件: " + CREATOR_IDS_PATH, e);
            System.exit(1);
        }
    }

    private static void initHttpClients() {
        int count = (THREAD_COUNT / WORKERS_PERHTTP_CLIENT);
        for (int i = 0; i <= count; i++) {
            SHARED_CLIENT_LIST.add(HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .version(HttpClient.Version.HTTP_2)
                    .executor(Executors.newVirtualThreadPerTaskExecutor())
                    .build());
        }
    }

    private static void closeHttpClients() {
        if (CollectionUtils.isEmpty(SHARED_CLIENT_LIST)) {
            return;
        }

        SHARED_CLIENT_LIST.forEach(HttpClient::close);
    }

    /**
     * 虚拟线程工作任务
     */
    private static void workerTask(int workerId) {
        logMessage("启动虚拟工作线程 #" + workerId);

        while (!Thread.currentThread().isInterrupted()) {
            try {
                // 发送HTTP请求
                sendHttpRequest(workerId);
            } catch (Exception e) {
                logError("工作线程#" + workerId + " 发生错误: " + e.getMessage());
                try {
                    Thread.sleep(1000); // 错误时等待1秒
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    /**
     * 发送HTTP请求（使用虚拟线程的优势：阻塞操作会自动挂起）
     */
    private static void sendHttpRequest(int workerId) {
        totalRequests.incrementAndGet();

        Long creatorId = getRandomCreatorId();
        String url = "https://" + HOST + "/service/getCreator/" + creatorId;

        long startTime = System.currentTimeMillis();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(REQUEST_TIMEOUT)
                    .GET()
                    .header("User-Agent", "VirtualThread-Benchmark/1.0")
                    .build();

            HttpResponse<String> response = SHARED_CLIENT_LIST.get(workerId / WORKERS_PERHTTP_CLIENT).send(
                    request, HttpResponse.BodyHandlers.ofString()
            );

            long duration = System.currentTimeMillis() - startTime;

            // 记录成功请求
            successfulRequests.incrementAndGet();
            logMessage(String.format(
                    "工作线程#%d, url=%s, HTTP状态=%d, 耗时=%dms",
                    workerId, url, response.statusCode(), duration
            ));

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            failedRequests.incrementAndGet();
            logError(String.format(
                    "工作线程#%d, url=%s, 错误: %s, 耗时=%dms",
                    workerId, url, e.getMessage(), duration
            ));
        }
    }

    /**
     * 获取随机创建者ID（线程安全）
     */
    private static Long getRandomCreatorId() {
        int index = ThreadLocalRandom.current().nextInt(0, CREATOR_IDS.size());
        return CREATOR_IDS.get(index);
    }

    /**
     * 启动统计信息报告器
     */
    private static void startStatisticsReporter() {
        Thread statisticsThread = Thread.ofVirtual().unstarted(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(10000); // 每10秒报告一次
                    printStatistics();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        statisticsThread.start();
    }

    /**
     * 打印实时统计信息
     */
    private static void printStatistics() {
        long total = totalRequests.get();
        long success = successfulRequests.get();
        long errors = failedRequests.get();

        double successRate = total > 0 ? (success * 100.0) / total : 0.0;

        String stats = String.format(
                "📊 实时统计 - 总请求: %d, 成功: %d, 错误: %d, 成功率: %.2f%%",
                total, success, errors, successRate
        );

        log.info(stats);
        logMessage(stats);
    }

    /**
     * 打印最终统计信息
     */
    private static void printFinalStatistics() {
        log.info("\n" + "=".repeat(50));
        printStatistics();
        log.info("✅ 程序执行完成");
    }

    /**
     * 记录日志消息
     */
    private static void logMessage(String message) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String logEntry = String.format("[%s] %s", timestamp, message);

        log.info(logEntry);
    }

    /**
     * 记录错误消息
     */
    private static void logError(String errorMessage) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        String logEntry = String.format("[%s] ❌ ERROR: %s", timestamp, errorMessage);

        log.error(logEntry);
    }
}