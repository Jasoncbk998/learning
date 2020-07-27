package oom;

import java.util.Random;

/**
 * ClassName GCDemo
 * Description
 * Create by Jason
 * Date 2020/7/26 18:53
 *
 *
 */
public class GCDemo {
    public static void main(String[] args) {
        System.out.println("模拟GC");
        try {
            String str = "aaa";
            while(true){
                 str = str + new byte[1024*1024*4];
                 str.intern();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }



    }
}
