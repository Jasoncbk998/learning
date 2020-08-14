import org.junit.Test;

/**
 * @Classname string
 * @Description TODO
 * @Date 2020/8/6 3:15 下午
 * @Created by jason
 */

public class stringtest {
    @Test
    public void test1() {
        int num = 1;
        Integer num1 = new Integer(1);
        System.out.println(num == num1);//true  地址值相同
        String aa = "aa";
        String aa1 = new String("aa");
        int i = aa.hashCode();
        int i1 = aa1.hashCode();
        System.out.println("i: \t" + i);//3104
        System.out.println("i1: \t" + i1);//3104
        System.out.println(aa1 == aa);//但是new string创建一个新的对象,这个对象指向了aa,所以false
    }
}
