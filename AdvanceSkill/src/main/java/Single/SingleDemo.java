package Single;

/**
 * ClassName SingleDemo
 * Description
 * Create by Jason
 * Date 2020/7/21 16:05
 *
 * 单例模式的DCL代码
 */
public class SingleDemo {
    private static SingleDemo instance = null;

    private SingleDemo() {
        System.out.println(Thread.currentThread().getName() + "\t 我是构造方法SingleDemo()");
    }

    //加synchronized  太笨重
    // DCL 双端检锁机制   double check lock
    public static SingleDemo getInstance() {
        if (instance == null) {
            //加锁的前后都进行判断
            synchronized (SingleDemo.class) {
                if (instance == null) {
                    instance = new SingleDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                SingleDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
    /**
     * 1	 我是构造方法SingleDemo()
     * 4	 我是构造方法SingleDemo()
     * 3	 我是构造方法SingleDemo()
     * 2	 我是构造方法SingleDemo()
     * 我们发现  只有4个
     */
}
