package NoSafeCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ClassName ArrayListNoSafe
 * Description
 * Create by Jason
 * Date 2020/7/22 11:14
 * <p>
 * 线程不安全
 * 为什么说list的是不安全的,因为他的add,添加元素的方法没有加锁等多线程的下的保护措施
 * 所以是线程不安全的,写操作为了保证并发性和效率没有加锁
 * 导致原因是  并发争抢修改,一个线程正在写,另外一个线程进来抢夺
 * 导致数据不一致,也就是并发修改异常
 * <p>
 * new Vector<>()
 * Collections.synchronizedList(new ArrayList<>());
 * new CopyOnWriteArrayList<>();
 */
public class ArrayListNoSafe {
    public static void main(String[] args) {
        ArrayListNoSafe list = new ArrayListNoSafe();
        list.copyOnWrite();
    }

    /**
     * 最佳实践
     * 写时复制,读写分离的思想
     add(E e)  e是添加进来的元素
         Object[] elements = getArray(); //获取原先所有的元素
         int len = elements.length;//获取长度
         Object[] newElements = Arrays.copyOf(elements, len + 1);//复制
         newElements[len] = e;//添加元素
         setArray(newElements);
     */
    public void copyOnWrite() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(1, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
    /**
     * 解决方案
     */
    public void syncList() {

        List<String> list = Collections.synchronizedList(new ArrayList<String>());
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(1, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
    /**
     * 验证多线程下,list线程不安全
     * [e82bb26c, 4e52b1c1]
     * [e82bb26c, 4e52b1c1]
     * [e82bb26c, 4e52b1c1]
     * 会造成并发修改异常 ConcurrentModificationException
     *
     */
    //java.util.ConcurrentModificationException  多线程下的报错
    public void arrayList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
