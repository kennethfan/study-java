package io.github.kennethfan.algorithm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Pattern {
    public static void main(String[] args) {
        String s = "aaaaaa";
        String p = "a*.";
        log.info("s {} {}match p {}", s, match(s, p) ? "" : "not ", p);

        s = "aab";
        p = "c*a*b";
        log.info("s {} {}match p {}", s, match(s, p) ? "" : "not ", p);

        s = "abcddd";
        p = ".*";
        log.info("s {} {}match p {}", s, match(s, p) ? "" : "not ", p);
    }

    /**
     * 给定一个字符串(s)和一个字符模式(p)。实现支持'.'和'*'的正则表达式匹配
     * '.'匹配任意单个字符
     * '*'匹配害个或多个前面的元索
     * 匹配应该覆盖整个字符串(s)，而不是部分字符串。
     * <p>
     * 示例1
     * s = "aa"
     * p = "a*"
     * 翰出：true
     * 解释：'*'代表可匹配零个或多个前面的元素，即可以匹配'a'。因此，重复'a'，一次宇符串可变为"aa"。
     *
     * @return
     */
    public static boolean match(String s, String p) {
        if (s == null || s.length() == 0 || p == null || p.length() == 0) {
            throw new IllegalArgumentException("s or p is null or blank");
        }


        return match(s, 0, p, 0);
    }

    private static boolean match(String s, int sBegin, String p, int pBegin) {
        if (pBegin == p.length()) {
            return sBegin == s.length(); // 字符串尾部
        }

        if (sBegin == s.length()) {
            return pBegin == p.length()  // 模式尾部
                    || (p.length() == pBegin + 3) && (p.charAt(pBegin + 2) == '*'); // 模式尾部还剩x*
        }

        char sc = s.charAt(sBegin);
        char pc = p.charAt(pBegin);

        boolean curMatch = sc == pc || pc == '.';
        if ((p.length() > pBegin + 1) && (p.charAt(pBegin + 1) == '*')) {
            return match(s, sBegin, p, pBegin + 2)  // 不匹配*
                    || (curMatch && match(s, sBegin + 1, p, pBegin)); // 匹配*后继续匹配
        }

        return curMatch && match(s, sBegin + 1, p, pBegin + 1);
    }
}
