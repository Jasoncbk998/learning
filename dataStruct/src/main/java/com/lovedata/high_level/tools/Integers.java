package com.lovedata.high_level.tools;

import java.util.Arrays;

public class Integers {
	/**
	 * 随机
	 * @param count
	 * @param min
	 * @param max
	 * @return
	 */
	public static Integer[] random(int count, int min, int max) {
		if (count <= 0 || min > max) return null;
		Integer[] array = new Integer[count];
		int delta = max - min + 1;
		for (int i = 0; i < count; i++) {
			array[i] = min + (int)(Math.random() * delta);
		}
		return array;
	}
	
	public static Integer[] combine(Integer[] array1, Integer[] array2) {
		if (array1 == null || array2 == null) return null;
		Integer[] array = new Integer[array1.length + array2.length];
		for (int i = 0; i < array1.length; i++) {
			array[i] = array1[i];
		}
		for (int i = 0; i < array2.length; i++) {
			array[i + array1.length] = array2[i];
		}
		return array;
		
	}

	/**
	 * 产生count个相同的数字
	 * 产生unsameCount个不相同的数字
	 * @param count
	 * @param unsameCount
	 * @return
	 */
	public static Integer[] same(int count, int unsameCount) {
		if (count <= 0 || unsameCount > count) return null;
		Integer[] array = new Integer[count];
		for (int i = 0; i < unsameCount; i++) {
			array[i] = unsameCount - i;
		}
		for (int i = unsameCount; i < count; i++) {
			array[i] = unsameCount + 1;
		}
		return array;
	}
	
	public static Integer[] headTailAscOrder(int min, int max, int disorderCount) {
		Integer[] array = ascOrder(min, max);
		if (disorderCount > array.length) return array;
		
		int begin = (array.length - disorderCount) >> 1;
		reverse(array, begin, begin + disorderCount);
		return array;
	}
	
	public static Integer[] centerAscOrder(int min, int max, int disorderCount) {
		Integer[] array = ascOrder(min, max);
		if (disorderCount > array.length) return array;
		int left = disorderCount >> 1;
		reverse(array, 0, left);
		
		int right = disorderCount - left;
		reverse(array, array.length - right, array.length);
		return array;
	}
	
	public static Integer[] headAscOrder(int min, int max, int disorderCount) {
		Integer[] array = ascOrder(min, max);
		if (disorderCount > array.length) return array;
		reverse(array, array.length - disorderCount, array.length);
		return array;
	}

	/**
	 * 尾部升序
	 * @param min
	 * @param max
	 * @param disorderCount
	 * @return
	 */
	public static Integer[] tailAscOrder(int min, int max, int disorderCount) {
		Integer[] array = ascOrder(min, max);
		if (disorderCount > array.length) return array;
		reverse(array, 0, disorderCount);
		return array;
	}
	
	public static Integer[] ascOrder(int min, int max) {
		if (min > max) return null;
		Integer[] array = new Integer[max - min + 1];
		for (int i = 0; i < array.length; i++) {
			array[i] = min++;
		}
		return array;
	}
	
	public static Integer[] descOrder(int min, int max) {
		if (min > max) return null;
		Integer[] array = new Integer[max - min + 1];
		for (int i = 0; i < array.length; i++) {
			array[i] = max--;
		}
		return array;
	}
	
	/**
	 * 反转一个数组，索引范围是[begin, end)
	 */
	private static void reverse(Integer[] array, int begin, int end) {
		int count = (end - begin) >> 1;
		int sum = begin + end - 1;
		for (int i = begin; i < begin + count; i++) {
			int j = sum - i;
			int tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
		}
	}

	/**
	 * 复制
	 * @param array
	 * @return
	 */
	public static Integer[] copy(Integer[] array) {
		return Arrays.copyOf(array, array.length);
	}

	/**
	 * 判断数组是否为升序
	 * @param array
	 * @return
	 */
	public static boolean isAscOrder(Integer[] array) {
		if (array == null || array.length == 0) return false;
		for (int i = 1; i < array.length; i++) {
			if (array[i - 1] > array[i]) return false;
		}
		return true;
	}
	
	public static void println(Integer[] array) {
		if (array == null) return;
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i != 0) string.append(",");
			string.append(array[i]);
		}
		System.out.println(string);
	}
}
