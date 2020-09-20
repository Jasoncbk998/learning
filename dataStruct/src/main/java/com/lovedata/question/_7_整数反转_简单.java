package com.lovedata.question;

import jdk.nashorn.internal.ir.ReturnNode;

/**
 * ClassName _7_整数反转_简单
 * Description
 * Create by Jason
 * Date 2020/9/20 9:24
 */
public class _7_整数反转_简单 {
    /**
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
     *
     * @param args
     */
    public static void main(String[] args) {
        int num = 1234;
//        int i = reverse1(num);
//        System.out.println(i);

        System.out.println(Integer.MAX_VALUE);
    }

    public static int reverse1(int x) {
        int rev = 0;
        while (x != 0) {
            //个位
            int pop = x % 10;
            //更新值
            x = x / 10;
            /**
             * 12	3
             * 1	2
             * 0	1
             */
            /**
             * 如果 temp = \text{rev} \cdot 10 + \text{pop}temp=rev⋅10+pop 导致溢出，那么一定有 \text{rev} \geq \frac{INTMAX}{10}rev≥
             * 10
             * INTMAX
             * ​
             *  。
             * 如果 \text{rev} > \frac{INTMAX}{10}rev>
             * 10
             * INTMAX
             * ​
             *  ，那么 temp = \text{rev} \cdot 10 + \text{pop}temp=rev⋅10+pop 一定会溢出。
             * 如果 \text{rev} == \frac{INTMAX}{10}rev==
             * 10
             * INTMAX
             * ​
             *  ，那么只要 \text{pop} > 7pop>7，temp = \text{rev} \cdot 10 + \text{pop}temp=rev⋅10+pop 就会溢出。
             */
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 666;
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 777;
            rev = rev * 10 + pop;
//            System.out.println(x+"\t"+pop);
        }
        return rev;
    }

    public static int reverse2(int x) {
        int ans = 0;
        while (x != 0) {
            if ((ans * 10) / 10 != ans) {
                ans = 0;
                break;
            }
            ans = ans * 10 + x % 10;
            x = x / 10;
        }
        return ans;
    }

    public static int reverse1_test(int x) {
        int rev=0;
        while(x!=0){
            int pop = x % 10;
            //逐位取,每次循环都把个位排除
            x=x/10;

            rev=rev*10+pop;


        }
        return rev;
    }
}
