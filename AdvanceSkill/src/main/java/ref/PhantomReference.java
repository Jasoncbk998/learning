package ref;

import java.lang.ref.ReferenceQueue;

/**
 * ClassName PhantomReference
 * Description
 * Create by Jason
 * Date 2020/7/26 11:34
 */
public class PhantomReference {
    public static void main(String[] args) throws InterruptedException {

        Object a = new Object();
        ReferenceQueue<Object> ref = new ReferenceQueue<>();
        java.lang.ref.PhantomReference<Object> phant = new java.lang.ref.PhantomReference<>(a, ref);
        System.out.println(a);
        System.out.println(phant.get());
        System.out.println(ref.poll());
        System.out.println("-----");

        a=null;
        System.gc();
        Thread.sleep(500);
        System.out.println(a);
        System.out.println(phant.get());
        System.out.println(ref.poll());
        /**
         * java.lang.Object@1b6d3586
         * null
         * null
         * -----
         * null
         * null
         * java.lang.ref.PhantomReference@4554617c
         */


    }
}
