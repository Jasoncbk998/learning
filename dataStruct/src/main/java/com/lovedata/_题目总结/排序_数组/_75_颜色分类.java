package com.lovedata._题目总结.排序_数组;


/**
 * https://leetcode-cn.com/problems/sort-colors/
 * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * <p>
 * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 * <p>
 * 注意:
 * 不能使用代码库中的排序函数来解决这道题。
 * <p>
 * 示例:
 * <p>
 * 输入: [2,0,2,1,1,0]
 * 输出: [0,0,1,1,2,2]
 */
public class _75_颜色分类 {
    /*
     * 一个只包含0、1、2的整型数组，要求对它进行【原地】排序
     * 你能想出一个仅使用常数空间的一趟扫描算法吗？
     *
     * 空间复杂度O(1)，时间复杂度O(n)
     *  0 1 2 0 2 1 0 1
     *
     * 用三个指针去控制移动数据
     */
    public void sortColors(int[] nums) {
        int i = 0;//遍历所有元素
        int l = 0;
        int r = nums.length - 1;//在最后,放2使用,发现是2就用r放置
        while (i <= r) {
            if (nums[i] == 0) {
                swap(nums, i++, l++);
            } else if (nums[i] == 1) {
                i++;
            } else {
                swap(nums, i, r--);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
