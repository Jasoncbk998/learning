package oom;

import java.util.ArrayList;

/**
 * ClassName GCoverHeadDemo
 * Description
 * Create by Jason
 * Date 2020/7/26 12:32
 */
public class GCoverHeadDemo {
    public static void main(String[] args) {

        int i = 0;
        ArrayList<String> list = new ArrayList<>();
        /**
         * 将 内存调小
         * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
         */
        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Throwable e) {
            System.out.println("i+++++++++:" + i);
            e.printStackTrace();
            throw e;
        }
    }
}
