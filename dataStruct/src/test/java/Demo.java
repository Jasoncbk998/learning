/**
 * @Classname test
 * @Description TODO
 * @Date 2020/8/6 10:11 上午
 * @Created by jason
 */

public class Demo {
    class Super {
        int flag = 1;

        Super() {
            test();
        }

        void test() {
            System.out.println("Super.test() flag=" + flag);
        }
    }

    class Sub extends Super {
        Sub(int i) {
            flag = i;
            System.out.println("Sub.Sub()flag=" + flag);
        }

        void test() {
            System.out.println("Sub.test()flag=" + flag);
        }
    }

    public static void main(String[] args) {
        new Demo().new Sub(5);
    }
}

