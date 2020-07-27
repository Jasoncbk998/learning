package CountDownLatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * ClassName CyclicBarrier
 * Description
 * Create by Jason
 * Date 2020/7/22 17:39
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyc = new CyclicBarrier(7, () -> {
            System.out.println("ok");
        });
        for(int i = 1 ; i <=7;i++){
            final int temp=i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 第:"+temp);
                try {
                    cyc.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
