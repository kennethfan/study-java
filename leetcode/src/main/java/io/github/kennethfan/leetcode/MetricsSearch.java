package io.github.kennethfan.leetcode;

/**
 * Created by kenneth on 2023/5/30.
 */
public class MetricsSearch {
    /**
     * 问题：
     * 数组metrics有如下特点
     * 每一行从左到右数据升序
     * 每一列从上往下数据升序
     * 求目标值target在数组metrics中的位置
     * <p>
     * 思路：
     * 从数组的左下角开始
     * 1、如果当前元素比target小，则当前列可以忽略
     * 2、如果当前元素比target大，则当前行可以忽略
     * 3、如果当前元素和target相当，则找到
     *
     * @param metrics
     * @param target
     * @return
     */
    public static boolean search(int[][] metrics, int target) {
        if (metrics == null) {
            return false;
        }

        // 行
        final int m = metrics.length;
        if (m == 0) {
            return false;
        }
        // 列
        final int n = metrics[0].length;
        if (n == 0) {
            return false;
        }

        int i = m - 1;
        int j = 0;


        while (i >= 0 && j < n) {
            int value = metrics[i][j];

            // 相等
            if (value == target) {
                System.out.println("i=" + i + ", j=" + j);
                return true;
            }

            // 比target大，忽略当前行
            if (value > target) {
                i--;
                continue;
            }

            // 比target小，忽略当前列
            if (value < target) {
                j++;
                continue;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int[][] metrics = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30},
        };

        boolean result = search(metrics, 20);
        System.out.println("find=" + result);

        result = search(metrics, 17);
        System.out.println("find=" + result);

        result = search(metrics, 80);
        System.out.println("find=" + result);
        result = search(null, 80);
        System.out.println("find=" + result);

        result = search(metrics, 0);
        System.out.println("find=" + result);
        result = search(metrics, 1);
        System.out.println("find=" + result);

        result = search(metrics, 30);
        System.out.println("find=" + result);
        result = search(metrics, 31);
        System.out.println("find=" + result);
    }
}
