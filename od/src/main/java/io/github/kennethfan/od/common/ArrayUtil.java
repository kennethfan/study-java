package io.github.kennethfan.od.common;

public class ArrayUtil {
    public static int[] genArr(int length, int min, int max) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be greater than 0");
        }

        int[] result = new int[length];

        for (int i = 0; i < length; i++) {
            result[i] = (int) (Math.random() * (max - min + 1) + min);
        }

        return result;
    }

    public static int sum(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("arr is null or empty");
        }

        int result = 0;
        for (int i : arr) {
            result += i;
        }

        return result;
    }
}
