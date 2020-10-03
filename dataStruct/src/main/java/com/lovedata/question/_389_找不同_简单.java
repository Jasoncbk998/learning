package com.lovedata.question;

/**
 * ClassName _389_找不同_简单
 * Description
 * Create by Jason
 * Date 2020/9/22 12:04
 */
public class _389_找不同_简单 {
    /**
     * 给定两个字符串 s 和 t，它们只包含小写字母。
     * <p>
     * 字符串 t 由字符串 s 随机重排，然后在随机位置添加一个字母。
     * <p>
     * 请找出在 t 中被添加的字母。
     * 输入：
     * s = "abcd"
     * t = "abcde"
     * <p>
     * 输出：
     * e
     * <p>
     * 解释：
     * 'e' 是那个被添加的字母。
     *
     * @param args
     */
    public static void main(String[] args) {
        String s = "abcd";
        String t = "abced";
//        char theDifference = findTheDifference(s, t);
//        System.out.println(theDifference);
        System.out.println(t.length());
    }

    public static char findTheDifference(String s, String t) {
        //字符串长度-1  (0,n-1)
        char ans = t.charAt(t.length() - 1);
        for (int i = 0; i < s.length(); i++) {
            //arr[3] ^bai= arr[2]; 相当于arr[3] = arr[3] ^ arr[2];
            /**
             * 1^1=0 0^0=0 1^0=1
             * (101)^(100)=001
             */
            ans ^= s.charAt(i);
            ans ^= t.charAt(i);
        }
        return ans;
    }
}
