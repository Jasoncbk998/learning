package JUC.saleDemo;

/**
 * ClassName SaleTicketDemo
 * Description
 * Create by Jason
 * Date 2020/7/24 10:37
 * <p>
 * 高内聚低耦合
 */
public class SaleTicketDemo {
    public static void main(String[] args) {
        //main 主线程
        //一共四个线程
//        Thread t1 = new Thread();
//        Thread t2 = new Thread();
//        Thread t3 = new Thread();
        Ticket t = new Ticket();
        //匿名内部类  使用lmbada表达式
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 1; i <= 10; i++) {
//                    t.sale();
//                }
//            }
//        }, "A").start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 1; i <= 10; i++) {
//                    t.sale();
//                }
//            }
//        }, "B").start();


        new Thread(() -> {
            t.sale();
        }, "AAA").start();

        new Thread(() -> {
            t.sale();
        }, "BBB").start();
    }
}
