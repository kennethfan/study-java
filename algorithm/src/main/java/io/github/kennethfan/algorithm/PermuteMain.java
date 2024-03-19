package io.github.kennethfan.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class PermuteMain {

    public static void main(String[] args) {

        for (int i = 1; i <= 5; i++) {
            int[] nums = new int[i];
            for (int j = 0; j < i; ) {
                nums[j] = ++j;
            }
            List<List<Integer>> result = permute(nums);
            log.info("{} permute total={} full trace is {}", nums, result.size(), result);
        }
    }

    /**
     * 给定n个不同的数字，求n个数字的排列组合
     *
     * @param nums
     * @return
     */
    private static List<List<Integer>> permute(int[] nums) {
        return permute(nums, new HashSet<>());

    }

    private static List<List<Integer>> permute(int[] nums, Set<Integer> visited) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }

        List<List<Integer>> result = new ArrayList<>();
        for (int num : nums) {
            // 已经遍历过
            if (visited.contains(num)) {
                continue;
            }

            visited.add(num);
            List<List<Integer>> subTraces = permute(nums, visited);
            if (subTraces.isEmpty()) {
                List<Integer> fullTrace = new LinkedList<>();
                fullTrace.add(num);
                result.add(fullTrace);
                visited.remove(num);
                continue;
            }

            for (List<Integer> subTrace : subTraces) {
                List<Integer> fullTrace = new LinkedList<>();
                fullTrace.add(num);
                fullTrace.addAll(subTrace);
                result.add(fullTrace);
            }
            visited.remove(num);
        }

        return result;
    }
}
