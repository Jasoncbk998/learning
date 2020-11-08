package com.lovedata.question;

import java.util.Arrays;

/**
 * @Classname _27_移除元素_简单
 * @Description TODO
 * @Date 2020/10/28 10:47 下午
 * @Created by jason
 */
public class _27_移除元素_简单 {
    public static void main(String[] args) {
        int[] nums = {3,2,2,3};
        int i1 = removeElement2(nums, 2);
        System.out.println(i1);
//        int[] nums1 = {0,1,2,2,3,0,4,2};
//        int i = removeElement1(nums1, 2);
//        System.out.println(i);
    }

    /**
     * 给定 nums = [3,2,2,3], val = 3,
     * <p>
     *     [3,2,2,3] => [3,3,2,3]
     * 函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
     * <p>
     * 你不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 给定 nums = [0,1,2,2,3,0,4,2], val = 2,
     * <p>
     * 函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。
     * <p>
     * 注意这五个元素可为任意顺序。
     * <p>
     * 你不需要考虑数组中超出新长度后面的元素。
     *
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement1(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }
        System.out.println(Arrays.toString(nums));
        return i;
    }

    public static int removeElement2(int[] nums, int val) {
        int i = 0;
        int n = nums.length;
        while (i < n) {
            if (nums[i] == val) {
                nums[i] = nums[n - 1];
                // reduce array size by one
                n--;
            } else {
                i++;
            }
        }
        System.out.println(Arrays.toString(nums));
        return n;
    }
}
