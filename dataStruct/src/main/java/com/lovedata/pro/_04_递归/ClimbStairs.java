package com.lovedata.pro._04_递归;

/**
 * 上楼梯
 */
public class ClimbStairs {

    static int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        //缩减n的规模
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    public static void main(String[] args) {
        int i = climbStairs(10);
        System.out.println(i);
    }

}
