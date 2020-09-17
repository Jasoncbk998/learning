package com.lovedata.pro._08_动态规划;

/**
 * leetcode_300
 * https://leetcode-cn.com/problems/longest-increasing-subsequence/
 * <p>
 * 给定一个无序的整数序列，求出它最长上升子序列的长度（要求严格上升）
 * <p>
 * 比如 [10, 2, 2, 5, 1, 7, 101, 18] 的最长上升子序列是 [2, 5, 7, 101]、[2, 5, 7, 18]，长度是 4
 * <p>
 * 最大上升子序列,可以不用连续
 * <p>
 * dp(i) 是以 nums[i] 结尾的最长上升子序列的长度，i ∈ [0, nums.length)
 * 以 nums[0] 10 结尾的最长上升子序列是 10，所以 dp(0) = 1
 * 以 nums[1] 2 结尾的最长上升子序列是 2，所以 dp(1) = 1
 * 以 nums[2] 2 结尾的最长上升子序列是 2，所以 dp(2) = 1
 * 以 nums[3] 5 结尾的最长上升子序列是 2、5，所以 dp(3) = dp(1) + 1 = dp(2) + 1 = 2
 * 以 nums[4] 1 结尾的最长上升子序列是 1，所以 dp(4) = 1
 * 以 nums[5] 7 结尾的最长上升子序列是 2、5、7，所以 dp(5) = dp(3) + 1 = 3
 * 以 nums[6] 101 结尾的最长上升子序列是 2、5、7、101，所以 dp(6) = dp(5) + 1 = 4
 * 以 nums[7] 18 结尾的最长上升子序列是 2、5、7、18，所以 dp(7) = dp(5) + 1 = 4
 */
public class LIS {
    public static void main(String[] args) {
        System.out.println("最大连续:"+lengthOfLIS1(new int[]{10, 2, 2, 5, 1, 7, 101, 18}));
    }

    /**
     * 牌顶
     */
    static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        // 牌堆的数量
        int len = 0;
        // 牌顶数组
        int[] top = new int[nums.length];
        // 遍历所有的牌
        for (int num : nums) {
            int begin = 0;
            int end = len;
            while (begin < end) {
                int mid = (begin + end) >> 1;
                if (num <= top[mid]) {
                    end = mid;
                } else {
                    begin = mid + 1;
                }
            }
            // 覆盖牌顶
            top[begin] = num;
            // 检查是否要新建一个牌堆
            if (begin == len) len++;
        }
        return len;
    }

    /**
     * 牌顶
     */
    static int lengthOfLIS2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        // 牌堆的数量
        int len = 0;
        // 牌顶数组
        int[] top = new int[nums.length];
        // 遍历所有的牌
        for (int num : nums) {
            int j = 0;
            while (j < len) {
                // 找到一个>=num的牌顶
                if (top[j] >= num) {
                    top[j] = num;
                    break;
                }
                // 牌顶 < num
                j++;
            }
            if (j == len) { // 新建一个牌堆
                len++;
                top[j] = num;
            }
        }
        return len;
    }

    /**
     * 动态规划
     * 10, 2, 2, 5, 1, 7, 101, 18
     */
    static int lengthOfLIS1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        //假设:3,2,4,5,6
        //我要算6的最大上升子序列,就需要判断3,2,4,5 所以j<i
        for (int i = 1; i < dp.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] <= nums[j]) {
                 //10, 2, 2, 5, 1, 7, 101, 18
                    //10>2 所以直接执行跳过
                    continue;
                }
                //i>j
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }

            max = Math.max(dp[i], max);
            System.out.println(dp[i]);
        }
        return max;
    }

}
