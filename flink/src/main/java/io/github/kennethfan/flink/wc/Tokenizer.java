package io.github.kennethfan.flink.wc;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {

    @Override
    public void flatMap(String line, Collector<Tuple2<String, Integer>> collector) throws Exception {
        // 过滤空行
        if (line == null || line.trim().isEmpty()) {
            return;
        }

        // 转换为小写并分割单词
        String[] words = line.toLowerCase().split("\\W+");

        for (String word : words) {
            if (!word.isEmpty()) {
                collector.collect(Tuple2.of(word, 1));
            }
        }
    }
}
