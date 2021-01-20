package com.lovedata.question;

/**
 * @Classname _1512_好数对的数目_简单
 * @Description 给你一个整数数组 nums 。
 * <p>
 * 如果一组数字 (i,j) 满足 nums[i] == nums[j] 且 i < j ，就可以认为这是一组 好数对 。
 * <p>
 * 返回好数对的数目。
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,2,3,1,1,3]
 * 输出：4
 * 解释：有 4 组好数对，分别是 (0,3), (0,4), (3,4), (2,5) ，下标从 0 开始
 * @Date 2021/1/20 下午9:46
 * @Created by jason
 */
public class _1512_好数对的数目_简单 {
    public static void main(String[] args) {
      int[]  nums= {1,2,3,1,1,3};
        int i = numIdenticalPairs(nums);
        System.out.println(i);

    }

    public static int numIdenticalPairs(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; ++i) {
            for (int j = i + 1; j < nums.length; ++j) {
                if (nums[i] == nums[j]) {
                    ++ans;
                }
            }
        }
        return ans;

    }
}
