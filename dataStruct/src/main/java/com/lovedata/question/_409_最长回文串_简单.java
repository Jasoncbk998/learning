package com.lovedata.question;

import com.sun.org.apache.xml.internal.serializer.ToSAXHandler;

import java.util.Arrays;

/**
 * ClassName _409_最长回文串_简单
 * Description
 * Create by Jason
 * Date 2020/10/3 19:40
 */

/**
 * 给定一个字符串,通过整理字符串使字符串成为最长的回文串,计算其长度
 */
public class _409_最长回文串_简单 {
    public static void main(String[] args) {
        int nums = longestPalindrome("abccccdd");
        System.out.println(nums);

    }

    public static int longestPalindrome(String s) {
        int[] count = new int[128];
        for (char c : s.toCharArray()) {
            //存储字符的个数
            count[c]++;
        }

        int ans = 0;
        for (int v : count) {
            // 1, 1, 4, 2
            //举例   5/2*2=4 出现奇数次就可以认为该字符可以作为回文串
//            1/2*2);//0 如果该字符出现一次,不可以作为回文串
            //4/2*2  =4   该字符是偶数次可以作为回文串
//            5/2*2);//4
            ans += v / 2 * 2;//对出现的次数进行分组,一左一右
            //v 是奇数   并且  ans是偶数
            //假如一个字符串所有字符出现的次数是1, 1, 4, 2,那么最长的回文串长度应该是7
            /**
             * 首先进来第一个字符,出现次数是1次,那么我们认为这个一个字符可以组成一个回文串    v=1,ans=1
             * 进入第二个字符,出现次数也是1次,显然,不可以构成回文串    v=1,ans=1
             * 进入第三个字符,出现次数是4次,是偶数次,我们认为可以作为回文串     v=4,ans=5
             * 进入第四个字符,出现次数是2次,偶数次,可以认为是回文串  v=2,ans=7
             * 通过控制奇偶次数来判断这个字符串可以组成最长回文串的长度
             */
            //v % 2 == 1 判断奇偶
            if (v % 2 == 1 && ans % 2 == 0) {
                ans++;
            }
        }
        return ans;
    }
}
