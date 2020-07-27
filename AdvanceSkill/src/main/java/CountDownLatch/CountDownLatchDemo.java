package CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * ClassName CountDownLatchDemo
 * Description
 * Create by Jason
 * Date 2020/7/22 16:48
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo c = new CountDownLatchDemo();
        c.countCountryPuls();
    }

    public void countCountryPuls() throws InterruptedException {
        // 6个数字
        CountDownLatch count = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t ok");
                count.countDown();
            }, CountryEnum.forEach(i).getName()).start();
        }
        count.await();
        System.out.println(Thread.currentThread().getName() + "\t go");
    }

    public void countNumber() throws InterruptedException {
        // 6个数字
        CountDownLatch count = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t ok");
                count.countDown();
            }, String.valueOf(i)).start();
        }
        count.await();
        System.out.println(Thread.currentThread().getName() + "\t go");
    }
}
