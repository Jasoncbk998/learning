package com.lovedata.pro._04_递归;

/**
 * 尾调用
 * 一个函数最后一个动作是调用,成为 尾调用
 *
 * 最后一个函数是调用自己,就是尾递归
 */
public class TailCall {
	
	public static void main(String[] args) {
		System.out.println(facttorial(4));
	}
	
	/**
	 * 1 * 2 * 3 * 4 * ... * (n - 1) * n
	 * @param n
	 * @return
	 */
	static int facttorial(int n) {
		return facttorial(n, 1);
	}
	
	static int facttorial(int n, int result) {
		if (n <= 1) return result;
		return facttorial(n - 1, result * n);
	}
	
//	static int facttorial(int n) {
//		if (n <= 1) return n;
//		return n * facttorial(n - 1);
//	}
}
