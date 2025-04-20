package io.github.kennethfan.od;

import java.util.HashMap;
import java.util.Map;

public class TestMain {


    public static void main(String[] args) {

        int[]  steps = new int[]{2, 2, 1, 1, 4};

        System.out.println(minStep(steps));
    }


    /**
     * 数组,
     * 值是可以跳的步数
     * @param steps
     *
     * f(x) = 1 + min(f(x+1), f(x+2), f(x+3), f(x+n)) n = [x]
     * @return
     */
    public static  int minStep(int[] steps) {
        Map<Integer, Integer> minSteps = new HashMap<>();

        int value = steps[0];
        if (value == 0) {
            return Integer.MAX_VALUE;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 1; i < value; i++) {
            min = Math.min(min, minStep(steps, i, minSteps));
        }

        return 1 + min;
    }


    public static int minStep(int[]steps, int index, Map<Integer, Integer> minSteps) {
        if (index == steps.length - 1) {
            return 0;
        }

        Integer result = minSteps.get(index);
        if (result != null) {
            return result;
        }

        int value = steps[index];
        if (value == 0) {
            return Integer.MAX_VALUE;
        }

        if (index + value >= steps.length -1) {
            minSteps.put(index, 1);
            return 1;
        }

        int min = Integer.MIN_VALUE;
        for (int i = 1; i < value; i++) {
            min = Math.min(min, minStep(steps, index + i, minSteps));
        }

        return 1 + min;
    }
}
