import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * hashSet线程不安全
 */
public class setTest {
    public static void main(String[] args) {


        CopyOnWriteArraySet<String> set =   new CopyOnWriteArraySet<String>() ; // (HashSet<String>) Collections.synchronizedSet(new HashSet<String>()) //new HashSet<String>();

        for (int i = 1; i <= 30; i++) {
                new Thread(()->{
                    set.add(UUID.randomUUID().toString().substring(0,8));
                    System.out.println(set);
                },String.valueOf(i)).start();

        }
    }
}