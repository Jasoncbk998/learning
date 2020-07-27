package ThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName myTreadPool
 * Description
 * Create by Jason
 * Date 2020/7/23 14:05
 * <p>
 * int i = Runtime.getRuntime().availableProcessors();//查看核心数量
 * System.out.println(i);
 * <p>
 * <p>
 * 通过线程池获取线程
 */
public class myTreadPool {
    public static void main(String[] args) {
        //一个池子 5个线程
//        ExecutorService pool = Executors.newFixedThreadPool(5);//一池 5线程
//        ExecutorService pool = Executors.newSingleThreadExecutor();//一池 一线程
        ExecutorService pool = Executors.newCachedThreadPool();//一个池  多个线程
        //模拟 10个用户来办理业务,每个用户就是一个外部请求线程
        try {
            //10个请求由5个线程处理
            for (int i =1; i<=10;i++){
                pool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pool.shutdown();
        }








    }
}
