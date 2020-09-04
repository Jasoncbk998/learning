package com.lovedata._题目总结.排序_数组;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/merge-sorted-array/
 * 两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
 * 说明:
 * <p>
 * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
 * 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
 * <p>
 * 输入:
 * nums1 = [1,2,3,0,0,0], m = 3
 * nums2 = [2,5,6],       n = 3
 * <p>
 * 输出: [1,2,2,3,5,6]
 */
public class _0088_合并两个有序数组 {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        /**
         * 首先,两个数组是有序的
         * 然后分别找到各自元素个数
         * 从后往前取值进行比较
         * 分析:
         * 1,3,5,0,0,0
         * 2,4,6
         *   一:
         *      1,3,5,0,0,6
         *      2,4
         *      i1=2 i2=1 cur=4
         *   二:
         *      1,3,5,0,5,6
         *      2,4
         *      i1=1 i2=1 cur=3
         *   三:
         *      1,3,5,4,5,6
         *      2
         *      i1=1 i2=0 cur=2
         *   四:
         *      1,3,3,4,5,6
         *      2
         *      i1=0 i2=0 cur=1
         *   五:
         *      1,2,3,4,5,6
         *      i1=0 i2=0 cur=0
         *
         */
        // nums1 = [1,3,5,0,0,0], m = 3
        // nums2 = [2,4,6],       n = 3
        int i1 = m - 1;//因为nums1一共m个元素,对应索引位是m-1
        int i2 = n - 1;
        int cur = nums1.length - 1;//因为nums1其中包含了占位的空位置,所以在这里取到存放元素的位置

        while (i2 >= 0) {
            if (i1 >= 0 && nums2[i2] < nums1[i1]) {
                //先赋值在对指针做减法
                nums1[cur--] = nums1[i1--];
            } else { // i1 < 0 || nums2[i2] >= nums1[i1]
                nums1[cur--] = nums2[i2--];
            }
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {5, 6, 7};
        merge(nums1, 3, nums2, 3);
        System.out.println(Arrays.toString(nums1));//[1, 2, 3, 5, 6, 7]
    }
}
