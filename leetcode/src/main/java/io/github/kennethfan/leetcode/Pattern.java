package io.github.kennethfan.leetcode;

/**
 * Created by kenneth on 2023/6/2.
 */
public class Pattern {

    /**
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     * <p>
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素
     * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/regular-expression-matching
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * <p>
     * <p>
     * f(i, j) = (si==pi) && f(i-i, j-1)
     *
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatch(String s, String p) {
        final int sLen = s.length();
        final int pLen = p.length();

        boolean[][] matches = new boolean[sLen + 1][pLen + 1];
        // matches[i][j] 表示s前i个字符和p前j个字符是否匹配
        matches[0][0] = true;

        for (int i = 0; i <= sLen; i++) {
            for (int j = 1; j <= pLen; j++) {
                char ch = p.charAt(j);
                if (ch == '*') {
                    // 匹配0次
                    matches[i][j] = matches[i][j - 2];
                    if (matches(s, p, i, j - 1)) {
                        matches[i][j] |= matches[i - 1][j];
                    }
                } else if (ch == '.') {
                    matches[i][j] = matches[i - 1][j - 1];
                } else {
                    matches[i][j] = ch == s.charAt(i) && matches[i - 1][j - 1];
                }
            }
        }

        return matches[sLen - 1][pLen - 1];
    }

    public static boolean matches(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }

        if (p.charAt(j - 1) == '.') {
            return true;
        }

        return s.charAt(i - 1) == p.charAt(j - 1);
    }

    public static void main(String[] args) {
        System.out.println(isMatch("aa", "a*"));
    }
}
