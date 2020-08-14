package com.jason.sort;

import java.util.Arrays;

/**
 * @Classname quicksort_test
 * @Description TODO
 * @Date 2020/8/14 3:12 下午
 * @Created by jason
 */
public class TestQuickSort {
    private static int partition(int[] arr, int left, int right) {
        //指定左指针i和右指针j
        int l = left;
        int r = right;

        //将第一个数作为基准值。挖坑
        int value = arr[left];

        //使用循环实现分区操作
        while (l < r) {
            //1.从右向左移动j，找到第一个小于基准值的值 arr[j]
            while (arr[r] >= value && l < r) {
                r--;
            }
            //2.将右侧找到小于基准数的值加入到左边的（坑）位置， 左指针想中间移动一个位置i++
            if (l < r) {
                arr[l] = arr[r];
                l++;
            }
            //3.从左向右移动i，找到第一个大于等于基准值的值 arr[i]
            while (arr[l] < value && l < r) {
                l++;
            }
            //4.将左侧找到的打印等于基准值的值加入到右边的坑中，右指针向中间移动一个位置 j--
            if (l < r) {
                arr[r] = arr[l];
                r--;
            }
        }

        //使用基准值填坑，这就是基准值的最终位置
        arr[l] = value;//arr[j] = y;
        //返回基准值的位置索引
        return l; //return j;
    }

    private static void quickSort(int[] arr, int low, int high) {//???递归何时结束
        if (low < high) {
            //分区操作，将一个数组分成两个分区，返回分区界限索引
            int index = partition(arr, low, high);
            //对左分区进行快排
            quickSort(arr, low, index - 1);
            //对右分区进行快排
            quickSort(arr, index + 1, high);
        }

    }

    public static void main(String[] args) {
        //给出无序数组
        int arr[] = {72, 6, 57, 88, 60, 42, 85};

        //输出无序数组
        System.out.println(Arrays.toString(arr));
        //快速排序

        quickSort(arr, 0, arr.length - 1);
        //partition(arr,0,arr.length-1);
        //输出有序数组
        System.out.println(Arrays.toString(arr));
    }


}
