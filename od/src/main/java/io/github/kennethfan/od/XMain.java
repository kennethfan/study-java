package io.github.kennethfan.od;

import java.util.*;

public class XMain {


    public static void main(String[] args) {
        int[] input = {2, 3, 6, 7};
        int target = 7;

        List<Stack<Integer>> result = bk(input, target, new HashSet<>());

        System.out.println("result=" + result);

    }


    private static List<Stack<Integer>> bk(int[] nums, int target, Set<Stack> visited) {
        final int length = nums.length;
        if (length == 0) {
            return new ArrayList<>();
        }
        List<Stack<Integer>> result = new ArrayList<>();


        Stack<Integer> branch = new Stack<>();
        int sum = 0;
        for (int i = 0; i < length; i++) {
            int j = i;
            while (true) {
                branch.push(nums[j]);
                sum += nums[j];
                if (sum == target) {
                    Stack copy = new Stack<>();
                    copy.addAll(branch);
                    result.add(copy);
                }

                if (sum >= target) {
                    if (branch.isEmpty()) {
                         break;
                    }

                    branch.pop();
                    sum -= nums[j];
                    j++;
                    if (j >= length) {
                        break;
                    }
                }
            }
        }

        return result;

    }

}
