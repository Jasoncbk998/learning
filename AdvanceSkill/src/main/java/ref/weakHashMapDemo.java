package ref;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * ClassName weakHashMapDemo
 * Description
 * Create by Jason
 * Date 2020/7/26 10:56
 */
public class weakHashMapDemo {
    public static void main(String[] args) {
        myHashMap();
        /**
         * {1=hashmap}
         * {1=hashmap}
         * {1=hashmap}	 1
         */
        System.out.println("------");
        myweakhashmap();
        /**
         * {2=aaaaa}
         * {2=aaaaa}
         * {}	 0
         */
    }
    private static void myweakhashmap() {
        WeakHashMap<Integer, String> map = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "aaaaa";
        map.put(key, value);
        System.out.println(map);
        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map + "\t " + map.size());
    }

    private static void myHashMap() {
        HashMap<Integer, String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "hashmap";
        map.put(key, value);
        System.out.println(map);
        key = null;
        System.out.println(map);
        System.gc();
        System.out.println(map + "\t " + map.size());
    }
}
