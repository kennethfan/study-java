package io.github.kennethfan.od.star1;

import io.github.kennethfan.od.common.ArrayUtil;

import java.util.Arrays;

public class N1 {

    public static void main(String[] args) {
        int n = 10;
        int[] arr = ArrayUtil.genArr(100, 0, 1000);

        System.out.println("arr=" + Arrays.toString(arr));
        System.out.println("n=" + n);
        System.out.println("sum=" + getSum(arr, n));
    }


    /**
     * 给定一个数组，编写一个函数来计算它的最大N个数与最小N个数的和。你需要对数组进行去重。
     * 说明：
     * *数组中数字范围[0, 1000]
     * *最大N个数与最小N个数不能有重叠，如有重叠，输入非法返回-1
     * *输入非法返回-1
     *
     * @param arr
     * @param n
     * @return
     */
    public static int getSum(int[] arr, int n) {
        if (arr == null || n <= 0) {
            return -1;
        }

        if (n * 2 > arr.length) {
            return -1;
        }

        // 桶排序
        boolean[] bucket = new boolean[1001];
        for (int i : arr) {
            if (i < 0 || i > 1000) {
                return -1;
            }

            bucket[i] = true;
        }
        System.out.println("bucket=" + Arrays.toString(bucket));

        int maxMin = 0;

        // 累加最小N个数
        int index = 0;
        int sum = 0;
        for (int i = 0; i <= 1000; i++) {
            if (bucket[i]) {
                continue;
            }

            sum += i;
            if (++index == n) {
                maxMin = i;
                break;
            }
        }

        // 累加最大N个数
        index = 0;
        for (int i = 1000; i >= 0; i--) {
            if (bucket[i]) {
                continue;
            }

            // 判断最小最大N个数是否重叠
            if (i <= maxMin) {
                return -1;
            }

            sum += i;
            if (++index == n) {
                break;
            }
        }

        return sum;
    }
}
