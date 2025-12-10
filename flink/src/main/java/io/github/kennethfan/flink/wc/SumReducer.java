package io.github.kennethfan.flink.wc;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public class SumReducer implements ReduceFunction<Tuple2<String, Integer>> {
    @Override
    public Tuple2<String, Integer> reduce(Tuple2<String, Integer> v1, Tuple2<String, Integer> v2) throws Exception {
        return Tuple2.of(v1.f0, v1.f1 + v2.f1);
    }
}
