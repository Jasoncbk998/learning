package ThreadPool.Callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * ClassName CallableDemo
 * Description
 * Create by Jason
 * Date 2020/7/23 12:49
 * <p>
 * 线程中,获得多线程的方式
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableDemo cl = new CallableDemo();
        cl.base();
    }

    public void base() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task1 = new FutureTask<>(new MyThread2());
        FutureTask<Integer> task2 = new FutureTask<>(new MyThread2());
        //如果多个线程共用一个task,则不会重复计算,会复用
        //如果想重复计算,那么就要创建多个FutureTask
        Thread bbb = new Thread(task1, "bbb");
        Thread aaa = new Thread(task1, "aaa");

        bbb.start();
        aaa.start();
        Integer integer = task1.get();
        System.out.println(integer);
    }
}
