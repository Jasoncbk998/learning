package AtomicReference;

import java.util.concurrent.atomic.AtomicReference;

/**
 * ClassName AtomicReferenceDemo
 * Description
 * Create by Jason
 * Date 2020/7/21 19:46
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomic = new AtomicReference<User>();
        User zs = new User("zhangsan", 10);
        User ls = new User("lisi", 20);
        User ww = new User("wangwu", 30);
        atomic.set(zs);

        System.out.println(atomic.compareAndSet(zs, ls) + "\t" + atomic.get().toString());//true	User{name='lisi', age=20}
        System.out.println(atomic.compareAndSet(ls, ww) + "\t" + atomic.get().toString());//true	User{name='wangwu', age=30}

    }
}


class User {
    private String name;
    private int age;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
