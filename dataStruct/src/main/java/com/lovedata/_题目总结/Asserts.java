package com.lovedata._题目总结;

public class Asserts {
	public static void test(boolean v) {
		if (v) return;
		System.err.println(new RuntimeException().getStackTrace()[1]);
	}
}

