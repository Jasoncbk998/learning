package Volatile;

import java.util.concurrent.TimeUnit;

/**
 * ClassName volatileDemoHaveVolatile
 * Description
 * Create by Jason
 * Date 2020/7/21 13:28
 * <p>
 * 在关键字中添加关键字 volatile
 * 可见性
 * 修改了volatile修饰的变量后,其他线程会马上感知,这个就是可见性
 * <p>
 * 原子性
 * 不可分割,完整性, 某一个线程正在做某一个业务,中间不可以被修改加塞或者被分割,需要整体完整
 * 同时成功同时失败
 * <p>
 * 比如张三签名 需要张三两个字都要写完 才可以下一个人签名  这个就是原子性
 * 演示volitale不保证原子性
 *
 * 为什么不可以保证原子性呢????
 *小于20000 是因为线程抢占 ,多个线程修改同一个主内存中的值,会有丢数据的现象
 * mydata.java -> mydata.class -> 字节码
 *
 */
public class volatileDemoHaveVolatile {
    public static void main(String[] args) {
        volatileDemoHaveVolatile v = new volatileDemoHaveVolatile();


        v.atomicity();//main	 finally number value 120008  不是20000  计算结果不对
        //main	 finally number value 200000  完毕

        //v.Visible();
    }

    /**
     * 验证原子性
     *
     * 不保证原子性
     * 如何保证原子性呢???
     *
     */
    public void atomicity() {
        myDataHaveVolatile myData = new myDataHaveVolatile(); //线程操作资源类
        //模拟20个线程
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 10000; j++) {
                    myData.addPlus();
                    myData.addAtomic();
                }
            }, String.valueOf(i)).start();
        }

        //需要等待上面20个线程计算完成后,再用main线程取结果
        while (Thread.activeCount() > 2) {
            Thread.yield();//如果有两个活跃的线程  主线程退让
        }
        System.out.println(Thread.currentThread().getName() + "\t finally number value " + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t finally number value " + myData.atmoic);
    }


    /**
     * 可见性
     */
    public void Visible() {
        myDataHaveVolatile myData = new myDataHaveVolatile(); //线程操作资源类
        //第一个线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "come in");
            try {
                //睡三秒钟
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            myData.addTo60();//修改
            System.out.println(Thread.currentThread().getName() + "\t 已经修改number值为:" + myData.number);
        }, "AAA").start();
        //验证可见性
        while (myData.number == 0) {
            //main线程一直在这里循环等待,直到number的值不在等于零
//            System.out.println("验证可见性");
        }

        System.out.println(Thread.currentThread().getName() + "\t 任务执行完成,number的值为:" + myData.number);
    }
}
