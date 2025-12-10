package io.github.kennethfan.flink.wc;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;

@Slf4j
public class StreamFactory {

    public static DataStream<String> fromFile(String name, StreamExecutionEnvironment env, String path, int parallelism) {
        Path filePath = new Path(path);

//        System.out.println("Loading file from: " + filePath);
        log.info("Loading file from: {}", filePath);

//        TextLineFormat format = new TextLineFormat(StandardCharsets.UTF_8);
        TextLineFormat format = new TextLineFormat();

        FileSource<String> fileSource = FileSource
                .forRecordStreamFormat(format, filePath)
                .build();

        WatermarkStrategy<String> watermarkStrategy = WatermarkStrategy
                .<String>forMonotonousTimestamps()
                .withIdleness(Duration.ofSeconds(10));

        return env.fromSource(
                        fileSource,
                        watermarkStrategy,
                        name
                )
                .name(name)
                .setParallelism(parallelism);
    }
}
