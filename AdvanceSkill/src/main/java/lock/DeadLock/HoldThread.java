package lock.DeadLock;

import java.util.concurrent.TimeUnit;

/**
 * ClassName HoldThread
 * Description
 * Create by Jason
 * Date 2020/7/24 10:05
 */
public class HoldThread implements Runnable {
    private String lockA;
    private String lockB;

    public HoldThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有锁:" + lockA + "尝试获得:" + lockB);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有锁:" + lockB + "尝试获得:" + lockA);
            }
        }
    }
}
