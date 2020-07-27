package com.jason.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;

/**
 * ClassName BubbleSort
 * Description
 * Create by Jason
 * Date 2020/7/24 18:50
 * 冒泡排序
 *  用中间值,时间复杂度是 o(n^2)
 */
public class BubbleSort {
    public static void main(String[] args) {
//        int arr[] = {3, 9, -1, 10, 20};
//        int arr[] = {1, 2, 3, 5, 4};
//        bubblesort(arr);
//        System.out.println(Arrays.toString(arr));

        //测试一下冒泡排序的速度O(n^2), 给80000个数据，测试
        //创建要给80000个的随机的数组
        int[] arr_test = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr_test[i] = (int) (Math.random() * 8000000); //生成一个[0, 8000000) 数
        }
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        //测试冒泡排序
        bubblesort(arr_test);
        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序后的时间是=" + date2Str);

    }

    // 将前面额冒泡排序算法，封装成一个方法
    public static void bubblesort(int[] arr) {
// 冒泡排序 的时间复杂度 O(n^2), 自己写出
        int temp = 0; // 临时变量
        boolean flag = false;//表示是否进行过交换
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
//            System.out.println("第" + (i + 1) + "趟排序后的数组");
//            System.out.println(Arrays.toString(arr));
            if (!flag) {
                break;
            } else {
                flag = false;
            }
//            System.out.println(Arrays.toString(arr));
        }


    }
}
