import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Classname test
 * @Description TODO
 * @Date 2020/8/10 10:40 上午
 * @Created by jason
 */
public class test {
    @Test
    public void map() {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1,"a");
        map.put(3,"a");
        map.put(2,"a");
        Set<Map.Entry<Integer, String>> entries = map.entrySet();
        for (Map.Entry<Integer, String> entry : entries) {
            System.out.println(entry.getValue());
        }



    }
}
