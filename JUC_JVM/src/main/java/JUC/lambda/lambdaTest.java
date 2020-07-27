package JUC.lambda;

/**
 * ClassName lambdaTest
 * Description
 * Create by Jason
 * Date 2020/7/24 12:03
 * <p>
 * 函数式编程
 */
public class lambdaTest {
    public static void main(String[] args) {

        Foo foo = new Foo() {
//            @Override
//            public void sayHello() {
//                System.out.println("hello");
//            }

            @Override
            public int add(int x, int y) {
                return x + y;
            }
        };
//        foo.sayHello();
        System.out.println(foo.add(1, 2));
        System.out.println("------");

        Foo foo1 = (x, y) -> {
//            System.out.println("Hello!! lambda !!");
            int i = x + y;
            return i;
        };
        foo1.div1(1, 4);
        /**
         * 静态
         */
        Foo.mv1(1,2);
        Foo.mv2(1,2);
        System.out.println(foo1.add(1, 2));

    }
}
