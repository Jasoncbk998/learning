package com.jason.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * ClassName SelectSort
 * Description
 * Create by Jason
 * Date 2020/7/24 19:17
 * <p>
 * 选择排序
 * 从小开始找,放到最后一个
 * 时间复杂度 也是o(n^2)
 * 效率高于冒泡排序
 * <p>
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1, -1, 90, 123};
        selectsort(arr);
        System.out.println(Arrays.toString(arr));

//创建要给80000个的随机的数组
//        int[] arr = new int[80000];
//        for (int i = 0; i < 80000; i++) {
//            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
//        }
//
//        System.out.println("排序前");
//        //System.out.println(Arrays.toString(arr));
//
//        Date data1 = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1Str = simpleDateFormat.format(data1);
//        System.out.println("排序前的时间是=" + date1Str);
//
//        selectsort(arr);
//
//
//        Date data2 = new Date();
//        String date2Str = simpleDateFormat.format(data2);
//        System.out.println("排序前的时间是=" + date2Str);
    }

    public static void selectsort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;//最小数的索引
            int min = arr[i];//假设arr[i]是最小值
            //拿到最小数的下标
            //这个循环取的值永远是上一个循环取值的下一个值
            //第一轮  从第一个循环中取到arr[0],然后用arr[0]在第二个循环中与arr[1]--arr[n] 依次比较,取得一个最小值
            //第二轮  在第一个循环中取得arr[1],用arr[1]与第二个循环中的arr[2]--arr[n]依次比较,取得一个最小值
            // ...
            for (int j = i + 1; j < arr.length; j++) {
                /**
                 * 如果是从大道小 =>  min < arr[j]
                 */
                /**
                 * 这一步是找最小值,将arr[i]元素依次与后边元素比较,找到最小值进行替换
                 */
                if (min > arr[j]) {//说明假定的最小值,并不是最小
                    min = arr[j];//重置min
                    minIndex = j;//重置minindex
                }
            }
            //将最小值,放在arr[0],交换
            //因为minIndex已经被赋值了,所以跟i不相等,所以找到了最小值进行替换
            if (minIndex != i) {
                //将找到的最小值与arr[i]交换
                arr[minIndex] = arr[i];
                //将i位换成最小值
                arr[i] = min;//取小
            }
//            System.out.println(Arrays.toString(arr));// 1, 34, 119, 101
        }

/*

		//使用逐步推导的方式来，讲解选择排序
		//第1轮
		//原始的数组 ： 	101, 34, 119, 1
		//第一轮排序 :   	1, 34, 119, 101
		//算法 先简单--》 做复杂， 就是可以把一个复杂的算法，拆分成简单的问题-》逐步解决

		//第1轮
		int minIndex = 0;
		int min = arr[0];
		for(int j = 0 + 1; j < arr.length; j++) {
			if (min > arr[j]) { //说明假定的最小值，并不是最小
				min = arr[j]; //重置min
				minIndex = j; //重置minIndex
			}
		}


		//将最小值，放在arr[0], 即交换
		if(minIndex != 0) {
			arr[minIndex] = arr[0];
			arr[0] = min;
		}

		System.out.println("第1轮后~~");
		System.out.println(Arrays.toString(arr));// 1, 34, 119, 101


		//第2轮
		minIndex = 1;
		min = arr[1];
		for (int j = 1 + 1; j < arr.length; j++) {
			if (min > arr[j]) { // 说明假定的最小值，并不是最小
				min = arr[j]; // 重置min
				minIndex = j; // 重置minIndex
			}
		}

		// 将最小值，放在arr[0], 即交换
		if(minIndex != 1) {
			arr[minIndex] = arr[1];
			arr[1] = min;
		}

		System.out.println("第2轮后~~");
		System.out.println(Arrays.toString(arr));// 1, 34, 119, 101

		//第3轮
		minIndex = 2;
		min = arr[2];
		for (int j = 2 + 1; j < arr.length; j++) {
			if (min > arr[j]) { // 说明假定的最小值，并不是最小
				min = arr[j]; // 重置min
				minIndex = j; // 重置minIndex
			}
		}

		// 将最小值，放在arr[0], 即交换
		if (minIndex != 2) {
			arr[minIndex] = arr[2];
			arr[2] = min;
		}

		System.out.println("第3轮后~~");
		System.out.println(Arrays.toString(arr));// 1, 34, 101, 119 */

    }
}
