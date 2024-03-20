package io.github.kennethfan.algorithm;

import lombok.extern.slf4j.Slf4j;

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

        return longestCommonSubsequence(s1, s1.length() - 1, s2, s2.length() - 1);
    }

    private static int longestCommonSubsequence(String s1, int i, String s2, int j) {
        // 到底
        if (i == -1 || j == -1) {
            return 0;
        }

        if (s1.charAt(i) == s2.charAt(j)) {
            return longestCommonSubsequence(s1, i - 1, s2, j - 1) + 1;
        } else {
            return Math.max(longestCommonSubsequence(s1, i, s2, j - 1), longestCommonSubsequence(s1, i - 1, s2, j));
        }
    }
}
