package Queue.syncAndLock;

/**
 * ClassName SyncAndReentrantLockDemo
 * Description
 * Create by Jason
 * Date 2020/7/23 12:03
 *
 * 多线程之间相互调用,实现 A->B->C  三个线程启动,要求如下
 *
 * AA打印5次,BB打印10次,CC打印5次
 * ....
 * 重复10次
 *
 */
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResource sr = new ShareResource();
        new Thread(()->{
            for(int i=1;i<=10;i++){
                sr.print5();
            }
        },"AAA").start();

        new Thread(()->{
            for(int i=1;i<=10;i++){
                sr.print10();
            }
        },"BBB").start();
        new Thread(()->{
            for(int i=1;i<=10;i++){
                sr.print15();
            }
        },"CCC").start();

    }
}
