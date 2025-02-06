package io.github.kennethfan.od.star1;

import io.github.kennethfan.od.common.ArrayUtil;

import java.util.Arrays;

public class N9 {


    public static void main(String[] args) {
        int[] input = ArrayUtil.genArr(30, 0, 10000);
        int step = 18;
        int left = 500;
        int[] result = jump(input, 1, 10);

        System.out.println("input=" + Arrays.toString(input));
        System.out.println("step=" + step);
        System.out.println("left=" + left);
        System.out.println("result=" + Arrays.toString(result));

    }

    /**
     * 给一个正整数列 nums，一个跳数 jump，及幸存数量 left。运算过程为：从索引为0的位置开始向后跳，中间跳过 J 个数字，命中索引为J+1的数字，该数被敲出，并从该点起跳，以此类推，直到幸存left个数为止。然后返回幸存数之和。
     * 约束：
     * 1）0是第一个起跳点。
     * 2）起跳点和命中点之间间隔 jump 个数字，已被敲出的数字不计入在内。
     * 3）跳到末尾时无缝从头开始（循环查找），并可以多次循环。
     * 4）若起始时 left>len(nums) 则无需跳数处理过程。
     * <p>
     * /**
     * * nums: 正整数数列，长度范围 [1,10000]
     * * jump: 跳数，范围 [1,10000]
     * * left: 幸存数量，范围 [0,10000]
     * * return: 幸存数之和
     */
    public static int[] jump(int[] nums, int step, int left) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        if (step <= 0 || left <= 0) {
            throw new IllegalArgumentException("");
        }

        int length = nums.length;
        int[] copy = new int[length];
        System.arraycopy(nums, 0, copy, 0, length);
        if (left > length) {
            return copy;
        }

        int next = 0;
        while (left <= length) {
            next = (next + step) % length;
            length--;
            if (length > next) {
                System.arraycopy(copy, next + 1, copy, next, length - next);
            }
        }

        int result[] = new int[left];
        System.arraycopy(copy, 0, result, 0, left);
        return result;
    }
}
