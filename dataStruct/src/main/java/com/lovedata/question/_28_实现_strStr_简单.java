package com.lovedata.question;

/**
 * @Classname _28_实现_strStr_简单
 * @Description TODO
 * @Date 2020/10/29 10:51 下午
 * @Created by jason
 * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
 * <p>
 * 示例 1:
 * <p>
 * 输入: haystack = "hello", needle = "ll"
 * 输出: 2
 * <p>
 * 输入: haystack = "aaaaa", needle = "bba"
 * 输出: -1
 */
public class _28_实现_strStr_简单 {
    public static void main(String[] args) {
        //判断needle这个字符串在haystack中的位置,位置按照第一个字符的位置
        int i = strStr("hello", "ll");
        System.out.println(i);
    }

    public static int strStr(String haystack, String needle) {
        int L = needle.length(), n = haystack.length();
        for (int start = 0; start < n - L + 1; ++start) {
            if (haystack.substring(start, start + L).equals(needle)) {
                return start;
            }
        }
        return -1;
    }
}
