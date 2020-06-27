import java.util.concurrent.CopyOnWriteArrayList;

public class arryTest {
    public static void main(String[] args) {

/**
 * 写时复制
 * 读写分离的思想
 *
 *  理解写时复制的概念
 *
 *  在我操作数组时,为了避免线程间抢占,一个线程写完后,进行复制到第二个线程去继续写入
 *
 * 拿到数组
 * Object[] elements = getArray();
 * 获取长度
 *  int len = elements.length;
 *  进行复制,长度+1, 然后放入新的元素e
 *  Object[] newElements = Arrays.copyOf(elements, len + 1);
 *  把e元素,加到索引为len的位置
 *  newElements[len] = e;
 *  放到array里
 *  setArray(newElements);
 *
 *
 *
 *
 */
        CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<String>();


    }
}
