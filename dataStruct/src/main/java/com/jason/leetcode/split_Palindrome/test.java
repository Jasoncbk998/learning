package com.jason.leetcode.split_Palindrome;

import java.util.List;

/**
 * @Classname test
 * @Description TODO
 * @Date 2020/8/3 3:24 下午
 * @Created by jason
 */
public class test {
    public static void main(String[] args) {
        split_Palindrome_string test = new split_Palindrome_string();
        List<List<String>> abb = test.partition("abb");
        /**
         * [[a, b, b], [a, bb]]
         */
        System.out.println(abb.toString());
    }
}
