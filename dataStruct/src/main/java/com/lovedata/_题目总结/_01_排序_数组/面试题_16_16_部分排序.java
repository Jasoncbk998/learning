package com.lovedata._题目总结._01_排序_数组;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/sub-sort-lcci/
 * { 1, 5, 4, 3, 2, 6, 7 }
 * 这其中数组部分数据排序,使数组整体有序
 * 找出这个数据区间
 */
public class 面试题_16_16_部分排序 {


    public static void main(String[] args) {
        int[] nums = {1, 5, 4,  7};
        int[] ints = subSort1(nums);
        System.out.println(Arrays.toString(ints));//[1, 4]
    }

    public static int[] subSort2(int[] array) {
        if (array == null || array.length == 0) return new int[]{-1, -1};
        int last = -1, first = -1;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int len = array.length;
        // {1, 5, 4, 6, 7};
        for (int i = 0; i < len; i++) {
            if (array[i] < max) {
                last = i;
            } else {
                max = Math.max(max, array[i]);
            }

            if (array[len - 1 - i] > min) {
                first = len - 1 - i;
            } else {
                min = Math.min(min, array[len - 1 - i]);
            }
        }
        return new int[]{first, last};
    }

    /**
     * 左和右两侧分别找到逆序的索引位
     * 比如
     * 从左向右寻找最大值,如果发现array(n)>array(n+1),则n+1是逆序索引
     * 从右向左寻找最小值,如果发现array(n)>array(n+1) ,则n为逆序索引
     *
     * @param array
     * @return
     */
    public static int[] subSort1(int[] array) {
        if (array.length == 0) return new int[]{-1, -1};
        int l = -1;
        int max = array[0];
        //{1, 5, 4, 6, 7};
        //从左向右找最大值[r,l],找l
        for (int i = 1; i < array.length; i++) {
            if (array[i] >= max) {
                max = array[i];
            } else {
                l = i;
            }
        }
        if (l == -1) return new int[]{-1, -1};
        int r = -1;
        int min = array[array.length - 1];
        //{1, 5, 4, 6, 7};
        for (int i = array.length - 2; i >= 0; i--) {
            if (array[i] <= min) {
                min = array[i];
            } else {
                r = i;
            }
        }
        return new int[]{r, l};

    }


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
