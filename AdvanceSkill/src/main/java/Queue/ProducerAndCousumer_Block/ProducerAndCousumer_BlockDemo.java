package Queue.ProducerAndCousumer_Block;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ClassName ProducerAndCousumer_BlockDemo
 * Description
 * Create by Jason
 * Date 2020/7/23 12:18
 * <p>
 * volatile cas atomicInteger blockqueue 线程交互  原子引用
 */
public class ProducerAndCousumer_BlockDemo {
    public static void main(String[] args) throws InterruptedException {

        MyResource ms = new MyResource(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            try {
                ms.myProducer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Producer").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            try {
                ms.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "consumer").start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("时间到,停止活动");
        ms.stop();

    }
}
