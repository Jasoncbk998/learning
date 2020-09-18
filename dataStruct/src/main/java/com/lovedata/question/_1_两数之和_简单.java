package com.lovedata.question;

import com.lovedata.base._11_哈希表.map.HashMap;

import java.util.Arrays;

/**
 * @Classname _1_两数之和
 * @Description TODO
 * @Date 2020/9/18 9:25 上午
 * @Created by jason
 */
public class _1_两数之和_简单 {
    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     * <p>
     * 给定 nums = [2, 7, 11, 15], target = 9
     * <p>
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     *
     * @param args
     */
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int[] solution = solution(nums, 18);
        System.out.println(Arrays.toString(solution));
    }

    private static int[] solution(int[] nums, int target) {
        int[] ints = new int[2];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int source = target - nums[i];
            if (map.containsKey(source)) {
                return new int[]{map.get(source), i};
            }
            map.put(nums[i],i);
        }


        return null;
    }


}
