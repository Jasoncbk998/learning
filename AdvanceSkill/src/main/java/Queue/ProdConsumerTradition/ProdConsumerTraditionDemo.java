package Queue.ProdConsumerTradition;

/**
 * ClassName ProdConsumerTraditionDemo
 * Description 一个初始值为0的变量 两个线程交替操作 一个加1 一个减1来5轮
 * Create by Jason
 * Date 2020/7/23 10:24
 * <p>
 * 线程  操作   资源类
 * 判断  干活    通知
 * 防止虚假唤醒
 * AA	1
 * BB	0
 * AA	1
 * BB	0
 * AA	1
 * BB	0
 * AA	1
 * BB	0
 * AA	1
 * BB	0
 */
public class ProdConsumerTraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    shareData.deIncrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }
}
