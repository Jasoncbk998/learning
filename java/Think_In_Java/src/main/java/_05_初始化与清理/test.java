package _05_初始化与清理;

/**
 * @Classname test
 * @Description TODO
 * @Date 2020/9/24 9:15 下午
 * @Created by jason
 */
public class test {
    static int i;
    static  class C {
        C(){
            i=1;
            System.out.print("C");
        }
    }

   static  class X {
        Y b = new Y();

        X() {
            System.out.print("X");
        }
    }

    static class Y {
       C c=new C();
        Y() {
            System.out.print("Y");
        }
    }

    public static class Z extends X {
//        Y y = new Y();

        Z() {
            System.out.print("Z");
        }

    }

    /**
     * 初始化
     * 首先是static  只加载一次
     * 这个可以看java编程思想 P95 5.7.2
     * 首先是变量,都会先进行初始化
     * 然后才是构造
     *
     * @param args
     */
    public static void main(String[] args) {
        new Z();     //结果是 CYXZ
    }

}