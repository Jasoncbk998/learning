package com.lovedata.pro._04_递归;

import java.util.Stack;

/**
 * 使用栈,把递归转化成非递归
 */
public class Main {

    public static void main(String[] args) {
        log(6);
    }

    static void log2(int n) {
        Stack<Frame> frames = new Stack<>();
        while (n > 0) {
            frames.push(new Frame(n, n + 10));
            n--;
        }
        while (!frames.isEmpty()) {
            Frame frame = frames.pop();
            System.out.println(frame.v);
        }
    }

    static void log(int n) {
        if (n < 1) return;
        log(n - 1);
        int v = n + 10;
        System.out.println(v);
    }

    /**
     * 求 1 + 2 + 3 + ... + (n - 1) + n 的值
     *
     * @param n
     * @return
     */
    int sum(int n) {
        if (n <= 1) return n;
        return n + sum(n - 1);
    }

    static class Frame {
        int n;
        int v;

        Frame(int n, int v) {
            this.n = n;
            this.v = v;
        }
    }
}
