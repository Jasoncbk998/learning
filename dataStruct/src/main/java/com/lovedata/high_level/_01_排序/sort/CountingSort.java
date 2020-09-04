package com.lovedata.high_level._01_排序.sort;

/**
 * 计数排序
 * 针对整数
 *
 */
public class CountingSort extends Sort<Integer> {
	/**
	 * 优化后的计数排序
	 */
	@Override
	protected void sort() {
		// 找出最值
		int max = array[0];
		int min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
			if (array[i] < min) {
				min = array[i];
			}
		}
		
		// 开辟内存空间，存储次数
		int[] counts = new int[max - min + 1];

		// 统计每个整数出现的次数
		for (int i = 0; i < array.length; i++) {
			//array[i] - min 是这个元素在排序数组中的索引
			counts[array[i] - min]++;
		}
		// 累加次数
		//不用算int=0,因为前边没有元素
		// i +=i-1 出现 就是当前值和前一个值进行求和计数到当前值
		//这个就是counts数组中的元素值就是次数了
		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i - 1];
		}
		
		// 从后往前遍历元素，将它放到有序数组中的合适位置
		int[] newArray = new int[array.length];
		for (int i = array.length - 1; i >= 0; i--) {
			//赋值
			newArray[--counts[array[i] - min]] = array[i];
		}
		
		// 将有序数组赋值到array
		for (int i = 0; i < newArray.length; i++) {
			array[i] = newArray[i];
		}
	}

	/**
	 * 简单版
	 * 效率低
	 * 内存空间浪费
	 * 不稳定
	 * 不能排序负整数
	 */
	protected void sort0() {
		// 找出最大值
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		} // O(n)
		
		// 开辟内存空间，存储每个整数出现的次数
		int[] counts = new int[1 + max];
		// 统计每个整数出现的次数
		for (int i = 0; i < array.length; i++) {
			counts[array[i]]++;
		} // O(n)
		
		// 根据整数的出现次数，对整数进行排序
		int index = 0;
		for (int i = 0; i < counts.length; i++) {
			while (counts[i]-- > 0) {
				array[index++] = i;
			}
		} // O(n)
	}	
	
	public static void main(String[] args) {
		Person[] persons = new Person[] {
				new Person(20, "A"),
				new Person(-13, "B"),
				new Person(17, "C"),
				new Person(12, "D"),
				new Person(-13, "E"),
				new Person(20, "F")
		};
		
		// 找出最值
		int max = persons[0].age;
		int min = persons[0].age;
		for (int i = 1; i < persons.length; i++) {
			if (persons[i].age > max) {
				max = persons[i].age;
			}
			if (persons[i].age < min) {
				min = persons[i].age;
			}
		}
		
		// 开辟内存空间，存储次数
		int[] counts = new int[max - min + 1];
		// 统计每个整数出现的次数
		for (int i = 0; i < persons.length; i++) {
			counts[persons[i].age - min]++;
		}
		// 累加次数
		for (int i = 1; i < counts.length; i++) {
			counts[i] += counts[i - 1];
		}
		
		// 从后往前遍历元素，将它放到有序数组中的合适位置
		Person[] newArray = new Person[persons.length];
		for (int i = persons.length - 1; i >= 0; i--) {
			newArray[--counts[persons[i].age - min]] = persons[i];
		}
		
		// 将有序数组赋值到array
		for (int i = 0; i < newArray.length; i++) {
			persons[i] = newArray[i];
		}
		
		for (int i = 0; i < persons.length; i++) {
			System.out.println(persons[i]);
		}
	}
	
	private static class Person {
		int age;
		String name;
		Person(int age, String name) {
			this.age = age;
			this.name = name;
		}
		@Override
		public String toString() {
			return "Person [age=" + age 
					+ ", name=" + name + "]";
		}
	}
}
