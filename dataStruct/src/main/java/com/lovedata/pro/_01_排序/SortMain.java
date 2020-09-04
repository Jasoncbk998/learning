package com.lovedata.pro._01_排序;

import java.util.Arrays;

import com.lovedata.pro._01_排序.sort.Sort;
import com.lovedata.pro._01_排序.sort.compare.SelectionSort;
import com.lovedata.pro.tools.Asserts;
import com.lovedata.pro.tools.Integers;


public class SortMain {

    public static void main(String[] args) {
//        BubbleSort3<Integer> sort = new BubbleSort3<Integer>();
//        Integer[] random = Integers.random(5, 1, 10);
//        sort.sort(random);
//        Integers.println(random);
        testSorts(Integers.random(10000, 5, 1000)
                , new SelectionSort()

        );
    }

    static void testSorts(Integer[] array, Sort... sorts) {
        for (Sort sort : sorts) {
            Integer[] newArray = Integers.copy(array);
            sort.sort(newArray);
            Asserts.test(Integers.isAscOrder(newArray));
        }
        Arrays.sort(sorts);
        //对sort进行排序,进行输出
        for (Sort sort : sorts) {
            System.out.println(sort);
        }
    }

    static void selectionSort(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (array[maxIndex] <= array[begin]) {
                    maxIndex = begin;
                }
            }
            int tmp = array[maxIndex];
            array[maxIndex] = array[end];
            array[end] = tmp;
        }

        // 8 10 9 10
    }

    //
    static void bubbleSort1(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                }
            }
        }
    }

    static void bubbleSort2(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            boolean sorted = true;
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }

    /**
     * 记录交换的位置,优化
     *
     * @param array
     */
    static void bubbleSort3(Integer[] array) {
        for (int end = array.length - 1; end > 0; end--) {
            // sortedIndex的初始值在数组完全有序的时候有用
            int sortedIndex = 1;
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                    sortedIndex = begin;
                }
            }
            end = sortedIndex;
        }
    }
}
