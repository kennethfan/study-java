package io.github.kennethfan.od.star1;

import io.github.kennethfan.od.common.ArrayUtil;

import java.util.Arrays;
import java.util.Stack;

public class N2 {

    public static void main(String[] args) {
        int n = 100;
        int[] arr = ArrayUtil.genArr(n, 150, 180);

        System.out.println("n=" + n);
        System.out.println("arr=" + Arrays.toString(arr));
        System.out.println("result=" + Arrays.toString(posResult(arr)));
    }

    /**
     * 在学校中，N个小朋友站成一队， 第i个小朋友的身高为height[i]，
     * 第i个小朋友可以看到的第一个比自己身高更高的小朋友j，那么j是i的好朋友(要求j > i)。
     * 请重新生成一个列表，对应位置的输出是每个小朋友的好朋友位置，如果没有看到好朋友，请在该位置用0代替。
     * 小朋友人数范围是 [0, 40000]。
     *
     * @param height
     * @return
     */
    public static int[] posResult(int[] height) {
        if (height == null || height.length <= 0) {
            throw new IllegalArgumentException("height is null or empty");
        }

        Stack<Integer> stack = new Stack<>();
        final int length = height.length;
        int[] result = new int[length];

        for (int i = length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && height[stack.peek()] <= height[i]) {
                stack.pop();
            }

            result[i] = stack.isEmpty() ? 0 : stack.peek() + 1;
            stack.push(i);
        }

        return result;
    }
}
