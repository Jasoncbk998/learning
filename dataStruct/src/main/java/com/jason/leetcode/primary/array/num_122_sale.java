package com.jason.leetcode.primary.array;

import java.util.ArrayList;

/**
 * @Classname num_122_sale
 * @Description TODO
 * @Date 2020/8/4 10:23 上午
 * @Created by jason
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 * <p>
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 * <p>
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: [7,1,5,3,6,4]
 * 输出: 7
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
 * 示例 2:
 * <p>
 * 输入: [1,2,3,4,5]
 * 输出: 4
 * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 *      注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
 *      因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 *
 *
 */
public class num_122_sale {
    public static int maxProfit1(int[] prices) {
        int i = 0;
        //波谷
        int valley = prices[0];
        //波峰
        int peak = prices[0];
        /**
         * 波谷买入,波峰卖出
         */
        int maxprofit = 0;
        /**
         * 思路
         * 通过while找到波峰和波谷  i>=i+1 即 i+1 为波谷
         * i<=i+1 即 i为波峰
         * 然后波峰-波谷=利润
         */
        while (i < prices.length - 1) {
            //7, 1, 5, 3, 6, 4
            while (i < prices.length - 1 && prices[i] >= prices[i + 1]) {
                i++;
                valley = prices[i];//找到波谷
            }
            while (i < prices.length - 1 && prices[i] <= prices[i + 1]) {
                i++;
                peak = prices[i];//波峰
                maxprofit += peak - valley;
            }
        }
        return maxprofit;
    }
    //7, 1, 5, 3, 6, 4
    public static  int maxProfit2(int[] prices) {
        int maxprofit = 0;
        ArrayList<Integer> list = new ArrayList<>();
        //nums[1]=1
        /**
         * 思路就是
         * 后一位大于前一位就做差 差值为利润,依次累加
         * i=1 1<7
         * i=2 5>1 利润4
         * i=3 3<5
         * i=4 6>3 利润3
         * i=5 4<6
         */
        //7, 1, 5, 3, 6, 4
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]){
                list.add(i);
                maxprofit += prices[i] - prices[i - 1];
            }
        }
        System.out.println("买入日期: \t"+list.toString());
        return maxprofit;
    }



    public static void main(String[] args) {
        int nums[] = {7, 1, 5, 3, 6, 4};
        int i = maxProfit2(nums);
        System.out.println(i);
    }

}
