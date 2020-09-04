package com.lovedata.high_level._01_排序.sort.compare;

import com.lovedata.high_level._01_排序.sort.Sort;

/**
 * 做了一些优化
 * 加入了 最大值的索引,减少后续比较的范围
 * 逐级递减
 * <p>
 * 根据数组的长度,分两次遍历数组
 *
 * @param <T>
 */
public class BubbleSort3<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int sortedIndex = 1;
            //索引位置1和0开始比较,得出较大值,进行交换
            for (int begin = 1; begin <= end; begin++) {
                // if (array[begin] < array[begin - 1]) {
                //比较二者大小抽取出来的方法
                if (cmp(begin, begin - 1) < 0) {
                    swap(begin, begin - 1);
                    //在内层循环中不断记录交换的位置
                    //在一次循环中记录最后一次交换的位置
                    //这个位置之后的索引位置都是有序的
                    sortedIndex = begin;
                }
            }
            //如果尾部局部有序,则记录最后一次交换位置,减少比较次数
            end = sortedIndex;
        }
    }

}

