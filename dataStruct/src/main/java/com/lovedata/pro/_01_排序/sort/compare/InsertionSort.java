package com.lovedata.pro._01_排序.sort.compare;

import com.lovedata.pro._03_图.MinHeap;

import java.util.Arrays;

/**
 * @Classname InsertionSort
 * @Description TODO
 * @Date 2020/9/11 3:25 下午
 * @Created by jason
 * <p>
 * 插入排序
 */
public class InsertionSort {
    static int[] nums;

    public static void main(String[] args) {
        InsertionSort insert = new InsertionSort();
        nums = new int[]{5, 8, 1, 3, 7, 6, 4};
        insert.sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    /**
     * 插入排序
     *
     * @param nums
     */
    public void sort(int[] nums) {
        for (int begin = 1; begin < nums.length; begin++) {
            //将nums[begin]插入到适合的位置
            insert(begin, search(begin));

        }
    }

    /**
     * 找到该元素的插入位置
     * 因为二分查找是对有序数组的,所以二分查找的begin是0开始
     * 传入的index是从1开始,这样就可以从局部有序,锁着index递增,慢慢的使全局有序
     * 先把两个元素保证有序,在三个元素有序,依次递增
     *
     * @param index
     * @return
     */
    private int search(int index) {
        int begin = 0;
        int end = index;
        //使用二分查找
        while (begin < end) {
            //待插入元素和中间值进行比较,判断插入范围
            //确定该元素插入位置
            int mid = (begin + end) >> 1;
            if (nums[index] < nums[mid]) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }

    /**
     * 将source位置的元素插入到dest位置
     *
     * @param source
     * @param dest
     */
    private void insert(int source, int dest) {
        int num = nums[source];
        for (int i = source; i > dest; i--) {
            //移动
            nums[i] = nums[i - 1];
        }
        nums[dest] = num;
    }


}
