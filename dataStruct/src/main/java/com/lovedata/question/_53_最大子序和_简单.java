package com.lovedata.question;

/**
 * @Classname _53_最大子序和_简单
 * @Description TODO
 * @Date 2020/9/19 10:02 上午
 * @Created by jason
 */
public class _53_最大子序和_简单 {
    /**
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * <p>
     * 示例:
     * <p>
     * 输入: [-2,1,-3,4,-1,2,1,-5,4]
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int sum = maxSubArray(nums);
        System.out.println(sum);
    }

    public static int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            //通过这种办法实现连续,妙哉
            /**
             * x是当前值,pre用来做加和
             * 比如,-2,1,-3
             * 先取出-2,此时pre为-2
             * 取出1,此时比较最大值 pre+x<x 即 -1<1 把最大值1赋值给pre,然后pre与maxAns进行比较,取最大值,赋值给maxAns
             * 取出-3,pre通过比较后为 -2,但是与maxAns进行取最大值还是1,则最大值从新开始计算,由此断开
             * 通过这种办法,保证了连续和最大子序和
             */
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;

    }


}
