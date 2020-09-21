package com.lovedata.question;

/**
 * ClassName _371_两整数之和_简单
 * Description
 * Create by Jason
 * Date 2020/9/20 10:40
 */
public class _371_两整数之和_简单 {
    /**
     * 不使用运算符 + 和 - ,计算两整数,​a 、b之和。
     * <p>
     * 示例 1:
     * <p>
     * 输入: a = 1, b = 2
     * 输出: 3
     * 示例 2:
     * <p>
     * 输入: a = -2, b = 3
     * 输出: 1
     *
     * @param args
     */
    public static void main(String[] args) {
        int a = 1, b = 2;
        int sum = getSum(a, b);
        System.out.println(sum);
    }

    /**
     * 使用位操作
     *
     * @param a
     * @param b
     * @return
     */
    public static int getSum(int a, int b) {

        while(b!=0){
            int temp = a ^ b;
             b = (a & b) << 1;
             a=temp;
        }
        return a;
    }
}
