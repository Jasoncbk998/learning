package ref;

import java.lang.ref.WeakReference;

/**
 * ClassName weakRef
 * Description
 * Create by Jason
 * Date 2020/7/25 18:38
 */
public class weakRef {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weak = new WeakReference<>(o1);
        System.out.println(o1);//java.lang.Object@1b6d3586
        System.out.println(weak.get());//java.lang.Object@1b6d3586
        o1=null;
        System.gc();
        System.out.println(o1);//null
        System.out.println(weak.get());//null
    }
}
