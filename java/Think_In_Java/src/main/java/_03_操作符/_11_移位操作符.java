package _03_操作符;

/**
 * @Classname _11_移位操作符
 * @Description TODO
 * @Date 2020/9/23 7:52 下午
 * @Created by jason
 */
public class _11_移位操作符 {
    public static void main(String[] args) {
        int i = 10;
        String s = Integer.toBinaryString(10);
        System.out.println(s);//1010
        /**
         * 位运算可以替代乘除
         */
        int a= 4;
        System.out.println(a>>1);//2
        System.out.println(a>>2);//1
        System.out.println(a<<1);//8
        System.out.println(a<<2);//16

    }
}
