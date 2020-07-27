package JUC.saleDemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName saleticket
 * Description
 * Create by Jason
 * Date 2020/7/24 10:41
 */
public class Ticket {
    private int number = 30;
    //可重入锁
    Lock lock = new ReentrantLock();

    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "\t 卖出第:" + (number--) + "票  \t 还剩下:" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
