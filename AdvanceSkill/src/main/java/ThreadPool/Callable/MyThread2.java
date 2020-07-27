package ThreadPool.Callable;

import java.util.concurrent.Callable;

/**
 * ClassName MyThread
 * Description
 * Create by Jason
 * Date 2020/7/23 12:50
 */
public class MyThread2 implements Callable<Integer> {



    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"\t come in");
        return 1024;
    }
}
