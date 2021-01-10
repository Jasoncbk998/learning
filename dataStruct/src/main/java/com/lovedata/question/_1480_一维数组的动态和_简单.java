package com.lovedata.question;

import java.util.Arrays;

/**
 * @Classname _1480_一维数组的动态和_简单
 * @Description TODO
 * @Date 2021/1/10 下午10:38
 * @Created by jason
 */
public class _1480_一维数组的动态和_简单 {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        int[] ints = runningSum(nums);
        System.out.println(Arrays.toString(ints));
    }


    public static int[] runningSum(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1];
        }
        return nums;


    }
}
