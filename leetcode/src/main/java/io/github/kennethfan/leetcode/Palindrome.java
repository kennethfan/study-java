package io.github.kennethfan.leetcode;

/**
 * Created by kenneth on 2023/6/1.
 */
public class Palindrome {
    /**
     * 给子字符串s，求最大回文字符串
     * 回文字符串特征：从左到右=从右到左
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        final int length = s.length();

        int[][] metrics = new int[length][length];

        int maxLen = 0;
        String maxS = null;
        for (int i = 0; i < length; i++) {
            for (int j = i; j < length; j++) {
                if (palindrome(s, i, j, metrics)) {
                    int len = j - i + 1;
                    if (len > maxLen) {
                        maxLen = len;
                        maxS = s.substring(i, j + 1);
                    }
                }
            }
        }

        return maxS;
    }

    private boolean palindrome(String s, int left, int right, int[][] metrics) {
        if (metrics[left][right] != 0) {
            return metrics[left][right] == 1;
        }

        boolean result;
        do {
            if (left - right == 0) {
                result = true;
                break;
            }

            if (left - right == 1) {
                result = s.charAt(left) == s.charAt(right);
            }

            result = s.charAt(left) == s.charAt(right) && palindrome(s, left + 1, right - 1, metrics);
        } while (false);

        metrics[left][right] = result ? 1 : -1;
        return result;
    }
}
