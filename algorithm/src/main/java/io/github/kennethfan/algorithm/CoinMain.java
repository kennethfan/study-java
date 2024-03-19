package io.github.kennethfan.algorithm;


import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CoinMain {

    public static void main(String[] args) {
        int[] coins = {1, 2, 5, 10};

        for (int amount = 0; amount <= 30; amount++) {
            log.info("amount={}, need coins={}", amount, minCombine(amount, coins));
        }
    }

    public static int minCombine(int amount, int[] coins) {
        return minCombine(amount, coins, new HashMap<>());
    }

    /**
     * 给你 k 种面值的硬币，面值分别为 c1, c2 ... ck ，每种硬 币的数量无限，再给一个总金额 amount ，问你最少需要几枚硬币凑出这个 金额，如果不可能凑出，算法返回 -1
     *
     * @param amount
     * @param coins
     * @param calculated
     */
    private static int minCombine(int amount, int[] coins, Map<Integer, Integer> calculated) {
        Integer cached = calculated.get(amount);
        if (cached != null) {
            return cached;
        }

        if (amount < 0) {
            return -1;
        }

        if (amount == 0) {
            return 0;
        }

        if (coins == null || coins.length == 0) {
            return -1;
        }

        int min = -1;
        for (int coin : coins) {
            int remain = minCombine(amount - coin, coins, calculated);
            if (remain == -1) {
                continue;
            }
            if (min == -1) {
                min = remain + 1;
            } else {
                min = Math.min(min, remain + 1);
            }
        }

        calculated.put(amount, min);
        return min;
    }
}
