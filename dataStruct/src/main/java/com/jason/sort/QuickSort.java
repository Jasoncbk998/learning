package com.jason.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @Classname QuickSort
 * @Description TODO
 * @Date 2020/7/27 8:21 下午
 * @Created by jason
 * 快速排序
 * 通过一趟排序,将要排序的数据切分成为两个独立的部分,其中一个部分的所有数据都比另外一个部分的所有数据都要小
 * 然后再按照同样的方法对两部分进行快速排序,整个过程可以递归进行,完成数据整体有序
 */
public class QuickSort {
    public static void main(String[] args) {
//        int[] arr = {-9, 78, 0, 23, -567, 70, -1, 900, 4561};
        int[] arr = {-9, 78, 0, 23, -567, 70};
//        int[] arr = {6,5,4,4,2,7,1};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

        //测试快排的执行速度
        // 创建要给80000个的随机的数组
//        int[] arr = new int[8000000];
//        for (int i = 0; i < 8000000; i++) {
//            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
//        }
//
//        System.out.println("排序前");
//        Date data1 = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date1Str = simpleDateFormat.format(data1);
//        System.out.println("排序前的时间是=" + date1Str);
//
//        quickSort(arr, 0, arr.length - 1);
//
//        Date data2 = new Date();
//        String date2Str = simpleDateFormat.format(data2);
//        System.out.println("排序前的时间是=" + date2Str);
        //System.out.println("arr=" + Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, int left, int right) {
        int l = left; //左下标
        int r = right; //右下标
        //将arr分为两个部分,中间值
        int middle = arr[(left + right) / 2];
        int temp = 0;
        /**
         * 保证
         * middle左边的元素均小于middle
         * middle右边的元素均大于middle
         */
        while (l < r) {
            // 通过比对寻找 l 和 r的索引位置

           //当前值若小于middle,则+1 继续比对
            while (arr[l] < middle) {
                l += 1;
            }
            //当前值是否大于middle,逐个寻找
            while (arr[r] > middle) {
                r -= 1;
            }
          //表明 middle左边所有值均小于middle
            //middle右边所有值均大于middle
            if (l >= r) {
                break;
            }

            //交换
            //因为通过while,找到了arr[l] > middle  arr[r]<middle  对二者换位
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            //arr[l]是arr[r]的互换值,arr[r]把找到小于等于pivot的值给了arr[l]
            //所以r之后的元素都是大于pivot,所以对r减一操作
//            if (arr[l] == middle) {
//                r -= 1;
//            }
//            //arr[l]是arr[r]的互换值,arr[l]把找到大于等于pivot的值给了arr[r]
//            //所以l以前的元素都是小于pivot,所以对l加一操作
//            if (arr[r] == middle) {
//                l += 1;
//            }
        }

        // 如果 l == r, 必须l++, r--, 否则为出现栈溢出
        if (l == r) {
            l += 1;
            r -= 1;
        }
//        //向左递归
        if (left < r) {
            quickSort(arr, left, r);
        }
//        //向右递归
        if (right > l) {
            quickSort(arr, l, right);
        }
    }
}
