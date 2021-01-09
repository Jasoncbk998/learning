package com.jason.base;

/**
 * @Classname arr
 * @Description
 * 二维数组, 分为外层数组和内存数组  arr[0] 外层
 *  arr[1][2] 内层数组
 * @Date 2020/11/21 7:04 下午
 * @Created by jason
 */
public class arr {
    public static void main(String[] args) {
        //默认的初始化值
        int[][] arr = new int[4][4];
        //内存中解析
        /**
         * 二维数组的外层数组,是一个地址值,对象在栈中,指向堆中的数组,然后堆中的数组[0],才能定位二维数组的位置,见笔记的图
         * 外层数组相当于寻址的过程
         */
        System.out.println(arr[0]);//[I@61bbe9ba  I是int类型, 61bbe9ba内存中的位置
        /**
         * 通过外层数组寻址定位,到内层数组取值
         */
        System.out.println(arr[0][0]);//0


        char[] chars = {1, 2, 3};
        char[] chars1 = new char[]{'a', 'b', '3'};
        System.out.println(chars);
        System.out.println(chars1);

    }
}
