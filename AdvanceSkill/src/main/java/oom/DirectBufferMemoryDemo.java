package oom;

import java.nio.ByteBuffer;

/**
 * ClassName DirectBufferMemoryDemo
 * Description
 * Create by Jason
 * Date 2020/7/26 12:50
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的maxDirectMemory:"+(sun.misc.VM.maxDirectMemory()/(double)1024/1024)+"MB");
        try {
         Thread.sleep(3000);
            ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
