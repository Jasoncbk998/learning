/**
 * @Classname testdouble
 * @Description TODO
 * @Date 2020/7/29 4:14 下午
 * @Created by jason
 */
public class testdouble {
    public static void main(String[] args) {
        Double i1 = 100.0;
        Double i2 = 100.0;
        Double i3 = 200.0;
        Double i4 = 200.0;
        boolean b = i1 == i2;
        System.out.println(i1.hashCode());
        System.out.println(i2.hashCode());
        System.out.println(i1.equals(i2));
        System.out.println(i1==i2);
        System.out.println(i3==i4);
    }
}
