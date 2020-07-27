package oom;

/**
 * ClassName ubableThreadDemo
 * Description
 * Create by Jason
 * Date 2020/7/26 13:03
 */
public class ubableThreadDemo {
    public static void main(String[] args) {
        for (int i = 1; ; i++) {
            System.out.println("-------------i" + i);
            new Thread(() -> {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "" + i).start();
        }
    }
}
