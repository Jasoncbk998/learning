package com.jason.leetcode.primary.string;

/**
 * @Classname tttt
 * @Description TODO
 * @Date 2020/8/6 1:15 下午
 * @Created by jason
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 123
 * 输出: 321
 *  示例 2:
 * <p>
 * 输入: -123
 * 输出: -321
 * 示例 3:
 * <p>
 * 输入: 120
 * 输出: 21
 */
public class num_7_Reverse_Integer {
    public static int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;//个位 十位  百位  千位
            x /= 10;//个位 十位  百位  千位
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }
            rev = rev * 10 + pop;
        }
        return rev;

    }

    public static void main(String[] args) {
        int num = 1234;
        int reverse = reverse(num);
        System.out.println(reverse);



    }
}
