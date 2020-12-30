package _05_初始化与清理;

/**
 * @Classname _7_1_初始化顺序
 * @Description TODO
 * @Date 2020/9/24 9:04 下午
 * @Created by jason
 */
class window {
    window(int marker) {
        System.out.println("window (" + marker + ")");
    }
}

class house {
    window w1 = new window(1);

    house() {
        System.out.println("house");
        w3 = new window(3);
    }

    window w2 = new window(2);

    void f() {
        System.out.println("f()");
    }

    window w3 = new window(3);

}

public class _7_1_初始化顺序 {
    public static void main(String[] args) {
        house house = new house();
        house.f();
    }
}
