package com.jason.stack;

/**
 * ClassName ArrayStack
 * Description
 * Create by Jason
 * Date 2020/7/22 19:11
 */
public class ArrayStack {
    //栈的元素位置从0开始 所以栈顶初始值是-1

    private int maxSize;//栈的大小
    private int[] stack;//数组 数组模拟栈 数据存放在该数组中
    private int top = -1;//top表示栈顶,初始化为-1

    //构造对象,传入栈的大小
    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    //栈满
    public boolean isFull() {
        //top初始值是-1  来一个元素就+1 所以如下
        return top == maxSize - 1;
    }

    //栈空
    public boolean isEmpty() {
        return top == -1;
    }

    //入栈   push
    public void push(int value) {
        //先判断栈是否满
        if (isFull()) {
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top] = value;
    }

    //出栈  pop 将栈顶的数据返回
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈空,无数据");
        }
        int value = stack[top];
        top--;
        return value;
    }

    //显示栈的情况, 遍历栈,遍历时,需要从栈顶开始显示数据
    public void list() {
        if (isEmpty()) {
            System.out.println("栈空,没有数据");
            return;
        }
        //需要从栈顶开始显示数据
        for (int i = top; i >= 0; i--) {
            //从栈顶开始出数据
            System.out.printf("stack [%d] = %d \n", i, stack[i]);
        }

    }

}
