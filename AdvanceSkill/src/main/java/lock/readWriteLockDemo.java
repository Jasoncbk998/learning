package lock;



/**
 * ClassName readWriteLockDemo
 * Description
 * Create by Jason
 * Date 2020/7/22 15:39
 * 多个线程同时操作一个资源类没有任何问题,所以为了满足并发量,读取共享资源应该可以同时进行
 * 但是如果一个线程想要去写共享资源,就不应该有其他线程可以对资源进行读或写
 *
 * 读 读 可以共存
 * 读 写  不可以共存
 * 写 写 不可以共存
 * 写操作  原子+独占  整个过程必须是一个完成的统一整体,中间不允许被分割被打断
 *
 * 打印结果    可以看到 写入没有被打断,读取可以打断
 */
public class readWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 1; i <= 5; i++) {
            final int tempint = i;
            new Thread(() -> {
                myCache.put(tempint + "", tempint);
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {
            int tempint = i;
            new Thread(() -> {
                myCache.get(tempint + "");
            }, String.valueOf(i)).start();
        }
    }
}
