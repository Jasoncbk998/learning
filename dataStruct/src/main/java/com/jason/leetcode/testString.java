package com.jason.leetcode;

/**
 * @Classname testString
 * @Description TODO
 * @Date 2020/8/3 2:36 下午
 * @Created by jason
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 * “回文串”是一个正读和反读都一样的字符串，比如“level”或者“noon”等等就是回文串。
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 * 输入: "race a car"
 * 输出: false
 */
public class testString {
    /**
     * 双指针思路,左右进行比较
     * 执行用时：3 ms
     * 内存消耗：39.9 MB
     * @param s
     * @return
     */
    public boolean isPalindrome1(String s) {
        if (s.isEmpty())
            return true;
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left)))
                left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right)))
                right--;
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right)))
                return false;
            left++;
            right--;
        }
        return true;
    }

    /**
     * 对数据进行翻转然后equals
     * 执行用时：6 ms
     * 内存消耗：39.8 MB
     *
     * @param s
     * @return
     */
    public static boolean isPalindrome(String s) {
        StringBuffer sbuffer = new StringBuffer();
        int slength = s.length();
        for (int i = 0; i < slength; i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                sbuffer.append(Character.toLowerCase(c));
            }
        }
        StringBuffer reverse = new StringBuffer(sbuffer).reverse();
        return sbuffer.toString().equals(reverse.toString());
    }

    public static void main(String[] args) {
        String s = "A man, a plan, a canal: Panama";
        boolean palindrome = isPalindrome(s);
        System.out.println(palindrome);
    }

}
