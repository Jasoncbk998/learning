package Volatile;

import java.util.concurrent.TimeUnit;

/**
 * ClassName volatileDemo
 * Description
 * Create by Jason
 * Date 2020/7/21 13:18
 * <p>
 * demo案例
 * <p>
 * 1验证 volatile的可见性
 * number=0 number变量之前没有添加volatile关键字进行修饰
 * 2.现在开始添加volatile
 *
 ***/


public class volatileDemoNoVolatile {
    public static void main(String[] args) {
        myDataNoVolatile myData = new myDataNoVolatile(); //线程操作资源类
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

        }

        System.out.println(Thread.currentThread().getName() + "\t 任务执行完成");


    }
}
