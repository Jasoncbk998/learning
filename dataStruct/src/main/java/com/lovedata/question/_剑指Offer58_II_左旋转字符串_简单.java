package com.lovedata.question;

/**
 * @Classname _剑指Offer58_II_左旋转字符串_简单
 * @Description 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
 * 示例 1：
 * 输入: s = "abcdefg", k = 2
 * 输出: "cdefgab"
 * @Date 2021/1/14 下午10:09
 * @Created by jason
 */
public class _剑指Offer58_II_左旋转字符串_简单 {
    public static void main(String[] args) {
        //n: 从0开始计位
        String abcdefg = reverseLeftWords2("abcdefg", 2);
        System.out.println(abcdefg);//cdefgab
    }

    public static  String reverseLeftWords2(String s, int n) {
        String res = "";
        //n+s.length() 因为从n开始循环,要循环一圈,所以加上长度
        for(int i = n; i < n + s.length(); i++){
            /**
             * 2 % 7 = 2
             * 3 % 7 = 3
             * ...
             * 6 % 7 = 6
             * 7 % 7 = 0
             * 8 % 7 = 1
             * 9 % 7 = 2
             */
            res += s.charAt(i % s.length());//利用取余,得到索引位置
        }

        return res;
    }

     public static String reverseLeftWords1(String s, int n) {
        StringBuilder res = new StringBuilder();
        for (int i = n; i < s.length(); i++)
            res.append(s.charAt(i));
        for (int i = 0; i < n; i++)
            res.append(s.charAt(i));
        return res.toString();
    }
}
