package _05_初始化与清理;

/**
 * @Classname test2
 * @Description TODO
 * @Date 2020/9/24 9:31 下午
 * @Created by jason
 */
public class test2 {
    static int i;
    static {
        String s="a";
        System.out.println("a");
    }

    /**
     * 类内容（静态变量、静态初始化块） => 实例内容（变量、初始化块、构造器）
     * @param args
     */
    public static void main(String[] args) {
        test2 test2 = new test2();

    }
}
