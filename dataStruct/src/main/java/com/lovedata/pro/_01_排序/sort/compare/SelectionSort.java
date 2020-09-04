package com.lovedata.pro._01_排序.sort.compare;


import com.lovedata.pro._01_排序.sort.Sort;

/**
 * 选择排序
 * 通过循环,每次找到最大值,然后与end值进行交换
 * 然后end--,这样每次找到缩小数组中的最大值,max再与end进行交换
 *
 * @param <T>
 */
public class SelectionSort<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {

//		for (int end = array.length - 1; end > 0; end--) {
//			int max = 0;
//			for (int begin = 1; begin <= end; begin++) {
//				if (cmp(max, begin) <= 0) {
//					max = begin;
//				}
//			}
//			swap(max, end);
//		}

        for (int end = array.length - 1; end > 0; end--) {
            int max = 0;
            for (int begin = 1; begin <= end; begin++) {
                //也就是max <= begin
                if (cmp(max, begin) < 0) {
                    max = begin;
                }
            }
            //内层循环中找到max,然后将max与end值进行交换,往复执行
            swap(max, end);
        }

        // 7 5 10 1 4 2 10
    }

}
