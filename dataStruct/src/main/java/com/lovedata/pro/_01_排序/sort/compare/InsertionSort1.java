package com.lovedata.pro._01_排序.sort.compare;

import com.lovedata.pro._01_排序.sort.Sort;

public class InsertionSort1<T extends Comparable<T>> extends Sort<T> {

	@Override
	protected void sort() {
		for (int begin = 1; begin < array.length; begin++) {
			//每一次循环都记录begin的位置,从begin开始查
			int cur = begin;
			//begin和begin下一个元素进行比较
			//从begin开始,一个一个进行比较,使用cur记录位置
			//cmp(cur, cur - 1) < 0 小于0 才交换 如果 1 3 3 相等是不交换的  所以是稳定的
			while (cur > 0 && cmp(cur, cur - 1) < 0) {
				swap(cur, cur - 1);
				cur--;
			}
		}
	}

}
