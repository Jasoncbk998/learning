package com.lovedata.question;

/**
 * @Classname _35_搜索插入位置_简单
 * @Description TODO
 * @Date 2020/10/30 10:12 下午
 * @Created by jason
 */
public class _35_搜索插入位置_简单 {
    public static void main(String[] args) {
        int[] num = {1, 3, 5, 6};//有序数组
        int i = searchInsert(num, 6);
        System.out.println(i);
    }

    /**
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * <p>
     * 你可以假设数组中无重复元素。
     * 示例 1:
     * 输入: [1,3,5,6], 5
     * 输出: 2
     * 示例 2:
     * 输入: [1,3,5,6], 2
     * 输出: 1
     *
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert(int[] nums, int target) {
        int length = nums.length;
        int left = 0, right = length - 1, ans = length;
        //典型的二分查找
        while (left <= right) {
            //移位运算
//            int mid = ((right - left) >> 1) + left;
            int mid = (left + right) >> 1;//相当于 (left+right)/2
            //目标值<=中间值  则说明该值在mid左边
            if (target <= nums[mid]) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

}
