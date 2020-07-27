package CountDownLatch;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * ClassName SemaphoreDemo
 * Description
 * Create by Jason
 * Date 2020/7/22 17:53
 * 1	 抢到车位
 * 3	 抢到车位
 * 2	 抢到车位
 * 2	 停3秒离开车位
 * 3	 停3秒离开车位
 * 1	 停3秒离开车位
 * 5	 抢到车位
 * 4	 抢到车位
 * 6	 抢到车位
 * 4	 停3秒离开车位
 * 6	 停3秒离开车位
 * 5	 停3秒离开车位
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //3个车位
        Semaphore sp = new Semaphore(3);
        //6个汽车
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    sp.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t 抢到车位");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "\t 停3秒离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    sp.release();
                }
            }, String.valueOf(i)).start();
        }

    }
}
