package lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName lockDemo
 * Description
 * Create by Jason
 * Date 2020/7/22 13:28
 * 可重入锁(递归锁)
 * 指的是同一线程外层函数获得锁之后,内层递归函数仍然能获取该锁的代码
 * 在同一个线程在外层方法获取锁的时候,在进入内层方法会自动获取锁
 *
 * 也就是说  线程可以进入任何一个他已经拥有的锁所同步着的代码块
 *
 *
 * t1	 sendSms
 * t1	 sendEmail:
 * t2	 sendSms
 * t2	 sendEmail:
 *
 * 证明了 synchronized 是可重入锁
 *
 *
 */
public class ReenterLockDemo {
    public static void main(String[] args) {
        ReenterLockDemo lock = new ReenterLockDemo();
//        lock.rennterLock();
        lock.sync();
    }

    /**
     * 验证ReentrantLock 是可重入锁
     * Thread-1	 get
     * Thread-1	 set
     * Thread-0	 get
     * Thread-0	 set
     */
    public void rennterLock(){
        Phone2 phone = new Phone2();
        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        t3.start();
        t4.start();
    }


    /**
     * 验证sync是可重入锁
     * t1	 sendSms  t1线程在外层方法获取锁的时候
     * t1	 sendEmail: t1在进入内层方法会自动获取锁
     * t2	 sendSms
     * t2	 sendEmail:
     */
    public void sync(){
        Phone phone = new Phone();
        new Thread(()->{
            try {
                phone.sendSms();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(()->{
            try {
                phone.sendSms();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2").start();

    }

}



