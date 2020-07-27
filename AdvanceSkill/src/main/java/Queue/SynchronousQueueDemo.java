package Queue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * ClassName SynchronousQueueDemo
 * Description
 * Create by Jason
 * Date 2020/7/23 10:03
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        //生产一个 消费一个
        SynchronousQueue<String> block = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                block.put("1");
                System.out.println(Thread.currentThread().getName() + "\t put 2");
                block.put("2");
                System.out.println(Thread.currentThread().getName() + "\t put 3");
                block.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t " + block.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t " + block.take());
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t " + block.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();


    }
}
