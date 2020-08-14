package com.jason.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * ClassName InsertSort
 * Description
 * Create by Jason
 * Date 2020/7/24 19:43
 * <p>
 * 时间复杂度是o(log2N)
 * 效率高于选择冒泡
 * <p>
 * 插入排序（Insertion Sorting）的基本思想是：
 * 把n个待排序的元素看成为一个有序表和一个无序表，
 * 开始时有序表中只包含一个元素，无序表中包含有n-1个元素，
 * 排序过程中每次从无序表中取出第一个元素，
 * 把它的排序码依次与有序表元素的排序码进行比较，将它插入到有序表中的适当位置，使之成为新的有序表
 * 存在的问题
 * 假如数组是[2,3,4,5,6,1]
 * 那么就做了太多的无用功,这种问题我们推荐使用希尔排序
 */
public class InsertSort {
    public static void main(String[] args) {

        int[] arr = {5, 4, 3, 2};
        insertSort(arr);
        System.out.println(Arrays.toString(arr));


//        int[] arr = new int[80000];
//        for (int i = 0; i < 80000; i++) {
//            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
//        }
//
//        System.out.println("插入排序前");
//        Date data1 = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1Str = simpleDateFormat.format(data1);
//        System.out.println("排序前的时间是=" + date1Str);
//
//        insertSort(arr); //调用插入排序算法
//
//        Date data2 = new Date();
//        String date2Str = simpleDateFormat.format(data2);
//        System.out.println("排序前的时间是=" + date2Str);

    }


    public static void insertSort(int[] arr) {
        /**
         * 通过使用val和index去维护一个有序的数组
         */
        int value = 0;//待插入元素
        int index = 0;//待插入元素的前一位的下标
        for (int i = 1; i < arr.length; i++) {
            //定义带插入的数
            value = arr[i];//将待插入元素保存到value
            index = i - 1;//arr[i] 的前面这个数的下标
            // 给insertVal 找到插入的位置
            //在while中,我们维护一个有序的数组,从小到大,不断的有元素插入进来,我们就不断的去按位比较不断向后推移
            //当推移完成后,对有序数组的第一位进行赋值
            while (index >= 0 && value < arr[index]) {
                arr[index + 1] = arr[index];
                index--;
            }
            //  4,5,2,1
            //我们通过while对我们的有序数组进行推移,然后在这里对有序数组的第一位进行赋值
            //因为index是待插入元素的前一位下标,所以进行赋值时应该+1
            arr[index + 1] = value;
        }
    }
}


