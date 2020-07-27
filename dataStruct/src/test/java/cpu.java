/**
 * ClassName cpu
 * Description
 * Create by Jason
 * Date 2020/7/25 11:11
 */
public class cpu {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());//8
        long maxMemory = Runtime.getRuntime().maxMemory();//返回 Java 虚拟机试图使用的最大内存量。
        long totalMemory = Runtime.getRuntime().totalMemory();//返回 Java 虚拟机中的内存总量。
        System.out.println("MAX_MEMORY = " + maxMemory + "（字节）、" + (maxMemory / (double) 1024 / 1024) + "MB");
        System.out.println("TOTAL_MEMORY = " + totalMemory + "（字节）、" + (totalMemory / (double) 1024 / 1024) + "MB");
        /**
         * MAX_MEMORY = 7613186048（字节）、7260.5MB  jvm占用总内存
         * TOTAL_MEMORY = 514850816（字节）、491.0MB 堆内存
         */



    }

}
