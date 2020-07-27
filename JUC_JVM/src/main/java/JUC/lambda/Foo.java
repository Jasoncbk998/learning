package JUC.lambda;

/**
 * ClassName Foo
 * Description
 * Create by Jason
 * Date 2020/7/24 12:10
 *
 * 多个default
 */
@FunctionalInterface
public interface Foo {
//    public void sayHello();
//    public int add1(int x, int y);
    public int add(int x, int y);
    //java 8  接口中允许有实现

    default int div(int x,int y) {
        return x/y;
    }
    default int div1(int x,int y) {
        return x/y;
    }

    public static int mv1(int x,int y){
        return x*y;
    };
    public static int mv2(int x,int y){
        return x*y;
    };
}
