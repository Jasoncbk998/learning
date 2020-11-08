package com.lovedata.question;

/**
 * @Classname _58_最后一个单词的长度_简单
 * @Description TODO
 * @Date 2020/10/31 7:08 下午
 * @Created by jason
 */
public class _58_最后一个单词的长度_简单 {
    public static void main(String[] args) {
        int jason = lengthOfLastWord("jason");
        System.out.println(jason);
    }

    /**
     * 给定一个仅包含大小写字母和空格 ' ' 的字符串 s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
     * <p>
     * 如果不存在最后一个单词，请返回 0 。
     * <p>
     * 说明：一个单词是指仅由字母组成、不包含任何空格字符的 最大子字符串。
     * 示例 :
     * 输入: "Hello World"
     * 输出: 5
     *
     * @param s
     * @return
     */
    public static int lengthOfLastWord(String s) {
        int end = s.length() - 1;
        //去除末尾的空格
        while (end >= 0 && s.charAt(end) == ' ') {
            end--;
        }
        if (end < 0) {
            return 0;
        }
        int start = end;
        //一个字符串中 空格 处就是两个单次的分割处
        while (start >= 0 && s.charAt(start) != ' ') {
            start--;
        }
        return end - start;
    }
}