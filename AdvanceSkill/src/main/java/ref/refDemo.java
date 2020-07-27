package ref;

/**
 * ClassName refDemo
 * Description
 * Create by Jason
 * Date 2020/7/25 18:23
 */
public class refDemo {
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = obj1;
        obj1 = null;
        System.gc();
        System.out.println(obj2);//java.lang.Object@1b6d3586

    }
}
