package ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * ClassName ReferenceQueueDemo
 * Description
 * Create by Jason
 * Date 2020/7/26 11:12
 */
public class ReferenceQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        ReferenceQueue<Object> refQue = new ReferenceQueue<>();
        WeakReference<Object> map = new WeakReference<Object>(a, refQue);
        System.out.println(a);
        System.out.println(map.get());
        System.out.println(refQue.poll());
        a=null;
        System.gc();
        Thread.sleep(500);
        System.out.println("---");
        System.out.println(a);
        System.out.println(map.get());
        System.out.println(refQue.poll());
        /**
         java.lang.Object@1b6d3586
         java.lang.Object@1b6d3586
         null
         ---
         null
         null
         java.lang.ref.WeakReference@4554617c
         */
    }
}

