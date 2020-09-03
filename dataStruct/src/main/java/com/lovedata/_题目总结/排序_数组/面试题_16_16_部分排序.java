package com.lovedata._题目总结.排序_数组;

/**
 * https://leetcode-cn.com/problems/sub-sort-lcci/
 * { 1, 5, 4, 3, 2, 6, 7 }
 * 这其中数组部分数据排序,使数组整体有序
 * 找出这个数据区间
 */
public class 面试题_16_16_部分排序 {
    public int[] subSort(int[] nums) {
        if (nums.length == 0) return new int[]{-1, -1};

        // 从左扫描到右寻找逆序对（正序：逐渐变大）
        int max = nums[0];
        // 用来记录最右的那个逆序对位置
        int r = -1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] >= max) {
                //更新最大值
                max = nums[i];
            } else {
                //记录逆序对
                r = i;
            }
        }

        // 提前结束
        if (r == -1) return new int[]{-1, -1};

        // 从右扫描到左寻找逆序对（正序：逐渐变小）
        int min = nums[nums.length - 1];
        // 用来记录最左的那个逆序对位置
        int l = -1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] <= min) {
                min = nums[i];
            } else {
                l = i;
            }
        }

        return new int[]{l, r};
    }
}
