package io.github.kennethfan.flink.wc;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

@Slf4j
public class BatchWorldCount {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: BatchWordCount <input> <output> [parallelism]");
            System.err.println("Example: BatchWordCount input.txt output.txt 4");
            System.exit(1);
        }

        final String inputPath = args[0];
        final String outputPath = args[1];
        int parallelism = 1;
        if (args.length > 2) {
            parallelism = Integer.parseInt(args[2]);
        }

        // 1. 创建流批一体执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 明确指定批处理模式
        env.setRuntimeMode(RuntimeExecutionMode.BATCH);

        // 设置并行度和作业名称
        env.setParallelism(parallelism);
        env.getConfig().enableObjectReuse();

        // 2. 使用最新的FileSource API读取输入数据
        DataStream<String> lines = StreamFactory.fromFile("fileSource", env, inputPath, parallelism);

        // 3. 定义处理逻辑
        SingleOutputStreamOperator<Tuple2<String, Integer>> counts = lines
                .flatMap(new Tokenizer())
                .name("Tokenizer")
                .setParallelism(parallelism)
                .keyBy(value -> value.f0)
                .reduce(new SumReducer())
                .name("SumReducer")
                .setParallelism(parallelism)
                .returns(TypeInformation.of(new TypeHint<Tuple2<String, Integer>>() {}));

        // 4. 输出结果到文件
        counts.writeAsText(outputPath)
                .name("FileSink")
                .setParallelism(parallelism);

        // 5. 执行作业
        try {
            System.out.println("Starting Flink WordCount job...");
            System.out.println("Input path: " + inputPath);
            System.out.println("Output path: " + outputPath);
            System.out.println("Parallelism: " + parallelism);

            env.execute("Flink Batch WordCount Example");
            System.out.println("Job completed successfully!");

        } catch (Exception e) {
            System.err.println("Job execution failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
