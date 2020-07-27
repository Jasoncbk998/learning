package lock.DeadLock;

/**
 * ClassName DeadLockDemo
 * Description
 * Create by Jason
 * Date 2020/7/24 10:04
 * 死锁是指两个或者两个以上的进程在执行过程中
 * 因为争夺资源造成一种互相等待的现象
 * 吃着碗里的,想着锅里的
 * */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldThread(lockA, lockB), "threadAAA").start();
        new Thread(new HoldThread(lockB, lockA), "threadBBB").start();

    }
}
