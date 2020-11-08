package com.lovedata.question;

/**
 * @Classname _136_只出现一次的数字_简单
 * @Description TODO
 * @Date 2020/11/4 12:07 上午
 * @Created by jason
 */
public class _136_只出现一次的数字_简单 {
    public static void main(String[] args) {
        int[] nums = {2, 2, 1};
        int i = singleNumber(nums);
        System.out.println(i);
    }

    /**
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * <p>
     * 说明：
     * <p>
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     * <p>
     * 示例 1:
     * <p>
     * 输入: [2,2,1]
     * 输出: 1
     *
     * @param nums
     * @return
     */
    public static int singleNumber(int[] nums) {
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }
}
