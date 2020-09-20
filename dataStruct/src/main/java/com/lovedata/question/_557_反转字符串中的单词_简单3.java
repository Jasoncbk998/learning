package com.lovedata.question;

/**
 * ClassName _557_反转字符串中的单词_简单
 * Description
 * Create by Jason
 * Date 2020/9/20 10:01
 */
public class _557_反转字符串中的单词_简单3 {
    /**
     * 输入："Let's take LeetCode contest"
     * 输出："s'teL ekat edoCteeL tsetnoc"
     *
     * @param args
     */
    public static void main(String[] args) {
        String words = "Let's take LeetCode contest";
        String s = reverseWords(words);
        System.out.println(s);
    }

    public static String reverseWords(String s) {
        if (s == null || s.length() == 0) return s;
        String[] word = s.split(" ");
        String value = "";
        for (int i = 0; i <= word.length - 1; i++) {
            for (int j = word[i].length() - 1; j >= 0; j--) {
                char c = word[i].charAt(j);
                value = value + c;
            }
            value = value + " ";
        }
        return value;
    }
}
