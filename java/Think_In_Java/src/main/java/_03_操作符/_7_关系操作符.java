package _03_操作符;

/**
 * @Classname _7_关系操作符
 * @Description TODO
 * @Date 2020/9/23 7:34 下午
 * @Created by jason
 */


public class _7_关系操作符 {
    public static void main(String[] args) {
        /**
         * java中类都是继承了object
         * object的equals是比较地址值
         */
        person p1 = new person();
        person p2 = new person();
        p1.age = 10;
        p2.age = 10;
        System.out.println(p1.equals(p2));//false
        /**
         * integer重写了object的equals,比较的是数值而不是地址值
         */
        Integer a1 = new Integer(10);
        Integer a2 = new Integer(10);
        System.out.println(a1 == a2);//fasle
        System.out.println(a1.equals(a2));//true


    }

}
