package com.lovedata._题目总结._01_排序_数组;

import java.util.Arrays;

/**
 * @Classname _977_有序数组的平方
 * @Description TODO
 * @Date 2020/9/11 10:55 上午
 * @Created by jason
 * 给定一个按非递减顺序排序的整数数组 A，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。
 * <p>
 * 输入：[-4,-1,0,3,10]
 * 输出：[0,1,9,16,100]
 * 示例 2：
 * 输入：[-7,-3,2,3,11]
 * 输出：[4,9,9,49,121]
 * 1 <= A.length <= 10000
 * -10000 <= A[i] <= 10000
 * A 已按非递减顺序排序。
 */
public class _977_有序数组的平方 {
    public static void main(String[] args) {
        int[] array = {-4, -1, 0, 3, 10};
        int[] ints = sortedSquares(array);
        System.out.println(Arrays.toString(ints));
    }

    public static int[] sortedSquares(int[] array) {
        if (array == null) return null;
        int[] ints = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            int pow = (int) Math.pow(array[i], 2);
            array[i] = pow;
        }
        //1,2,3,4,5
        //0,1,2,3,4
        int t = 0;
        for (int k = array.length - 1; k >= 0; k--) {
            int max = 0;//假设最大的索引
            for (int j = 1; j <= k; j++) {
                if (array[max] >= array[j]) {
                    max = j;
                }
            }
            int tmp = array[max];
            array[max] = array[k];
            array[k] = tmp;
            ints[t++] = array[k];
        }
//        System.out.println(Arrays.toString(ints));
        return ints;
    }
}
