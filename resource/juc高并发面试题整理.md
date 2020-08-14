前置知识

jvm

juc

package java.util.concurrent

# 1 volatile的理解

## 1.1 .volatile是Java虚拟机提供的轻量级的同步机制

- 保证可见性
- 不保证原子性(重要)下边介绍了这个,代码也有示例
- 禁止指令重排: 使用volatile避免指令重排,告诉编译器我不需要你来给我做优化

## 1.2 谈一下JMM

了解volatile之前,需要了解一下JMM

JMM(Java内存模型Java Memory Model,简称JMM)本身是一种抽象的概念 并不真实存在,它描述的是一组规则或规范通过规范定制了程序中各个变量(包括实例字段,静态字段和构成数组对象的元素)的访问方式.

JMM关于同步规定:

```
1.线程解锁前,必须把共享变量的值刷新回主内存
2.线程加锁前,必须读取主内存的最新值到自己的工作内存
3.加锁解锁是同一把锁
```

仔细读

**由于JVM运行程序的实体是线程,而每个线程创建时JVM都会为其创建一个工作内存(有些地方成为栈空间),工作内存是每个线程的私有数据区域,而Java内存模型中规定所有变量都存储在主内存,主内存是共享内存区域,所有线程都可访问,但线程对变量的操作(读取赋值等)必须在工作内存中进行,首先要将变量从主内存拷贝到自己的工作空间,然后对变量进行操作,操作完成再将变量写回主内存,不能直接操作主内存中的变量,各个线程中的工作内存储存着主内存中的变量副本拷贝,因此不同的线程无法访问对方的工作内存,此案成间的通讯(传值) 必须通过主内存来完成,其简要访问过程如下图:**

![image-20200721130053871](assets\image-20200721130053871.png)

主内存就是相当于我们电脑中的8g内存,16g内存,这个就是主内存,它是一个硬件的概念,当我们new一个对象出来,这个对象就存在主内存中

此时模拟多线程,多个线程去主内存中,拷贝要修改的值,拷贝到自己的工作内存

![image-20200721130907949](assets\image-20200721130907949.png)

如图,student对象,age的值原本是25,但是现在有三个线程分别去在主内存中拷贝走student对象的age值,其中t1线程在自己的工作内存中对该值进行了修改,并且写回主物理内存中的age值,此时其他的两个线程要马上知道这个操作,t2和t3线程知晓主物理内存中的值已经被修改,会去从新拷贝,这个就是JMM的可见性





1. #### **可见性**

通过前面对JMM的介绍,我们知道
各个线程对主内存中共享变量的操作都是各个线程各自拷贝到自己的工作内存操作后再写回主内存中的.
这就可能存在一个线程AAA修改了共享变量X的值还未写回主内存中时 ,另外一个线程BBB又对内存中的一个共享变量X进行操作,但此时A线程工作内存中的共享比那里X对线程B来说并不不可见.这种工作内存与主内存同步延迟现象就造成了可见性问题.

验证
 假如 int a=0 ,在变量前没有加volatile,没有可见性,但是加了volatile关键字,则有了可见性

参看代码

2. #### **原子性**

验证volatile不包含原子性

何为原子性
不可分割,保证完整性,即使某一个线程正在做某个业务时,其他的业务不会影响我 

演示
多线程操作,对同一变量进行累加
比如:10个线程,每个线程进行2万次,对volatile int a 进行 +1操作,最后的结果会有很大偏差,可以看到 volatile没有原子性
从代码到java字节码中,putfiled时,会丢数据

比如一个线程在主内存中拿到值进行处理并将数据进行回写主内存,这个操作中可能会被其他线程加塞操作,导致丢失数据

比如,t1线程刚写到主内存中,其他线程还没有拿到更新的值,也直接进行写入到了主内存中,这样就会出现写覆盖,也是会丢失数据



![image-20200721141709929](assets\image-20200721141709929.png)

```
因为线程抢占 ,多个线程修改同一个主内存中的值,会有丢数据的现象
```

怎么办?  sync杀鸡焉用牛刀

使用 java.util.atomic.AtoincInteger

那就需要用 AtomicInteger a =0;  原子性的int  然后配合volatile使用

3. #### **VolatileDemo代码演示可见性+原子性代码**

```
AtomicInteger atmoic = new AtomicInteger();
```

那么为什么使用AtomicInteger就可以解决原子性的问题呢?

底层是CAS原理,后边会介绍

4. #### **有序性**

![image-20200721150258675](assets\image-20200721150258675.png)

单线程环境里面确保程序最终执行结果和代码顺序执行的结果一致.
处理器在进行重新排序是必须要考虑指令之间的数据依赖性

多线程环境中线程交替执行,由于编译器优化重排的存在,两个线程使用的变量能否保持一致性是无法确定的,结果无法预测

举一个例子

考试答题的时候,做题顺序和题目顺序是不一样的

这也就是指令重排,你写的代码的顺序和字节码的顺序是不一样的

---

多线程下

重排

```java
public void mySort(){
    int x=11;//语句1
    int y=12;//语句2
    x=x+5;//语句3
    y=x*x;//语句4
}
1234
2134
1324
问题:
请问语句4 可以重排后变成第一条码? 不可以
因为存在数据的依赖性 没办法排到第一个
```


案例 1 	

int a ,b ,x,y=0;

| 线程1   | 线程2 |
| ------- | ----- |
| x=a;    | y=b;  |
| b=1;    | a=2;  |
|         |       |
| x=0 y=0 |       |

如果编译器对这段代码进行执行重排优化后,可能出现下列情况:

| 线程1    | 线程2 |
| -------- | ----- |
| b=1;     | a=2;  |
| x=a;     | y=b;  |
|          |       |
| x=2  y=1 |       |

对比两个区别,这就是重排,加了volatile就是避免重排

案例2 

![image-20200721154858552](assets\image-20200721154858552.png)

假设多线程同时访问这个类,就会出现指令重排的问题,就会影响本身的逻辑,就会影响我们的调用顺序的逻辑,可以使用volatile禁止指令重排

**指令重排小结**

![image-20200721155337377](assets\image-20200721155337377.png)

![image-20200721155344127](assets\image-20200721155344127.png)



 



**以上4点 =>线程安全性获得保证** 

怎么做到的呢?

工作内存和主内存同步延迟现象导致的可见性问题

可以使用synchronize或者volatile关键字解决,他们都可以是一个线程修改后的变量立即对其他线程可见



对于指令重排导致的可见性问题和有序性问题

可以利用volatile关键字解决,因为volatile的另外一个作用就是禁止指令重排



## 1.3如何应用volatile

1. 单例模式DCL

DCL(双端检锁) 机制不一定线程安全,原因是有指令重排的存在,加入volatile可以禁止指令重排
  原因在于某一个线程在执行到第一次检测,读取到的instance不为null时,instance的引用对象可能没有完成初始化.
instance=new SingletonDem(); 可以分为以下步骤(伪代码)

```java
memory=allocate();	//1.分配对象内存空间
instance(memory);	//2.初始化对象
instance=memory;	//3.设置instance的指向刚分配的内存地址,此时instance!=null 
```

步骤2和步骤3不存在数据依赖关系.而且无论重排前还是重排后程序执行的结果在单线程中并没有改变,因此这种重排优化是允许的.所以要禁止重排,否则会影响逻辑

```java
memory=allocate();	//1.分配对象内存空间
instance=memory;	//3.设置instance的指向刚分配的内存地址,此时instance!=null 但对象还没有初始化完.
instance(memory);	//2.初始化对象
```

但是指令重排只会保证串行语义的执行一致性(单线程) 并不会关心多线程间的语义一致性
所以当一条线程访问instance不为null时,由于instance实例未必完成初始化,也就造成了线程安全问题.

# 2 CAS

## 2.1 compare and set  CAS 比较并交换

```java
//初始值设置为5
AtomicInteger atomic = new AtomicInteger(5);
int i = atomic.get();
System.out.println(i);
//期望是5  如果是就修改为2019
System.out.println(atomic.compareAndSet(5, 2019) + "\t 当前值:" + atomic.get());//true	 当前值:2019
System.out.println(atomic.compareAndSet(5, 2014) + "\t 当前值:" + atomic.get());//false	 当前值:2019
```

比如我两个线程  要去修改主物理内存的一个值   ,A拿到了这个值想要去修改为5,那么就会有一个期待值,
AtomicInteger a = new AtomicInteger(5)
a.compareAndSet(5,2019)
a.compareAndSet(5,1024)
5就是期待值
一个线程去操作的时候发现,我要修改的这个值,此时是5,跟我拿到这个值的时候没有变化,那我就去修改它为2019
第二个线程,这个时候 获取这个值是5,但是已经有一个线程进行了修改,此时他获取的值是5,但是现在这个值的数值是2019,所以第二个线程修改值就失败了



## 2.2 CAS底层原理,  UnSafe的理解

### 2.2.1 atomicInteger.getAndIncrement()

方法的源代码:

```java
/**
 * Atomically increments by one the current value.
 *
 * @return the previous value
 */
public final int getAndIncrement() {
    return unsafe.getAndAddInt(this, valueOffset, 1);
}
印出来一个问题:UnSafe类是什么?
在jdk源码中的一个类
```

![image-20200721174635550](assets\image-20200721174635550.png)



### 2.2.2 UnSafe

1. UnSafe

 UnSafe 是CAS的核心类 由于Java 方法无法直接访问底层 ,需要通过本地(native)方法来访问,UnSafe相当于一个后面,基于该类可以直接操作特额定的内存数据.**UnSafe类在于sun.misc包中**,其内部方法操作可以向C的指针一样直接操作内存,因为Java中CAS操作的助兴依赖于UNSafe类的方法.

所以UnSafe是保证原子性的决定性因素

**注意UnSafe类中所有的方法都是native修饰的,也就是说UnSafe类中的方法都是直接调用操作底层资源执行响应的任务**

这个类在 jdk源生自带的,从娘胎里带出来的

2. 变量**ValueOffset**,便是该变量在内存中的偏移地址,因为UnSafe就是根据内存偏移地址获取数据的

![image-20200721174934806](assets\image-20200721174934806.png)

this就是当前的值,valueoffset内存里的地址,可以准确找到这个值

3. 变量value和volatile修饰,保证了多线程之间的可见性.

### 2.2.3 cas是什么

CAS的全称为Compare-And-Swap ,它是一条CPU并发原语.
它的功能是判断内存某个位置的值是否为预期值,如果是则更新为新的值,这个过程是原子的.

CAS并发原语提现在Java语言中就是sun.miscUnSaffe类中的各个方法.调用UnSafe类中的CAS方法,JVM会帮我实现CAS汇编指令.这是一种完全依赖于硬件 功能,通过它实现了原子操作,再次强调,由于CAS是一种系统原语,原语属于操作系统用于范畴,是由若干条指令组成,用于完成某个功能的一个过程,并且原语的执行必须是连续的,在执行过程中不允许中断,也即是说CAS是一条原子指令,不会造成所谓的数据不一致的问题. 也就是说线程安全

![image-20200721182711905](assets\image-20200721182711905.png)

![image-20200721182726183](assets\image-20200721182726183.png)

注意看两个图, `unsfae.getAndAddInt(this,valueoffset,1)`,这个1就是进行+1操作,对值进行修改

分别对应下图的var1 var2 var4

原理介绍

***

this.getIntVolatile(var1,var2),var1代表了当前的这个对象,var2就是这个对象的地址值,可以拿到var5这个值

这个时候进入while里进行比较,当前对象var1的地址值var2,它的值是不是var5,也就是上边取出来的值,如果是就var5+var4 也就是+1

这也就是真实值和我的期望值不一致,就要去重新获取

***

这个是unsafe里的逻辑,进行值的比较,靠的cpu原语保证原子性,在getAndAddInt中 通过while循环不断去比较,如果发现值被修改过,他就会去从新获取var5,然后再进行比较,所以保证了原子性

1. unSafe.getAndIncrement

```
var1 AtomicInteger对象本身.
var2 该对象值的引用地址
var4 需要变动的数值
var5 是用过var1 var2找出内存中绅士的值
用该对象当前的值与var5比较
如果相同,更新var5的值并且返回true
如果不同,继续取值然后比较,直到更新完成
```

![image-20200721184415844](assets\image-20200721184415844.png)

假设线程A和线程B两个线程同时执行getAndAddInt操作(分别在不同的CPU上):

- AtomicInteger里面的value原始值为3,即主内存中AtomicInteger的value为3,根据JMM模型,线程A和线程B各自持有一份值为3的value的副本分别到各自的工作内存.

- 线程A通过getIntVolatile(var1,var2) 拿到value值3,这时线程A被挂起.

- 线程B也通过getIntVolatile(var1,var2) 拿到value值3,此时刚好线程B没有被挂起并执行compareAndSwapInt方法比较内存中的值也是3 成功修改内存的值为4 线程B打完收工 一切OK.

- 这是线程A恢复,执行compareAndSwapInt方法比较,发现自己手里的数值和内存中的数字4不一致,说明该值已经被其他线程抢先一步修改了,那A线程修改失败,只能重新来一遍了.

- 线程A重新获取value值,因为变量value是volatile修饰,所以其他线程对他的修改,线程A总是能够看到,线程A继续执行compareAndSwapInt方法进行比较替换,直到成功.

2. 底层汇编

![image-20200721184442784](assets\image-20200721184442784.png)

3. 总结

![image-20200721184454594](assets\image-20200721184454594.png)

### 2.2.4  CAS的缺点

- 循环时间长,开销很大

![image-20200721185128412](assets\image-20200721185128412.png)

并发性加强了,但是循环太长了,开销变大

- 只能保证一个共享变量的原子性

![image-20200721185145356](assets\image-20200721185145356.png)

- 引出ABA问题

因为CAS的缺点引出来的ABA问题   那么原子引用更新?  如何规避ABA问题

又引出一个新的问题!

# 3  原子类AtomicInteger的ABA问题 &原子更新引用

## 3.1 ABA问题的产生

![image-20200721191158658](assets\image-20200721191158658.png)

好比AB两个线程,主物理内存中有一个值C 

A线程用时10秒,B用时1秒

此时A和B拿到了C值,

B拿到了C,在自己工作内存中将C改成B,并且回写到物理内存.此时物理内存中的值是B

此时B又去物理内存中取值为B,在自己的工作内存中将B修改为C,并回写到物理内存中,此时物理内存中的值为C

而此时A,再去主物理内存取值进行比较发现.期望值是C 真实值也是C,则判断通过,认为没有人动过这个值

而在B线程中,已经对C值进行了改动

## 3.2 原子引用更新

这个问题的是因为ABA问题,因为CAS只关注头和尾的一致性,不关注中间过程中的变化,在这段过程中,数据被修改过这个就是ABA问题

如果不允许中间过程产生猫腻,则需要**原子引用更新**

```java
 AtomicReference<User> atomic = new AtomicReference<User>();
        User zs = new User("zhangsan", 10);
        User ls = new User("lisi", 20);
        User ww = new User("wangwu", 30);
        atomic.set(zs);

        System.out.println(atomic.compareAndSet(zs, ls) + "\t" + atomic.get().toString());//true	User{name='lisi', age=20}
        System.out.println(atomic.compareAndSet(ls, ww) + "\t" + atomic.get().toString());//true	User{name='wangwu', age=30}

```

期待值是真实值,则改,反之不改

### 3.3 时间戳原子引用

引入版本号

不管多少个线程去修改同一个值,只要修改值,版本号就递增

`AtomicStampedReference`

详情参看代码

```java
public class ABADemo {
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    //初始值是100,初始时间戳为1
    private static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {

        System.out.println("===以下是ABA问题的产生===");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "t1").start();

        new Thread(() -> {
            //先暂停1秒 保证完成ABA
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2019) + "\t" + atomicReference.get());//产生ABA
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("===以下是ABA问题的解决===");

        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号" + stamp + "\t值是" + stampedReference.getReference());
            //暂停1秒钟t3线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //模拟ABA
            stampedReference.compareAndSet(100, 101, stampedReference.getStamp(), stampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 第2次版本号" + stampedReference.getStamp() + "\t值是" + stampedReference.getReference());
            stampedReference.compareAndSet(101, 100, stampedReference.getStamp(), stampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 第3次版本号" + stampedReference.getStamp() + "\t值是" + stampedReference.getReference());
        }, "t3").start();

        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号" + stamp + "\t值是" + stampedReference.getReference());
            //保证线程3完成1次ABA
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             * t4以为版本号是1,尝试修改,但是t3已经修改过了版本号为3,所以修改失败
             */
            int stamp1 = stampedReference.getStamp();
            boolean result = stampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "\t 修改成功否:" + result + "\t 最新版本号:" + stampedReference.getStamp());
            System.out.println("最新的值\t" + stampedReference.getReference() + "\t 最新的版本号是:" + stamp1);
        }, "t4").start();
        System.out.println("t5尝试修改");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            stampedReference.compareAndSet(stampedReference.getReference(), 2019, stampedReference.getStamp(), stampedReference.getStamp() + 1);
            //t5	 修改完的值:2019	 修改完成的版本号:4
            System.out.println(Thread.currentThread().getName() + "\t 修改完的值:" + stampedReference.getReference() + "\t 修改完成的版本号:" + stampedReference.getStamp());
        }, "t5").start();
    }
}
```

# 4 ArrayList线程不安全,怎么解决?

## 4.1 CopyOnWrite

写时复制

```java
package ArrayList;

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

```

## 4.2 set map 

如何解决线程不安全

```java
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

```

# 5 java的锁

公平锁,非公平锁,可重入锁,递归锁, 自旋锁

## 5.1 公平锁和非公平锁

### 5.1.1  是什么

公平锁
    是指多个线程按照申请锁的顺序来获取锁类似排队打饭 **先来后到**
非公平锁
    是指在多线程获取锁的顺序并不是按照申请锁的顺序,有可能后申请的线程比先申请的线程优先获取到锁,在高并发的情况下**,有可能造成优先级反转或者饥饿现象,允许加塞**

### 5.1.2 区别

  公平锁/非公平锁
  并发包ReentrantLock的创建可以指定构造函数的boolean类型来得到公平锁或者非公平锁 默认是非公平锁

![image-20200722132756452](assets\image-20200722132756452.png)

### 5.1.3 总结

Java ReentrantLock而言,
通过构造哈数指定该锁是否是公平锁 默认是非公平锁 非公平锁的优点在于吞吐量必公平锁大.


对于synchronized而言 也是一种非公平锁.

## 5.2 可重入锁(递归锁)

### 5.2.1 是什么

![image-20200722140422778](assets\image-20200722140422778.png)

### 5.2.2 ReentrantLock/synchronized就是一个典型的可重入锁

### 5.2.3 可重入锁最大的作用就是防止死锁

线程可以进入任意一个锁,那么这个锁内的错you代码块都可以进入

### 5.2.4 ReenterLockDemo

代码

```java
package lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName lockDemo
 * Description
 * Create by Jason
 * Date 2020/7/22 13:28
 * 可重入锁(递归锁)
 * 指的是同一线程外层函数获得锁之后,内层递归函数仍然能获取该锁的代码
 * 在同一个线程在外层方法获取锁的时候,在进入内层方法会自动获取锁
 *
 * 也就是说  线程可以进入任何一个他已经拥有的锁所同步着的代码块
 *
 * t1	 sendSms
 * t1	 sendEmail:
 * t2	 sendSms
 * t2	 sendEmail:
 *
 * 证明了 synchronized 是可重入锁
 */
public class ReenterLockDemo {
    public static void main(String[] args) {
        ReenterLockDemo lock = new ReenterLockDemo();
//        lock.rennterLock();
        lock.sync();
    }
    /**
     * 验证ReentrantLock 是可重入锁
     * Thread-1	 get
     * Thread-1	 set
     * Thread-0	 get
     * Thread-0	 set
     */
    public void rennterLock(){
        Phone2 phone = new Phone2();
        Thread t3 = new Thread(phone);
        Thread t4 = new Thread(phone);
        t3.start();
        t4.start();
    }
    /**
     * 验证sync是可重入锁
     * t1	 sendSms  t1线程在外层方法获取锁的时候
     * t1	 sendEmail: t1在进入内层方法会自动获取锁
     * t2	 sendSms
     * t2	 sendEmail:
     */
    public void sync(){
        Phone phone = new Phone();
        new Thread(()->{
            try {
                phone.sendSms();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(()->{
            try {
                phone.sendSms();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2").start();

    }

}
```

```java
package lock;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * ClassName Phone
 * Description
 * Create by Jason
 * Date 2020/7/22 14:11
 */
public class Phone2 implements Runnable{
    private Lock lock= new ReentrantLock();
    private void get(){
        //lock要配对,一个lock  一个unlock
        lock.lock();
//        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t get");
            set();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
//            lock.unlock();
        }
    }

    private void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t set");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        get();
    }
}
```

```java
package lock;

import java.util.concurrent.ExecutionException;

/**
 * ClassName Phone
 * Description
 * Create by Jason
 * Date 2020/7/22 14:11
 */
public class Phone  {
    public synchronized void sendSms() throws Exception {
        System.out.println(Thread.currentThread().getName()+"\t sendSms" );
        sendEmail();
    }

    public synchronized void  sendEmail()throws Exception {
        System.out.println(Thread.currentThread().getName()+"\t sendEmail:");
    }
}

```

## 5.3 自旋锁

![image-20200722145001331](assets\image-20200722145001331.png)

可以联想到 UnSafe类和CAS思想

参看代码

```java
package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ClassName SnipLockDemo
 * Description
 * Create by Jason
 * Date 2020/7/22 15:03
 * <p>
 * 实现自旋锁
 * 好处:循环比较获取直到成功为止,没有类似wait的阻塞
 * <p>
 * 通过CAS操作完成自旋锁,A线程先进来调用mylock方法自己持有锁5秒钟,B随后近来发现当前有线程持有锁,不是null,所以只能通过自旋等待,直到A释放锁后B随后抢到
 */
public class SnipLockDemo {
    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    public static void main(String[] args) {
        SnipLockDemo demo = new SnipLockDemo();
        new Thread(() -> {
            demo.mylock();
            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
            demo.unmylock();
        }, "AA").start();
//        try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
        new Thread(() -> {
            demo.mylock();
//            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
            demo.unmylock();
        }, "BB").start();
        new Thread(() -> {
        demo.mylock();
//        try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();}
        demo.unmylock();
    }, "CC").start();
    }

    public void mylock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t com in yes");
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println("进入while,线程上锁:"+Thread.currentThread().getName());
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }
    public void unmylock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t invoked un my lock");

    }
}

```

## 5.4 独占锁(写)/共享锁(读)/互斥锁

![image-20200722153033758](assets\image-20200722153033758.png)

```java
package lock;
/**
 * ClassName readWriteLockDemo
 * Description
 * Create by Jason
 * Date 2020/7/22 15:39
 * 多个线程同时操作一个资源类没有任何问题,所以为了满足并发量,读取共享资源应该可以同时进行
 * 但是如果一个线程想要去写共享资源,就不应该有其他线程可以对资源进行读或写
 *
 * 读 读 可以共存
 * 读 写  不可以共存
 * 写 写 不可以共存
 * 写操作  原子+独占  整个过程必须是一个完成的统一整体,中间不允许被分割被打断
 * 打印结果    可以看到 写入没有被打断,读取可以打断
 */
public class readWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 1; i <= 5; i++) {
            final int tempint = i;
            new Thread(() -> {
                myCache.put(tempint + "", tempint);
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {
            int tempint = i;
            new Thread(() -> {
                myCache.get(tempint + "");
            }, String.valueOf(i)).start();
        }
    }
}

```

```java
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

```

## 5.5 sychnized和lock的区别

### 区别

- 构成

  sychnized是java的关键字是jvm层面, 底层通过mointor对象来完成,其实wait/notify也依赖于monitor对象只有在同步块或方法中才能调用wait/notify等方法,不会产生死锁

  lock是api层面的,是具体类`java.util.current.locks.Lock`的锁

- 使用方法

  sychnized不需要手动释放锁,当sychnized代码执行完毕后,系统会自动让线程释放对锁的占用

  ReentrantLock需要手动释放锁,没有主动释放锁,可能导致死锁出现

  需要lock()和unlock()方法配合,try/finally语句块来完成

- 等待是否可中断

  sychnized不可中断,除非抛出异常或者正常运行完成

  ReentrantLock可中断

  ​	设置超时方法,tryLock(long timeout,TimeUnit unit)

  ​	lockInterruptibly() 放到代码块中,调用interrupt()方法可中断

- 加锁是否公平

  sychnized非公平锁

  ReentrantLock两者都可以,默认是公平锁,构造方法可以传入boolean值,true为公平锁,false为非公平锁

- 锁绑定多个条件condition

  sychnized没有

  ReentrantLock用来实现分组唤醒需要唤醒的线程们,可以精确唤醒,而不是像sychnized要么随机唤醒,要么全部唤醒

#    6 juc相关包:CountDownLatch/CyclicBarrier/Semaphore

juc下的相关包的了解

CountDownLatch 倒计时



CyclicBarrier

Semaphore



## 6.1 CountDownLatch 

让一些线程阻塞直到另外一些完成后才被唤醒

CountDownLatch主要有两个方法,当一个或多个线程调用await方法时,调用线程会被阻塞.其他线程调用countDown方法计数器减1(调用countDown方法时线程不会阻塞),当计数器的值变为0,因调用await方法被阻塞的线程会被唤醒,继续执行

CountDownLatch主要有两个方法

- 当一个或多个线程调用await方法时,调用线程会被阻塞.其他线程调用countDown方法计数器减1(调用countDown方法时线程不会阻塞)
- 当计数器的值变为0,因调用await方法被阻塞的线程会被唤醒,继续执行

```java
package CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * ClassName CountDownLatchDemo
 * Description
 * Create by Jason
 * Date 2020/7/22 16:48
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo c = new CountDownLatchDemo();
        c.countCountryPuls();
    }

    public void countCountryPuls() throws InterruptedException {
        // 6个数字
        CountDownLatch count = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t ok");
                count.countDown();
            }, CountryEnum.forEach(i).getName()).start();
        }
        count.await();
        System.out.println(Thread.currentThread().getName() + "\t go");
    }

    public void countNumber() throws InterruptedException {
        // 6个数字
        CountDownLatch count = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t ok");
                count.countDown();
            }, String.valueOf(i)).start();
        }
        count.await();
        System.out.println(Thread.currentThread().getName() + "\t go");
    }
}
```

### 枚举的小技巧

```java
package CountDownLatch;

import jdk.nashorn.internal.objects.annotations.Getter;

/**
 * ClassName countryEnum
 * Description
 * Create by Jason
 * Date 2020/7/22 17:31
 */
public enum CountryEnum {

    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");

    CountryEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 自己定义的遍历方法
     *
     * @param index
     * @return
     */
    public static CountryEnum forEach(int index) {
        CountryEnum[] countryEnums = CountryEnum.values();
        for (CountryEnum countryEnum : countryEnums) {
            if (index == countryEnum.getCode()) {
                return countryEnum;
            }
        }
        return null;
    }

}
```



## 6.2 CyclicBarrier

CyclicBarrier的字面意思是可循环(Cyclic) 使用的屏障(barrier).它要做的事情是,让一组线程到达一个屏障(也可以叫做同步点)时被阻塞,知道最后一个线程到达屏障时,屏障才会开门,所有被屏障拦截的线程才会继续干活,线程进入屏障通过CyclicBarrier的await()方法.

```java
package CountDownLatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * ClassName CyclicBarrier
 * Description
 * Create by Jason
 * Date 2020/7/22 17:39
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyc = new CyclicBarrier(7, () -> {
            System.out.println("ok");
        });
        for(int i = 1 ; i <=7;i++){
            final int temp=i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 第:"+temp);
                try {
                    cyc.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}

```

## 6.3 Semaphore

信号量的主要用户两个目的,一个是用于多喝共享资源的相互排斥使用,另一个用于并发资源数的控制.



```java
package CountDownLatch;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * ClassName SemaphoreDemo
 * Description
 * Create by Jason
 * Date 2020/7/22 17:53
 * 1	 抢到车位
 * 3	 抢到车位
 * 2	 抢到车位
 * 2	 停3秒离开车位
 * 3	 停3秒离开车位
 * 1	 停3秒离开车位
 * 5	 抢到车位
 * 4	 抢到车位
 * 6	 抢到车位
 * 4	 停3秒离开车位
 * 6	 停3秒离开车位
 * 5	 停3秒离开车位
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //3个车位
        Semaphore sp = new Semaphore(3);
        //6个汽车
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    sp.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t 抢到车位");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "\t 停3秒离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    sp.release();
                }
            }, String.valueOf(i)).start();
        }

    }
}

```



# 7 阻塞队列

## 7.1 队列+阻塞队列

阻塞队列,顾名思义,首先它是一个队列,而一个阻塞队列在数据结构中所起的作用大致如图所示:

![image-20200723093111466](assets\image-20200723093111466.png)

当阻塞队列是空时,从队列中获取元素的操作将会被阻塞.
当阻塞队列是满时,往队列中添加元素的操作将会被阻塞.
同样
试图往已满的阻塞队列中添加新圆度的线程同样也会被阻塞,知道其他线程从队列中移除一个或者多个元素或者全清空队列后使队列重新变得空闲起来并后续新增

## 7.2 为什么用?好处?



在多线程领域:所谓阻塞,在某些情况下会挂起线程(即线程阻塞),一旦条件满足,被挂起的线程优惠被自动唤醒

该唤醒谁,该堵塞谁

为什么需要使用BlockingQueue

**好处**是我们不需要关心什么时候需要阻塞线程,什么时候需要唤醒线程,因为BlockingQueue都一手给你包办好了

在concurrent包 发布以前,在多线程环境下,我们每个程序员都必须自己去控制这些细节,尤其还要兼顾效率和线程安全,而这会给我们的程序带来不小的复杂度.

## 7.3 BlockingQueue的核心方法

![image-20200723094640897](assets\image-20200723094640897.png)

## 7.4架构梳理&种类分析

![image-20200723093824893](assets\image-20200723093824893.png)

**ArrayBlockingQueue: 由数组结构组成的有界阻塞队列.**

**LinkedBlockingDeque: 由链表结构组成的有界(但大小默认值Iteger>MAX_VALUE)阻塞队列.**

PriorityBlockingQueue:支持优先级排序的无界阻塞队列.

DelayQueue: 使用优先级队列实现的延迟无界阻塞队列.

**SynchronousQueue:不存储元素的阻塞队列,也即是单个元素的队列.**

​	理论

```
SynchronousQueue没有容量

与其他BlcokingQueue不同,SynchronousQueue是一个不存储元素的BlcokingQueue

每个put操作必须要等待一个take操作,否则不能继续添加元素,反之亦然.
```

​	代码 : SynchronousQueueDemo



LinkedTransferQueue:由链表结构组成的无界阻塞队列.

LinkedBlocking**Deque**:由了解结构组成的双向阻塞队列.

## 7.5可以用在哪里

### 7.5.1 生产者消费者模式

Queue/ProdConsumerTradition

### 7.5.2 线程池

Queue.ProducerAndCousumer_Block

### 7.5.3 消息中间件

Queue.SynchronousQueueDemo



# 8 线程池

获取多线程的方式

继承Thread

实现Runnable接口,没有返回值

实现Callable接口,有返回值,可以抛异常

线程池

## 8.1 为什么使用线程池

 线程池做的工作主要是控制运行的线程的数量,处理过程中将任务加入队列,然后在线程创建后启动这些任务,如果先生超过了最大数量,超出的数量的线程排队等候,等其他线程执行完毕,再从队列中取出任务来执行.

他的主要特点为:线程复用:控制最大并发数:管理线程.

- 降低资源消耗.通过重复利用自己创建的线程降低线程创建和销毁造成的消耗.
- 提高响应速度.当任务到达时,任务可以不需要等到线程和粗昂就爱你就能立即执行.
- 提高线程的可管理性.线程是稀缺资源,如果无限的创阿金,不会消耗资源,还会较低系统的稳定性,使用线程池可以进行统一分配,调优和监控.





## 8.2 如何使用线程池

### 8.2.1 架构实现

Java中的线程池是通过Executor框架实现的,该框架中用到了Executor,Executors,ExecutorService,ThreadPoolExecutor这几个类.

Executors是一个辅助工具类              

![image-20200723140956835](assets\image-20200723140956835.png)

### 8.2.2 编码实现

- #### Executors.newFixedThreadPool(int)

  执行一个长期的任务,性能好很多

主要特点如下:

```
1.创建一个定长线程池,可控制线程的最大并发数,超出的线程会在队列中等待.
2.newFixedThreadPool创建的线程池corePoolSize和MaxmumPoolSize是 相等的,它使用的的LinkedBlockingQueue
```

![image-20200723143913979](assets\image-20200723143913979.png)

- #### Executors.newSingleThreadExecutor()

一个任务一个线程执行的任务场景

```
主要特点如下:
1.创建一个单线程化的线程池,它只会用唯一的工作线程来执行任务,保证所有任务都按照指定顺序执行.
2.newSingleThreadExecutor将corePoolSize和MaxmumPoolSize都设置为1,它使用的的LinkedBlockingQueue

```

![image-20200723143930444](assets\image-20200723143930444.png)



- #### Executors.newCachedThreadPool()

适用:执行很多短期异步的小程序或者负载较轻的服务器

```
 主要特点如下:
1.创建一个可缓存线程池,如果线程池长度超过处理需要,可灵活回收空闲线程,若无可回收,则创建新线程.
2.newCachedThreadPool将corePoolSize设置为0MaxmumPoolSize设置为Integer.MAX_VALUE,它使用的是SynchronousQUeue,也就是说来了任务就创建线程运行,如果线程空闲超过60秒,就销毁线程

```

![image-20200723143948808](assets\image-20200723143948808.png)

### 8.2.3 ThreadPoolExecutor

底层究竟是什么?

![image-20200723144532590](assets\image-20200723144532590.png)



## 8.3 线程池几个重要的参数

```java
 public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.acc = System.getSecurityManager() == null ?
                null :
                AccessController.getContext();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }
```

一共七个参数

- 1.corePoolSize:线程池中的常驻核心线程数,**相当于正常的线程数,通常到达上限可以进行扩容**

```java
1.在创建了线程池后,当有请求任务来之后,就会安排池中的线程去执行请求任务,近视理解为今日当值线程
2.当线程池中的线程数目达到corePoolSize后,就会把到达的任务放入到缓存队列当中.
```

- 2.maximumPoolSize:线程池能够容纳同时执行的最大线程数,此值大于等于1,**线程同时运行的峰值个数**
- 3.keepAliveTime:多余的空闲线程存活时间,当空间时间达到keepAliveTime值时,多余的线程会被销毁直到只剩下corePoolSize个线程为止
- 4.unit:keepAliveTime的单位
- 5.workQueue:任务队列,被提交但尚未被执行的任务. **相当于候客区**
- 6.threadFactory:表示生成线程池中工作线程的线程工厂,用户创建新线程,一般用默认即可,**生成线程的工厂**
- 7.handler:拒绝策略,表示当线程队列满了并且工作线程大于等于线程池的最大显示 数(maxnumPoolSize)时如何来拒绝. **已经到达承受上限,拒绝后边的请求,有四种拒绝策略 **

## 8.4 线程池的底层工作原理

![image-20200723153349736](assets\image-20200723153349736.png)

## 8.5 线程池的拒绝策略

一个接口四个策略

```
等待队列也已经排满了,再也塞不下新的任务了
同时,
线程池的max也到达了,无法接续为新任务服务
这时我们需要拒绝策略机制合理的处理这个问题.
```

### 8.5.1 JDK内置的拒绝策略

- AbortPolicy(默认):直接抛出RejectedException异常阻止系统正常运行

- CallerRunPolicy:"调用者运行"一种调节机制,该策略既不会抛弃任务,也不会抛出异常,而是

- DiscardOldestPolicy:抛弃队列中等待最久的任务,然后把当前任务加入队列中尝试再次提交

- DiscardPolicy:直接丢弃任务,不予任何处理也不抛出异常.如果允许任务丢失,这是最好的拒绝策略

以上内置策略均实现了RejectExecutionHandler接口

## 8.6 你在工作中单一的/固定数的/可变你的三种创建线程池的方法,你用哪个多?

哪个都不用

生产上只能使用自定义的

参考阿里巴巴java开发手册

【强制】线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。 说明：使用线程池的好处是减少在创建和销毁线程上所消耗的时间以及系统资源的开销，解决资源不足的问题。如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。 

【强制】线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。说明：Executors返回的线程池对象的弊端如下：
1）FixedThreadPool和SingleThreadPool:允许的请求队列长度为Integer.MAX_VALUE，可能会堆积大量的请求，从而导致OOM。
2）CachedThreadPool和ScheduledThreadPool:允许的创建线程数量为Integer.MAX_VALUE，可能会创建大量的线程，从而导致OOM。

![image-20200724093253598](assets\image-20200724093253598.png)

## 8.7  自定义线程池

```java
package ThreadPool.customize;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName customizePoolDemo
 * Description
 * Create by Jason
 * Date 2020/7/24 9:37
 */
public class customizePoolDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor threadpool = new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        try {
            for (int i = 1; i <= 10; i++) {
                threadpool.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadpool.shutdown();
        }
    }
}

```



## 8.8 参数如何配置

两个角度去考虑

### 8.8.1 CPU密集型

System.out.println(Runtime.getRuntime().availableProcessors());查看CPU核数

![image-20200724095359271](assets\image-20200724095359271.png)

### 8.8.2 IO密集型

![image-20200724095413061](assets\image-20200724095413061.png)

![image-20200724095420252](assets\image-20200724095420252.png)



# 9 死锁&定位分析

## 9.1 什么是死锁&原因

### 9.1.1 死锁

![image-20200724100039007](assets\image-20200724100039007.png)

### 9.1.2  原因

- 系统资源不足

- 进程运行推进的顺序不合适

- 资源分配不当

## 9.2 代码

```java
package lock.DeadLock;

/**
 * ClassName DeadLockDemo
 * Description
 * Create by Jason
 * Date 2020/7/24 10:04
 * 死锁是指两个或者两个以上的进程在执行过程中
 * 因为争夺资源造成一种互相等待的现象
 * 吃着碗里的,想着锅里的
 * */
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldThread(lockA, lockB), "threadAAA").start();
        new Thread(new HoldThread(lockB, lockA), "threadBBB").start();

    }
}

```

```java
package lock.DeadLock;

import java.util.concurrent.TimeUnit;

/**
 * ClassName HoldThread
 * Description
 * Create by Jason
 * Date 2020/7/24 10:05
 */
public class HoldThread implements Runnable {
    private String lockA;
    private String lockB;

    public HoldThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有锁:" + lockA + "尝试获得:" + lockB);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有锁:" + lockB + "尝试获得:" + lockA);
            }
        }
    }
}

```

## 9.3 解决&排查思路

jps命令定位进程编号



![image-20200724101727464](assets\image-20200724101727464.png)



jstack找到死锁查看



![image-20200724101745075](assets\image-20200724101745075.png)

AAA 我正再锁着e898 我等待e8d0

BBB 我正在锁着e8d0 我等待e898