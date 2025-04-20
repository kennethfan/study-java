package io.github.kennethfan.algorithm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockProfit {

    public static void main(String[] args) {
        int[] prices = {2, 4, 1};
        log.info("maxProfit of prices {} is {}", prices, maxProfitWithoutK(prices));

        prices = new int[]{3, 2, 6, 5, 0, 3};
        log.info("maxProfit of prices {} is {}", prices, maxProfitWithoutK(prices));


        prices = new int[]{2, 4, 1};
        log.info("maxProfit of prices {} is {}", prices, maxProfitWithK(prices, 2));

        prices = new int[]{3, 2, 6, 5, 0, 3};
        log.info("maxProfit of prices {} is {}", prices, maxProfitWithK(prices, 2));
    }

    /**
     * 给定一个数组，它的第i个元素是一支给定的股票在第i天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成k笔交易。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
     * <p>
     * 示例1:
     * 输入：[2,4,1]，k=2
     * 输出： 2
     * 解释：在第1天（股票价格=2）的时候买入，在第2天（股票价格=4）的时候卖出，这笔交易所能获得利润=4-2=2
     * <p>
     * 示例2：
     * 输入：[3,2,6,5,0,3]， k=2
     * 输出： 7
     * 解释：在第2天（股票价格=2）的时候买入，在第3天（股票价格=6）的时候卖出，这笔交易所能获得利润=6-2=4
     * 随后，在第5天（股票价格=9）的时候买入，在第6天（股票价格=3)的时候卖出，这笔交易所能获得利润=3-0=3
     */
    private static int maxProfitWithoutK(int[] prices) {
        // 此处假设k无限
        if (prices == null || prices.length == 0) {
            return 0;
        }

        if (prices.length == 1) {
            return 0;
        }

        // 最后状态是卖出时的收益
        int[] maxAfterSale = new int[prices.length];
        // 最后状态是买入时的收益
        int[] maxAfterBuy = new int[prices.length];

        // 第0天买入收益
        maxAfterBuy[0] = -prices[0];
        // 第1天买入收益
        maxAfterBuy[1] = Math.max(maxAfterBuy[0], -prices[1]);
        // 第1天卖出收益
        maxAfterSale[1] = Math.max(0, prices[1] - prices[0]);

        for (int i = 2; i < prices.length; i++) {
            // 当天手上是买入状态时的收益 = Max(前一天是买入状态收益，前一天是卖出状态收益-今天买入价格）
            maxAfterBuy[i] = Math.max(maxAfterBuy[i - 1], maxAfterSale[i - 1] - prices[i]);
            // 当天手上是卖出状态时的收益 = Max(前一天是卖出状态收益，前一天是买入状态收益+今天卖出的收益）
            maxAfterSale[i] = Math.max(maxAfterSale[i - 1], maxAfterBuy[i - 1] + prices[i]);
        }

        return Math.max(maxAfterBuy[prices.length - 1], maxAfterSale[prices.length - 1]);
    }

    private static int maxProfitWithK(int[] prices, int k) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        if (prices.length == 1) {
            return 0;
        }

        if (k < 1) {
            return 0;
        }

        // k过大，相当于交易次数不受限制
        if (2 * k <= prices.length - 1) {
            return maxProfitWithoutK(prices);
        }
        k = prices.length / 2;

        int[][] maxAfterSale = new int[k][prices.length];
        int[][] maxAfterBuy = new int[k][prices.length];


        maxAfterSale[0][0] = 0;
        maxAfterBuy[0][0] = 0;
//        maxAfterSale[1][0] =
//        maxAfterBuy[1][0] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            maxAfterBuy[0][i] = Math.max(maxAfterBuy[0][i - 1], -prices[i]);
            maxAfterSale[0][i] = Integer.MIN_VALUE;
        }

        for (int i = 1; i < k; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                maxAfterBuy[i][j] = Math.max(maxAfterBuy[i - 1][j - 1], maxAfterSale[i - 1][j - 1] - prices[j]);
                maxAfterSale[i][j] = Math.max(maxAfterSale[i - 1][j - 1], maxAfterBuy[i - 1][j - 1] + prices[j]);
            }
        }

        return Math.max(maxAfterBuy[k - 1][prices.length - 1], maxAfterSale[k - 1][prices.length - 1]);
    }
}
