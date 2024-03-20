package io.github.kennethfan.algorithm;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StoneGame {

    public static void main(String[] args) {
        int[] piles = {3, 9, 1, 2};

        log.info("stoneGame for {} is {}", piles, stoneGame(piles));
        log.info("stoneGame2 for {} is {}", piles, stoneGame2(piles));
    }


    /**
     * 你和你的朋友面前有一排石头堆，用一个数组piles表示，piles[i] 表示第i堆石子有多少个。你们轮流拿石头，一次拿一堆，但是只能拿走最左边或者最右边的石头堆。所有石头被拿完后，谁拥有的石头多，谁获胜。
     * 石头的堆数可以是任意正整数，石头的总数也可以是任意正整数，这样就能 打破先手必胜的局面了。比如有三堆石头 piles = [1, 100, 3] ，先手不管拿1还是3，能够决定胜负的100都会被后手拿走，后手会获胜。
     * 假设两人都很聪明，请你设计一个算法，返回先手和后手的最后得分(石头 总数)之差。比如上面那个例子，先手能获得 4 分，后手会获得 100 分，你 的算法应该返回 -96。
     * <p>
     * 思路
     * dp[i][j].fir表示，对于piles[i...j]这部分石头堆，先手能获得的最高分数。dp[i][j].sec表示，对于piles[i...j]这部分石头堆，后手能获得的最高分数。
     * 举例理解一下，假设 piles = [3, 9, 1, 2]，索引从0开始
     * dp[0][1].fir = 9 意味着:面对石头堆 [3, 9]，先手最终能够获得9分。 dp[1][3].sec =2意味着:面对石头堆 [9, 1, 2]，后手最终能够获得2分。
     * <p>
     * 选择左边
     * left = piles[i] + dp[i+1][j].sec
     * 选择右边
     * right = piles[j] + dp[i][j-1].sec
     * dp[i][j].fir = max(left, right)
     * left > right
     * dp[i][j].sec = dp[i+1][j].first
     * left < right
     * dp[i][j].sec = dp[i][j-1].first
     *
     * @param piles
     * @return
     */
    private static int stoneGame(int[] piles) {
        if (piles == null || piles.length == 0) {
            return 0;
        }
        if (piles.length == 1) {
            return piles[0];
        }

        int first = maxStoneGameFirst(piles, 0, piles.length - 1);
        int second = maxStoneGameSecond(piles, 0, piles.length - 1);

        return first - second;
    }

    private static int maxStoneGameFirst(int[] piles, int i, int j) {
        if (j - i == 1) {
            return Math.max(piles[i], piles[j]);
        }


        int left = piles[i] + maxStoneGameSecond(piles, i + 1, j);
        int right = piles[j] + maxStoneGameSecond(piles, i, j - 1);

        return Math.max(left, right);
    }

    private static int maxStoneGameSecond(int[] piles, int i, int j) {
        if (j - i == 1) {
            return Math.min(piles[i], piles[j]);
        }

        int leftFirst = piles[i] + maxStoneGameSecond(piles, i + 1, j);
        int rightFirst = piles[j] + maxStoneGameSecond(piles, i, j - 1);
        if (leftFirst > rightFirst) {
            return maxStoneGameFirst(piles, i + 1, j);
        }
        return maxStoneGameFirst(piles, i, j - 1);
    }

    private static int stoneGame2(int[] piles) {
        if (piles == null || piles.length == 0) {
            return 0;
        }
        if (piles.length == 1) {
            return piles[0];
        }
        Map<Pair, Integer> firstCache = new HashMap<>();
        Map<Pair, Integer> secondCache = new HashMap<>();

        final int length = piles.length;

        // 初始化间隔为0的情况
        for (int i = 0; i < length; i++) {
            Pair pair = new Pair(i, i);
            firstCache.put(pair, piles[i]);
            secondCache.put(pair, 0);
        }

        // 初始化间隔为step的情况
        for (int step = 1; step < length; step++) {
            for (int begin = 0; begin < length - step; begin++) {
                int i = begin;
                int j = begin + step;
                Pair pair = new Pair(i, j);

                int left = piles[i] + secondCache.get(new Pair(i + 1, j));
                int right = piles[j] + secondCache.get(new Pair(i, j - 1));
                if (left > right) {
                    firstCache.put(pair, left);
                    secondCache.put(pair, firstCache.get(new Pair(i + 1, j)));
                } else {
                    firstCache.put(pair, right);
                    secondCache.put(pair, firstCache.get(new Pair(i, j - 1)));
                }
            }
        }

        Pair pair = new Pair(0, length - 1);
        return firstCache.get(pair) - secondCache.get(pair);
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class Pair {
        private int i;
        private int j;
    }

}

