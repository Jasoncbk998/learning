package com.lovedata.pro._01_排序.sort.compare;


import com.lovedata.pro._01_排序.sort.Sort;

/**
 * 优化
 * 从比较交换改成 挪动
 * 挪动元素
 * @param <T>
 */
public class InsertionSort2<T extends Comparable<T>> extends Sort<T> {

	@Override
	protected void sort() {
		for (int begin = 1; begin < array.length; begin++) {
			int cur = begin;
			//拿出当前元素进行比较
			T v = array[cur];
			//逆序对越多,优化效果越明显
			while (cur > 0 && cmp(v, array[cur - 1]) < 0) {
				//挪动
				array[cur] = array[cur - 1];
				//移动指针
				cur--;
			}
			array[cur] = v;
		}
	}

}
