package lock;


import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName Phone
 * Description
 * Create by Jason
 * Date 2020/7/22 14:11
 */
public class Phone2 implements Runnable{
    private Lock lock= new ReentrantLock();
    private void get(){
        //lock要配对,一个lock  一个unlock
        lock.lock();
//        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t get");
            set();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
//            lock.unlock();
        }
    }

    private void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t set");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        get();
    }
}
