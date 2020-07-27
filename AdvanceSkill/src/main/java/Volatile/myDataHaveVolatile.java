package Volatile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName myData
 * Description
 * Create by Jason
 * Date 2020/7/21 13:20
 */
public class myDataHaveVolatile {
    volatile int number = 0;

    public void addTo60() {
        this.number = 60;
    }
    //验证原子性,number被volatile修饰,验证不保证原子性
    //为什么不加synchronized,不要杀鸡用牛刀

    public void addPlus() {
        number++;
    }

    //带原子性的number
    AtomicInteger atmoic = new AtomicInteger();

    public void addAtomic() {
        atmoic.getAndIncrement();
    }
}
