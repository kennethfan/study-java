package io.github.kennethfan.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MinDistance {

    public static void main(String[] args) {
        String s1 = "horse", s2 = "ros";
        log.info("min distance for {} and {} is {}", s1, s2, minDistance(s1, s2));

        s1 = "intention";
        s2 = "execution";
        log.info("min distance for {} and {} is {}", s1, s2, minDistance(s1, s2));
    }

    /**
     * 给定两个字符串s1和s2，计算出将s1转换成s2所使用的最少操作数。
     * 你可以对一个字符串进行如下三种操作：
     * 1. 插入一个字符
     * 2. 删除一个字符
     * 3. 替换一个字符
     * <p>
     * 示例1：
     * 输入：s1 = "horse", s2 ="ros"
     * 输出：3
     * 解释：
     * horse -＞ rorse('h'替换为'r'）
     * rorse ->rose（删除'r')
     * rose ->ros（删除'e'）
     * <p>
     * 示例2：
     * 输入：s1 = "intention" s2="execution"
     * 输出：5
     * 解释：
     * intention -> inention （删除't'）
     * inention -> enention （'i'替换为'e')
     * enention -> exention ('n'替换为'x'）
     * exention -> exection (将'n'替换为'c')
     * exection -> execution （插入'u'）
     * <p>
     * 思路：
     * 编辑距离问题就是给我们两个字符串s1和s2，只能用三种操作，让我们把s1变成s2，求最少的操作数。
     * 需要明确的是，不管是把s1变成s2 还是反过来，结果都是一样的，所以后文就以s1变成s2举例。
     * 解决两个字符串的动态规划问题，一般都是用两个指针i,j分别指向两个字符串的最后，然后一步步往前走，缩小问题的规模。
     *
     * @param s1
     * @param s2
     * @return
     */
    private static int minDistance(String s1, String s2) {
        if (s1 == null || s1.length() == 0) {
            return s1 == null ? 0 : s2.length();
        }
        if (s2 == null || s2.length() == 0) {
            return s1.length();
        }

        int i = s1.length() - 1;
        int j = s2.length() - 1;


        return minDistance(s1, i, s2, j, new HashMap<>());
    }

    private static int minDistance(String s1, int i, String s2, int j, Map<Integer, Map<Integer, Integer>> cacluted) {
        if (i < 0) {
            return j + 1;
        }

        if (j < 0) {
            return i + 1;
        }

        if (cacluted.containsKey(i) && cacluted.get(i).containsKey(j)) {
            return cacluted.get(i).get(j);
        }

        int distance;
        if (s1.charAt(i) == s2.charAt(j)) {
            distance = minDistance(s1, i - 1, s2, j - 1, cacluted);
        } else {
            int insert = minDistance(s1, i, s2, j - 1, cacluted) + 1;
            int delete = minDistance(s1, i - 1, s2, j, cacluted) + 1;
            int replace = minDistance(s1, i - 1, s2, j - 1, cacluted) + 1;
            distance = Math.min(Math.min(insert, delete), replace);
        }

        cacluted.computeIfAbsent(i, integer -> new HashMap<>()).put(j, distance);
        return distance;
    }
}
