package com.lovedata._题目总结._03_栈_队列;

/**
 * 155. 最小栈
 * <p>
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * <p>
 * push(x) —— 将元素 x 推入栈中。
 * pop() —— 删除栈顶的元素。
 * top() —— 获取栈顶元素。
 * getMin() —— 检索栈中的最小元素。
 * <p>
 * 示例:
 * <p>
 * 输入：
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 * <p>
 * 输出：
 * [null,null,null,null,-3,null,0,-2]
 * <p>
 * 解释：
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 */
public class MinStack {
    private Node head;

    /**
     * initialize your data structure here.
     */
    public MinStack() {
        head = new Node(0, Integer.MAX_VALUE, null);
    }

    public void push(int x) {
        head = new Node(x, Math.min(x, head.min), head);
    }

    public void pop() {
        head = head.next;
    }

    public int top() {
        return head.val;
    }

    public int getMin() {
        return head.min;
    }

    private static class Node {
        public int val;
        public int min;
        public Node next;

        public Node(int val, int min, Node next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }

//	/* 用来存放正常数据 */
//	private Stack<Integer> stack;
//	/* 用来存放最小数据 */
//	private Stack<Integer> minStack;
//
//    /** initialize your data structure here. */
//    public MinStack() {
//    	stack = new Stack<>();
//    	minStack = new Stack<>();
//    }
//    
//    public void push(int x) {
//    	stack.push(x);
//    	if (minStack.isEmpty()) {
//    		minStack.push(x);
//    	} else {
//    		minStack.push(Math.min(x, minStack.peek()));
//    	}
//    }
//    
//    public void pop() {
//    	stack.pop();
//    	minStack.pop();
//    }
//    
//    public int top() {
//    	return stack.peek();
//    }
//    
//    public int getMin() {
//    	return minStack.peek();
//    }
}
