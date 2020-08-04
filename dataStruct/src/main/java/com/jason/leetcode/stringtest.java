package com.jason.leetcode;

import javafx.application.Preloader;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @Classname num2
 * @Description TODO
 * @Date 2020/8/2 8:02 下午
 * @Created by jason
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 */
public class stringtest {
    public static HashMap<Integer, String> stringlength(String s) {
        HashSet<Character> occ = new HashSet<>();
        HashMap<Integer, String> map = new HashMap<>();
        int n = s.length();
        String target = null;
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; i++) {
            //i 不等于0
            //相当于从字符首位开始算,然后去掉首位,在从新的首位开始计算,以此类推
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));//取出字符串中的 i-1字符 每次循环都去掉字符串首位的字符,在hashset中去掉
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            int number = rk - i + 1;
            //取出无重复极长字符串
            if (ans <= number) {
                target = s.substring(i, number+1);
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            //rk - i +1  rk是我们累加的无重复次数,减去i是我们从第几位开始循环的 +1 是因为rk初始值是-1
            ans = Math.max(ans, number);
            map.put(ans,target);

        }
        return map;
    }

    public static void main(String[] args) {
        // 取数据有bug
        String s = "cbcbb";
        HashMap<Integer, String> map = stringlength(s);
        System.out.println(map.toString());
    }


}
