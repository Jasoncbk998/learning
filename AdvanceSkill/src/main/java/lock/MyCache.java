package lock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * ClassName MyCache
 * Description
 * Create by Jason
 * Date 2020/7/22 15:39
 */
public class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock relock = new ReentrantReadWriteLock();

    /**
     * 写
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        relock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入" + key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 正在完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            relock.writeLock().unlock();
        }
    }
    /**
     * 读
     *
     * @param key
     */
    public void get(String key) {
        relock.readLock().lock();
        try {

            System.out.println(Thread.currentThread().getName() + "\t 正在读取");
            //模拟网络延迟
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Object value = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 正在完成" + value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            relock.readLock().unlock();
        }
    }
    public void clearCaChe() {
        map.clear();
    }
}
