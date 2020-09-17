package com.lovedata.pro._01_排序.sort.compare;


import java.util.Arrays;

/**
 * 快速排序是不稳定的排序
 */
public class QuickSort_my {
    public static void main(String[] args) {
        int[] nums = {7, 3, 7, 7, 7, 7, 7, 1};
        sort(nums, 0, nums.length);
        System.out.println(Arrays.toString(nums));
    }

    private static void sort(int[] nums, int begin, int end) {
        if (end - begin < 2) return;
        int mid = middle(nums, begin, end);
        sort(nums, begin, mid);
        sort(nums, mid + 1, end);
    }

    //  int[] nums = {8,7,6,5, 4, 3, 2, 1};
    private static int middle(int[] array, int begin, int end) {
        end--;
        int pivot = array[begin];
        while (begin < end) {
            //右边
            //  int[] nums = {8,7,6,5, 4, 3, 2, 1};
            while (begin < end) {
                if (array[end] > pivot) {
                    end--;
                } else {
                    array[begin++] = array[end];
                    break;
                }
            }
            //左边
            //  int[] nums = {8,7,6,5, 4, 3, 2, 1};
            while (begin < end) {
                if (array[begin] > pivot) {
                    array[end--] = array[begin];
                    break;
                } else {
                    begin++;
                }
            }
        }
        array[begin] = pivot;
        return begin;
    }

}
