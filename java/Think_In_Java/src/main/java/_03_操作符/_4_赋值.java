package _03_操作符;

/**
 * @Classname _3_4_赋值
 * @Description TODO
 * @Date 2020/9/23 7:16 下午
 * @Created by jason
 */
public class _4_赋值 {


    public static void main(String[] args) {

        person p1 = new person();
        person p2 = new person();
        p1.age = 10;
        p2.age = 20;
        System.out.println("NO1   p1.age=" + p1.age + "\t" + "p2.age=" + p2.age);

        //只是数值引用改变,并不改变p1和p2的地址值
        p1.age = p2.age;
        System.out.println("NO2   p1.age=" + p1.age + "\t" + "p2.age=" + p2.age);


//      当p1=p2 那么p2的对象引用给了p1,也就是说此时的p1和p2是一个对象了
        p1 = p2;//这个赋值时对 '对象'的引用
        //此时p1和p2的地址值是p2的,所以这个时候不管对p1和p2,无论对谁修改,都会同时改变二者的age
        p2.age = 30;
//        p1.age=44;
        System.out.println("NO3   p1.age=" + p1.age + "\t" + "p2.age=" + p2.age);


    }
}
