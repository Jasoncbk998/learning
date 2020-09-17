package com.lovedata.pro._08_动态规划;

/**
 * https://leetcode-cn.com/problems/maximum-subarray/
 * 最大连续子序列
 */
public class MaxSubArray {
    public static void main(String[] args) {
        int[] array = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
//        int[] array = new int[]{-2, -1, -3};
        System.out.println("最大值:"+maxSubArray1(array));
    }

    /**
     * 给定数组
     * 给定一个长度为 n 的整数序列，求它的最大连续子序列和
     * 比如 –2、1、–3、4、–1、2、1、–5、4 的最大连续子序列和是 4 + (–1) + 2 + 1 = 6
     * 找到以每个元素结尾的最大连续子序列
     * 如: 必须以该元素结尾,如果发现有一个子序列为正值,则下次求和直接与结尾元素求和即可
     * -2  ->  -2  包含元素:-2
     * 1   ->  1   包含元素:1
     * -3  ->  -2  包含元素:1+(-3)
     * 4   ->  2   包含元素:1+(-3)+4=2
     * -1  ->  1   包含元素:1-3+4-1=1
     * ...
     * ...
     *
     * @param nums
     * @return
     */
    static int maxSubArray2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int dp = nums[0];
        int max = dp;//最大
        //-2, 1, -3, 4, -1, 2, 1, -5, 4
        for (int i = 1; i < nums.length; i++) {
            if (dp <= 0) {
                dp = nums[i];
            } else {
                dp = dp + nums[i];
            }
            max = Math.max(dp, max);

        }
        return max;
    }

    static int maxSubArray1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < dp.length; i++) {
            int prev = dp[i - 1];
            if (prev <= 0) {
                dp[i] = nums[i];
            } else {
                dp[i] = prev + nums[i];
            }
            max = Math.max(dp[i], max);
            System.out.println("子序列:"+dp[i]);
        }
        return max;
    }
}
