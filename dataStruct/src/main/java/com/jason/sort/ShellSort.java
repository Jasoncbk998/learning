package com.jason.sort;

/**
 * ClassName ShellSort
 * Description
 * Create by Jason
 * Date 2020/7/27 16:43
 */
public class ShellSort {
    public static void main(String[] args) {
//int[] arr = { 8, 9, 1, 7, 2, 3, 5, 4, 6, 0 };
    }

    public static void shellSort2(int[] arr) {
        // 增量gap, 并逐步的缩小增量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // 从第gap个元素，逐个对其所在的组进行直接插入排序
            for (int i = gap; i < arr.length; i++) {
                int j =i;
                int temp=arr[j];
                if(arr[j]<arr[j-gap]){
                    while(j-gap>=0 && temp<arr[j-gap]){
                        //移动
                        arr[j]=arr[j-gap];
                        j-=gap;
                    }
                    //推出while,就给temp找到插入的位置
                    arr[j]=temp;
                }
            }
        }
    }
}
