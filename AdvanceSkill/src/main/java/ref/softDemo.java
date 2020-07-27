package ref;

import java.lang.ref.SoftReference;

/**
 * ClassName softDemo
 * Description
 * Create by Jason
 * Date 2020/7/25 18:28
 */
public class softDemo {
    public static void main(String[] args) {
        soft_Ref_Mem_Enough();
    }

    /**
     * 产生大对象,让内存不够用,
     *  -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    public static void soft_Ref_Mem_Enough() {
        Object o = new Object();
        SoftReference<Object> soft_o = new SoftReference<>(o);
        System.out.println(o);
        System.out.println(soft_o.get());
        o=null;

        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println(o);//java.lang.ref.SoftReference@1b6d3586
            System.out.println(soft_o.get());//java.lang.Object@1b6d3586
        }
    }

    public static void soft_Ref_Mem_NoEnough() {
        Object o = new Object();
        SoftReference<Object> soft_o = new SoftReference<>(o);
        System.out.println(o);//java.lang.ref.SoftReference@1b6d3586
        System.out.println(soft_o.get());//java.lang.Object@1b6d3586
        o=null;
        System.gc();
        //内存够用
        System.out.println(o);//null
        System.out.println(soft_o.get());//java.lang.Object@1b6d3586
    }
}
