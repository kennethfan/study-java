package io.github.kennethfan;

import lombok.extern.slf4j.Slf4j;
import org.simmetrics.Metric;
import org.simmetrics.metrics.StringMetrics;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

@Slf4j
public class SimilarityMain {

    public String add(Integer a, Integer b) {
        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException {

        String str1 = "This is a sentence. It is made of words";
        String str2 = "This sentence is similar. It has almost the same words";

        Metric metric = StringMetrics.cosineSimilarity();

        float result = metric.compare(str1, str2);
        log.info("Similarity is {}, s1={}, s2={}", result, str1, str2);


        str1 = "#/list/7: 4 schema violations found";
        str2 = "#/list/6: 8 schema violations found";
        result = StringMetrics.cosineSimilarity().compare(str1, str2); // 向量余弦
        log.info("cosineSimilarity is {}, s1={}, s2={}", result, str1, str2);
        result = StringMetrics.jaccard().compare(str1, str2); // 交集/并集
        log.info("jaccardSimilarity is {}, s1={}, s2={}", result, str1, str2);
        result = StringMetrics.levenshtein().compare(str1, str2); // 最小编辑距离
        log.info("levenshteinSimilarity is {}, s1={}, s2={}", result, str1, str2);

        log.info("match={}", Pattern.matches("^abc$", "test"));
    }
}
