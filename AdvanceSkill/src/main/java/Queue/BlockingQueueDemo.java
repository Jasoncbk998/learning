package Queue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ClassName BlockingQueueDemo
 * Description
 * Create by Jason
 * Date 2020/7/23 9:23
 * ArrayBlockingQueue  基于数组解耦股的有界阻塞队列,此队列按照FIFO 先进先出的原则对元素进行排序
 * linkedBlockingQueue 基于链表结构的阻塞队列,按照FIFO先进先出排序元素,吞吐量通常高于ArrayBlockingQueue
 * SynchronousQueue 不存储元素的阻塞队列,没个插入操作必须等到领英线程调用移除操作,否则插入操作一直处于阻塞状态,吞吐量同行高于linkedBlockingQueue
 * <p>
 * 1. 队列  先到先得
 * <p>
 * 2 阻塞队列
 * 优点:
 * <p>
 * <p>
 * 不得不阻塞的时候,怎么管理:
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws Exception {
        BlockingQueueDemo block = new BlockingQueueDemo();

    }

    //阻塞
    public void method2() throws InterruptedException {
        BlockingQueue<String> block = new ArrayBlockingQueue<String>(3);//容量是3
        block.put("a");
        block.put("b");
        block.put("c");
        System.out.println("等待插入d");
//        block.put("d");//队列满,则等待
        block.offer("d", 2, TimeUnit.SECONDS);
        System.out.println("成功插入d");


        block.take();//取,如果取完所有元素,就会等待 ,一直等待,直到取到元素


    }

    //抛出异常,返回成功或者失败
    public void method1() {
        //队列满,在添加元素则抛出异常
        BlockingQueue<String> block = new ArrayBlockingQueue<String>(3);//容量是3
        block.add("a");// 布尔型
        block.add("b");
        block.add("c");
//        String remove = block.remove();  //移除a,先进先出
//        String element = block.element(); // 队列排头 是b  队首是谁

//        boolean a = block.offer("a"); //false 插入元素成功true  失败则false
//
//        block.peek();//队首的元素是什么
//        String poll = block.poll();//移除队首元素
//        block.peek();//队首元素
    }
}
