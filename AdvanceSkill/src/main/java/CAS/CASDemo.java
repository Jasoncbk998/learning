package CAS;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName CASDemo
 * Description
 * Create by Jason
 * Date 2020/7/21 17:03
 * <p>
 * 1. cas
 * 比较 交换
 */
public class CASDemo {
    public static void main(String[] args) {
        //初始值设置为5
        AtomicInteger atomic = new AtomicInteger(5);
        int i = atomic.get();
        System.out.println(i);
        //期望是5 真实值也是5 那么就可以修改为2019,这个值就是主物理内存中的值被修改
        System.out.println(atomic.compareAndSet(5, 2019) + "\t 当前值:" + atomic.get());//true	 当前值:2019
        System.out.println(atomic.compareAndSet(5, 2014) + "\t 当前值:" + atomic.get());//false	 当前值:2019


    }
}
