package NoSafeCollection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * ClassName SetNoSafeDemo
 * Description
 * Create by Jason
 * Date 2020/7/22 12:51
 * <p>
 * hashset不安全
 */
public class SetAndMap_NoSafeDemo {
    public static void main(String[] args) {

        SetAndMap_NoSafeDemo test = new SetAndMap_NoSafeDemo();
      test.concurrentMap();

    }
    public void concurrentMap(){
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= 15; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(1, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * 线程不安全
     */
    public void mapUnSafe() {
        HashMap<String, String> map = new HashMap<>();
        for (int i = 1; i <= 15; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(1, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * public CopyOnWriteArraySet() {
     * al = new CopyOnWriteArrayList<E>();
     * }
     * <p>
     * 还是CopyOnWriteArrayList
     */
    public void setCopyOnWrite() {
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
        for (int i = 1; i <= 15; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(1, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    public void setSync() {
        Set<String> set = Collections.synchronizedSet(new HashSet<String>());
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(1, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    /**
     * hashSet底层是 hashmap
     * 但是hashmap是kv
     * 那么hashset?
     * public boolean add(E e) {
     * return map.put(e, PRESENT)==null;
     * }
     * <p>
     * K 就是e
     * v就是PERSEN常量
     * private static final Object PRESENT = new Object();
     */
    public void set() {
        HashSet<String> set = new HashSet<>();
        for (int i = 1; i <= 15; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(1, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
