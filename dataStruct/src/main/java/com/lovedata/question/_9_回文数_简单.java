package com.lovedata.question;

/**
 * ClassName _9_回文数_简单
 * Description
 * Create by Jason
 * Date 2020/9/20 10:20
 */
public class _9_回文数_简单 {
    public static void main(String[] args) {
        int num = 121;
        boolean palindrome = isPalindrome1(num);
        System.out.println(palindrome);
    }

    public static boolean isPalindrome1(int x) {
        if( x<0) return false;
        int a=x;
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;//个位
            x = x / 10;
            rev = rev * 10 + pop;
        }

        if (rev == a) {
            return true;
        }
        return false;
    }

//    public static boolean isPalindrome2(int x) {
//
//    }

}
