package io.github.kennethfan.algorithm;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LongestCommonSubsequence {

    public static void main(String[] args) {
        String str1 = "abcde", str2 = "ace";

        log.info("longest common subsequence length of {} and {} is  {}", str1, str2, longestCommonSubsequence(str1, str2));
    }

    /**
     * 我们求两个字符串的LCS⻓度
     * 输入: str1 = "abcde", str2 = "ace" 输出: 3
     * 解释: 最⻓公共子序列是 "ace"，它的⻓度是 3
     *
     * @param s1
     * @param s2
     * @return
     */
    private static int longestCommonSubsequence(String s1, String s2) {
        if (s1 == null || s1.length() == 0) {
            return s2 == null ? 0 : s2.length();
        }

        if (s2 == null) {
            return s1.length();
        }

        return longestCommonSubsequence(s1, s1.length() - 1, s2, s2.length() - 1, new HashMap<>());
    }

    private static int longestCommonSubsequence(String s1, int i, String s2, int j, Map<Pair, Integer> calculated) {
        // 到底
        if (i == -1 || j == -1) {
            return 0;
        }
        Pair pair = new Pair(i, j);
        Integer value = calculated.get(pair);
        if (value != null) {
            return value;
        }

        if (s1.charAt(i) == s2.charAt(j)) {
            value = longestCommonSubsequence(s1, i - 1, s2, j - 1, calculated) + 1;
        } else {
            value = Math.max(longestCommonSubsequence(s1, i, s2, j - 1, calculated), longestCommonSubsequence(s1, i - 1, s2, j, calculated));
        }
        calculated.put(pair, value);

        return value;
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class Pair {
        private int i;
        private int j;
    }
}
