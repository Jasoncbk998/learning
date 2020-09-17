package com.lovedata._题目总结._03_栈_队列;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/daily-temperatures/
 * 请根据每日 气温 列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。如果气温在这之后都不会升高，请在该位置用 0 来代替。
 * <p>
 * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，
 * 你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
 * 第一天是73度,第二天是74度,所以是过一天升温,所以73对应的是1
 * 每一个元素,右边第一个比他大的值
 * <p>
 * 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
 */
public class _739_每日温度 {
    /**
     * 精简版
     */
    public int[] dailyTemperatures(int[] T) {
        if (T == null || T.length == 0) return null;
        int[] values = new int[T.length];
        for (int i = T.length - 2; i >= 0; i--) {
            int j = i + 1;
            while (true) {
                if (T[i] < T[j]) {
                    values[i] = j - i;
                    break;
                } else if (values[j] == 0) {
                    //因为j右边没有比它大的了,所以i的右边也没有比它大的了,直击给values[i]赋值0
                    values[i] = 0;
                    break;
                }
                // 当T[i] == T[j]的时候
                j = j + values[j];//j右移寻找大于values[i]的值,每加一次都从新进入一个while
            }
        }
        return values;
    }

    /**
     * 原版
     * @param T
     * @return
     */
    public int[] dailyTemperatures1(int[] T) {
        if (T == null || T.length == 0) return null;
        int[] values = new int[T.length];
        for (int i = T.length - 2; i >= 0; i--) {
            int j = i + 1;
            while (true) {
                if (T[i] < T[j]) {
                    values[i] = j - i;
                    break;
                } else if (values[j] == 0) {
                    values[i] = 0;
                    break;
                } else if (T[i] == T[j]) {
                    values[i] = values[j] + j - i;
                    break;
                } else {
                    j = j + values[j];
                }
            }
        }
        return values;
    }
/**
 * 使用栈
 */
    public int[] dailyTemperatures2(int[] T) {
        if (T == null || T.length == 0) return null;
        int[] result = new int[T.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < T.length; i++) {
            // 这里应该要写大于，不要写大于等于
            while (!stack.isEmpty() && T[i] > T[stack.peek()]) {
                result[stack.peek()] = i - stack.peek();
                stack.pop();
            }
            stack.push(i);
        }
        return result;
    }
}
