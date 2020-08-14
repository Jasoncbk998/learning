package com.jason.leetcode.primary.array;

import sun.print.SunMinMaxPage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Classname delrepeat
 * @Description TODO
 * @Date 2020/8/3 3:32 下午
 * @Created by jason
 * 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
 * <p>
 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 * 示例 1:
 * 给定数组 nums = [1,1,2],
 * 函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。
 * 你不需要考虑数组中超出新长度后面的元素。
 * <p>
 * 示例 2:
 * 给定 nums = [0,0,1,1,1,2,2,3,3,4],
 * 函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
 * 你不需要考虑数组中超出新长度后面的元素。
 */
public class num_26_delrepeat {
    public static int removeDuplicates1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int p = 0;
        int q = 1;
        while (q < nums.length) {
            if (nums[p] != nums[q]) {
                nums[p + 1] = nums[q];
                p++;
            }
            q++;
        }
//        System.out.println(Arrays.toString(nums));
        return p + 1;
    }

    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        ArrayList<Integer> list = new ArrayList<>();
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            /**第一次 j=1
             * i=0 j=1 相当于数组的n和-1比较,两者不等不进入判断
             * 第二次 j=2
             * i=0 j=2 数组的n 和n-2 相等进入判断,将nums[0]=nums[2], 1 1 2 => 1 2 2
             * 以此类推
             */

            if (nums[j] != nums[i]) {
                i++;
                list.add(i + 1);
                nums[i] = nums[j];
            }
        }
        System.out.println("去除重复元素的数据:" + Arrays.toString(Arrays.copyOf(nums, i + 1)));
        System.out.println("重复元素的索引: \t" + list.toString());
        return i + 1;
    }

    public static void main(String[] args) {
        int nums[] = {1, 2, 3, 5, 5, 1, 2, 3, 4, 5, 6};//[2,5,1, 3, 4, 2, 4, 2, 2]
        int i = removeDuplicates(nums);

        System.out.println(i);
    }
}
