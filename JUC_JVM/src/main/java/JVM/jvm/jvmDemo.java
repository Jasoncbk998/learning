package JVM.jvm;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

/**
 * ClassName jvmDemo
 * Description
 * Create by Jason
 * Date 2020/7/24 13:06
 */
public class jvmDemo {
    public static void main(String[] args) {
        //jdk自带的,他走的类装载器是 启动类加载器（Bootstrap）
        //为什么直接可以用object  因为装好jdk时,object已经就给加载进去了  在jre的rt.jar中
        Object o = new Object();
        System.out.println(o.getClass().getClassLoader());
//        System.out.println(o.getClass().getClassLoader().getParent());
//        System.out.println(o.getClass().getClassLoader().getParent().getParent());

        //自己编写的   类装载器走的是 AppClassLoader
        jvmDemo jvmDemo = new jvmDemo();
        System.out.println(jvmDemo.getClass().getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(jvmDemo.getClass().getClassLoader().getParent());//sun.misc.Launcher$ExtClassLoader@1b6d3586
        System.out.println(jvmDemo.getClass().getClassLoader().getParent().getParent());//null



        Thread t = new Thread();
        t.start();
    }


}
