package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ClassName SnipLockDemo
 * Description
 * Create by Jason
 * Date 2020/7/22 15:03
 * <p>
 * 实现自旋锁
 * 好处:循环比较获取直到成功为止,没有类似wait的阻塞
 * <p>
 * 通过CAS操作完成自旋锁,A线程先进来调用mylock方法自己持有锁5秒钟,B随后近来发现当前有线程持有锁,不是null,所以只能通过自旋等待,直到A释放锁后B随后抢到
 */
public class SnipLockDemo {
    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    public static void main(String[] args) {
        SnipLockDemo demo = new SnipLockDemo();
        new Thread(() -> {
            demo.mylock();
            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
            demo.unmylock();
        }, "AA").start();
//        try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
        new Thread(() -> {
            demo.mylock();
//            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
            demo.unmylock();
        }, "BB").start();

        new Thread(() -> {
        demo.mylock();
//        try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
        demo.unmylock();
    }, "CC").start();
    }

    public void mylock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t com in yes");
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println("进入while,线程上锁:"+Thread.currentThread().getName());
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }
    public void unmylock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t invoked un my lock");

    }
}
