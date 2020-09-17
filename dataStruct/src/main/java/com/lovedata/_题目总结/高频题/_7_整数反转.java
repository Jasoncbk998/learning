package com.lovedata._题目总结.高频题;

/**
 * https://leetcode-cn.com/problems/reverse-integer/
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
public class _7_整数反转 {
    public static int reverse3(int x) {
        long result = 0;
        while (x != 0) {
            int tmp = x % 10;
            result = result * 10 + tmp;
            x /= 10;
        }
        return (int) result;
    }

    public static int reverse1(int x) {
        long res = 0;
        while (x != 0) {
            res = res * 10 + x % 10;
            if (res > Integer.MAX_VALUE) return 0;
            if (res < Integer.MIN_VALUE) return 0;
            x /= 10;
        }
        return (int) res;
    }

    public static int reverse(int x) {
        int res = 0;
        while (x != 0) {
            int prevRes = res;
            int mod = x % 10;
            res = prevRes * 10 + mod;
            if ((res - mod) / 10 != prevRes) return 0;
            x /= 10;
        }
        return res;
    }

    public static void main(String[] args) {
        int i = reverse4(123);
        System.out.println(i);


    }


    public static int reverse4(int x){
        long result=0;

        while(x!=0){
            int tmp = x % 10;
            result=result*10+tmp;
            x/=10;
        }
    return (int)result;
    }
}
