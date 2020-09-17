package com.lovedata._题目总结.高频题;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/two-sum/
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * 给定 nums = [2, 7, 11, 15], target = 9
 * <p>
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
public class _1_两数之和 {
    public int[] twoSum1(int[] nums, int target) {
        if (nums == null) return null;
        // 用来存放之前扫描过的元素
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer idx = map.get(target - nums[i]);
            if (idx != null) return new int[]{idx, i};
            map.put(nums[i], i);
        }
        return null;
    }

    /**
     * 根据map的kv性质
     * Integer index = map.get(targer - nums[i]);
     * 如果index不为空说明有这个数值,所以就把这两个元素的索引保存起来,输出即可
     *
     * @param nums
     * @param targer
     * @return
     */
    public int[] twoSum2(int[] nums, int targer) {
        if (nums == null) return null;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer index = map.get(targer - nums[i]);
            if (index != null) {
                return new int[]{index, i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        int[] ints = twoSum3(nums, 9);
        System.out.println(Arrays.toString(ints));
    }

    public static int[] twoSum3(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer index = map.get(target - nums[i]);
            if(index!=null){
                return new int[]{index,i};
            }
            map.put(nums[i],i);
        }


        return null;
    }

}
