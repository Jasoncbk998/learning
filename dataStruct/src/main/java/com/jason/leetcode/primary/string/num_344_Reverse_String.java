package com.jason.leetcode.primary.string;

import java.util.Arrays;

/**
 * @Classname trdt
 * @Description TODO
 * @Date 2020/8/6 11:25 上午
 * @Created by jason
 * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
 * <p>
 * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
 * <p>
 * 你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：["h","e","l","l","o"]
 * 输出：["o","l","l","e","h"]
 * 示例 2：
 * <p>
 * 输入：["H","a","n","n","a","h"]
 * 输出：["h","a","n","n","a","H"]
 */
public class num_344_Reverse_String {
    /**
     * 一次 递归,只调整本次递归的left和right
     * 然后一次进行一次调整
     * 第一次递归
     * left=0 right=3
     * (d,b,c,a)
     * 第二次递归
     * left=1 right=2
     * (d,c,b,a)
     */
    public static void helper(char[] s, int left, int right) {
        if (left >= right) {
            return;
        }
        char tmp = s[left];
        s[left++] = s[right];
        s[right--] = tmp;
        helper(s, left, right);
    }

    public static void reverseString(char[] s) {
        helper(s, 0, s.length - 1);
    }

    public static void main(String[] args) {
        char[] arr = {'a', 'b', 'c', 'd'};
        reverseString(arr);
        System.out.println(Arrays.toString(arr));


    }

}
