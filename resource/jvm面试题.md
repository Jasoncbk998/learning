jvm面试题

# 1 jvm垃圾回收的时候如何确定垃圾? & GC Roots是什么?

## 1.1 怎么判断垃圾

内存中极影不再被使用到的空间就是垃圾

# 1.2 怎样进行垃圾回收,如何判断一个对象是否可以被回收

###  1.2.1 引用计数法

java中,引用和对象是有关联的,如果要操作对象则必须用引用进行

因此,很显然一个简单的办法是通过引用计数来判断一个对象是否可以回收,简单说,给对象中添加一个引用计数器

每当一个地方引用,就会计数器值加1

每当一个引用失效时,计数器值减1

任何时刻计数器值为零的对象就是不可能在被使用的,那么这个对象就是可回收对象

那为什么主流的java虚拟机里面没有使用这种算法呢?因为最主要的原因是他很难解决对象之间的相互引用的问题

A ->B

B ->A

这样就不会进行回收

### 1.2.2 枚举根节点做可达性分析(跟搜索路径)

解决引用计数法的问题,循环引用

![image-20200725141002069](assets\image-20200725141002069.png)

所谓GC Roots 或者说 tracing GC的  根集合,就是一组必须活跃的引用



基本思路就是通过一系列名为,GC Roots的对象作为起始点,从这个被称为GC Roots的对象开始向下搜索,如果一个对象到GC Roots没有任何引用链相连时,这说明此对象不可用,则**会被回收**,也即给定一个集合的引用作为根出发,通过引用关系遍历对象图,能被遍历到的(可到达的),对象就被判定为存活,没有被遍历到的就自然判定为死亡



#### 什么可以作为GC Roots引用的对象

虚拟机栈(栈帧中局部变量区,也叫作局部变量表)中引用的对象: 比如方法

方法区中的类静态属性引用的对象

方法区中常量引用的对象

本地方占中的JNI(Native方法)引用的对象

被引用到了就不可以回收,没有引用到就可以回收

# 2 jvm参数调优和默认设置

查看:  java的  E:\soft\jdk\bin下 

有很多命令 其中常用的jps  jinfo

jinfo可以查看正在运行的java进程

![image-20200725145748542](assets\image-20200725145748542.png)

![image-20200725145836396](assets\image-20200725145836396.png)



可以看到 test_gc进程没有开启gc打印参数

然后我们在idea中添加jvm参数,在进行测试     -XX:+PrintGCDetails

![image-20200725150045061](assets\image-20200725150045061.png)





## 2.1 参数类型

### 2.1.1 标配参数

-version

-help

java -version

### 2.1.2 X参数(了解)

-Xint  解释执行

-Xcomp 第一次使用就编译成本地代码

-Xmixed 混合模式

### 2.2.3 XX参数(重点)

#### boolean类型

公式:      -XX : + 或者 - 某个属性	   + 表示开启   -表示关闭

查看是否使用串行垃圾回收器  -XX:-UseSerialGC      -XX:+UseSerialGC  

#### kv设值类型

使用方式: -XX:属性key=属性值value

举例 

-XX:MetaspaceSize=128m   通过这个命令去设置

![image-20200725150618496](assets\image-20200725150618496.png)

-XX:MaxTenuringThreshold=15   同理  还是使用jinfo进行查看



#### jinfo类型,如何查看当前运行程序的配置

上边有演示

#### 题外话

##### 两个经典的参数: -Xms    和   -Xmx 

-Xms      等价于  -XX:InitialHeapSize  初始化堆内存

-Xmx   等价于    -XX:MaxHeapSize 最大堆内存



## 2.2 查看JVM默认值



需要注意的是   如果查看参数配置   如果发现 有冒号 

![image-20200725152008862](assets\image-20200725152008862.png)

这个就是被修改过的,没有冒号的就是jdk的初始参数配置,没有被修改

##### 1 -XX:+PrintFlagsInitial

查看安装jdk的初始默认值



查看方式:

java -XX:+PrintFlagsInitial -version  

![image-20200725151604987](assets\image-20200725151604987.png)



##### 2 -XX:+PrintFlagsFinal

查看修改和更新的参数

查看方式:   java -XX:+PrintFlagsFinal -version



##### 3 PrintFlagsFinal举例,运行java命令的同时打印参数

java -XX:+PrintFlagsFinal -XX:MetspaceSize=512m  java文件

这样再去查看这两个参数 就是加了: 证明被修改过

##### 4 -XX:+PrintCommandLineFlags

 ![image-20200725153334959](assets\image-20200725153334959.png)



## 2.3  有哪些常用的参数

常用的上边出现的都常用

- -Xms

​	初始大小内存,默认物理内存1/64

​	等价于 :    -XX:InitialHeapSize

- -Xms

​	最大分配内存,默认为物理内存1/4

​	等价于    :     -XX:MaxHeapSize

- -Xss

​	设置单个线程栈的大小,一般默认为512k --- 1024k

默认是操作系统的的默认值 ,可以去查官网

​	等价于   -XX:ThreadStackSize

- -Xmn

​	设置年轻代大小

​	使用默认值

- -XX:MetaspaceSize

  默认是20多m

​	元空间的本质和永久代类似,都是对jvm规范中方法区的实现,不过元空间与永久代之间最大的区别在于

​	元空间并不在虚拟机中,而是使用本地内存,因此默认情况下,元空间的大小仅受本地内存限制

​	-Xms10m -Xmx10m -XX:MetaspaceSize=1024m -XX:+PrintFlagsFinal

- -XX:+PrintGCDetails

  查看GC信息如何查看GC日志信息,去查看JVM总结

  



- -XX:SuriviorRatio

​	调整比例新生代的比例,伊甸区,from区 to区的比例  默认是811

设置新生代中eden和s0/s1空间的比例

默认 -XX:SuriviorRatio=8,Eden:s0:s1=8:1:1

假如     

-XX:SuriviorRatio=4,Eden:s0:s1=4:1:1

SuriviorRatio的值就是设置eden区的比例占多少,s0/s1相同

不加这个参数就是按照默认811去分配



- -XX:NewRatio  

  配置年轻代和老年代在堆结构的占比默认 -XX:NewRatioc=2 新生代占1 老年代占2 年轻代整个堆的1/3

  假如 

  -XX:NewRatio=4 新生代占1 老年代占4 年轻代占整个堆的1/5    NewRatio的值就是设置老年代的占比,剩下的 

  新生代和年老代的比例

  可以看成这个数值 就是年老代/新生代的比值

- -XX:MaxTenuringThreshold

新生代进入年老代的阈值  设置垃圾的年龄

如果-XX:MaxTenuringThreshold=0,那么不经过survivor区,直接进入老年代,这样设置的话,对于年老代比较多的应用 这样效率比较高

如果这个值设置的比较高,则年轻代会在survivor进行多次复制,这样可以增加对象在年轻代中的存活时间,增加年轻代即被回收的概论

可以通过jinfo -flag  MaxTenuringThreshold    进程id  查看

默认是15   必须是0--15之间



- 常用参数设置

-Xms128m -Xmx4096m -Xss1024k  -XX:MetaspaceSize=521m  -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseSerialGC



-XX:+UseSerialGC  串行垃圾回收器

-XX:+UseParallelGC  并行垃圾回收器



# 3 强引用,软引用,弱引用,虚引用  分别是什么

大多数用的是强引用



## 3.1 整体架构

![image-20200725180631918](assets\image-20200725180631918.png)

## 3.2 强引用(默认支持模式)

当内存不足时,JVM开始垃圾回收,对于强引用的对象,就算是出现了OOM也不会对该对象进行回收,死都不会回收

```
强引用是我们最常见的普通 对象引用,只要还有强引用指向一个对象,就能表明对象还活着, 垃圾收集器就不会碰这种对象,在Java中最常见的就是强引用 , 把一个对象赋给一个引用变量,这个引用变量就是一个强引用,当一个对象被强引用变量引用时,他处于可达状态,他是不可能被垃圾回收机制回收的,即使该对象以后永远都不会被用到,即使这样JVM也不会对其进行回收,因此强引用是造成Java内存泄漏的主要原因之一

对于一个普通的对象,如果美欧其他引用关系 ,只要超过了引用的作用域或者显式地将相应(强)引用赋值为null,一般认为就是可以被垃圾收集的 (当然也需要看垃圾收集策略)
```

```java
        Object obj1 = new Object();
        Object obj2 = obj1;
        obj1 = null;
        System.gc();
        System.out.println(obj2);//java.lang.Object@1b6d3586
```



## 3.3 软引用

内存足够 我就不收  不够我就收了

软引用是一种相对强引用弱化了一些的引用,需要用java.lang.SoftReference类来实现,可以让对象豁免一些垃圾收集,对于软引用对象来说

​	系统内存足够时,  不会 被回收

​	系统内存不足时,   会   , 被回收

软引用通常用在对内存敏感的程序中,比如告诉缓存就需要用到软引用,内存足够的时候就保留,不够用就会进行回收

```java
package ref;

import java.lang.ref.SoftReference;

/**
 * ClassName softDemo
 * Description
 * Create by Jason
 * Date 2020/7/25 18:28
 */
public class softDemo {
    public static void main(String[] args) {
        soft_Ref_Mem_Enough();
    }

    /**
     * 产生大对象,让内存不够用,
     *  -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    public static void soft_Ref_Mem_Enough() {
        Object o = new Object();
        SoftReference<Object> soft_o = new SoftReference<>(o);
        System.out.println(o);
        System.out.println(soft_o.get());
        o=null;

        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println(o);//java.lang.ref.SoftReference@1b6d3586
            System.out.println(soft_o.get());//java.lang.Object@1b6d3586
        }
    }

    public static void soft_Ref_Mem_NoEnough() {
        Object o = new Object();
        SoftReference<Object> soft_o = new SoftReference<>(o);
        System.out.println(o);//java.lang.ref.SoftReference@1b6d3586
        System.out.println(soft_o.get());//java.lang.Object@1b6d3586
        o=null;
        System.gc();
        //内存够用
        System.out.println(o);//null
        System.out.println(soft_o.get());//java.lang.Object@1b6d3586
    }
}
	
```





## 3.4 弱引用

弱引用需要用java.lang.ref.WeakReference类来实现,他比软引用的生存周期更短

对于只有弱引用的对象来说,只要垃圾回收机制一运行,不管JVM内存空间是否足够,都会回收该对象占用的内存

内存不管是否够用,只要gc就会回收

```java
package ref;

import java.lang.ref.WeakReference;

/**
 * ClassName weakRef
 * Description
 * Create by Jason
 * Date 2020/7/25 18:38
 */
public class weakRef {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weak = new WeakReference<>(o1);
        System.out.println(o1);//java.lang.Object@1b6d3586
        System.out.println(weak.get());//java.lang.Object@1b6d3586
        o1=null;
        System.gc();
        System.out.println(o1);//null
        System.out.println(weak.get());//null
    }
}
```

### 3.4.1 软引用和弱引用的使用场景

假如有一个应用需要读取大量的本地图片

- 如果每次读取图片都需要从硬盘中读取则会严重影响性能
- 如果一次性全部加载到内存中有可能造成内存溢出

此时使用软引用可以解决这问题

设计思路是:用一个HashMap来保存图片的路径和相应图片关联对象的软引用之间的映射关系,内存不足时,JVM会自动回收这些缓存图片对象所占用的空间,从而有效避免了OOM的问题

Map<String,SoftReference<Bitmap>> image=new HashMap<String,SoftReference<Bitmap>>();

### 3.4.2 弱引用中的WeakHashMap知道吗

在util包中 可以找到WeakHashMap

```java
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

```



## 3.5 虚引用

虚引用需要java.lang.ref.PhantomReference类来实现

顾名思义,就是形同虚设,与其他几种引用都不同,虚引用并不会决定对象的生命周期

如果一个对象仅持有虚引用,那么他就和没有任何引用一样,在任何时候都可能被垃圾回收器回收,他不能单独使用也不能通过他访问对象,虚引用必须和引用队列(ReferenceQueue)联合使用



虚引用的主要作用是跟踪对象被垃圾回收的状态,仅仅是提供了一种确保对象被finalize以后,做某些事情的机制.PhantomReference的get方法总是返回null,因此无法访问对应的引用对象,其意义在于说明一个对象已经进入finalization阶段,可以被gc回收,用来实现比finalization机制更灵活的回收操作



换句话说,设置虚引用关联的唯一目的,就是这个对象被收集器回收的时候收到一个系统通知或者后续添加进一步的处理

java技术允许使用finalize()方法在垃圾收集器将对象从内存中清除出去之前做必要的清理工作







### 3.5.1  引用队列

class ReferenceQueue<T>

在回收之前需要被引用队列保存

```java
package ref;

import java.lang.ref.ReferenceQueue;

/**
 * ClassName PhantomReference
 * Description
 * Create by Jason
 * Date 2020/7/26 11:34
 */
public class PhantomReference {
    public static void main(String[] args) throws InterruptedException {

        Object a = new Object();
        ReferenceQueue<Object> ref = new ReferenceQueue<>();
        java.lang.ref.PhantomReference<Object> phant = new java.lang.ref.PhantomReference<>(a, ref);
        System.out.println(a);
        System.out.println(phant.get());
        System.out.println(ref.poll());
        System.out.println("-----");

        a=null;
        System.gc();
        Thread.sleep(500);
        System.out.println(a);
        System.out.println(phant.get());
        System.out.println(ref.poll());
        /**
         * java.lang.Object@1b6d3586
         * null
         * null
         * -----
         * null
         * null
         * java.lang.ref.PhantomReference@4554617c
         */
    }
}

```



## 3.6 总结

java提供了四种引用类型,垃圾回收的时候,各自有各自的特点

referencequeue是用来配合引用工作的,没有referencequeue一样可以运行



创建引用的时候可以指定关联的队列,当gc释放对象内存的时候,会将引用加入到引用队列

如果程序发现某个虚引用已经被加入到引用队列中,那么就可以在所引用的对象的内存被回收之前,采取必要的行动,这相当于一种通知机制



当关联的引用队列中有数据的时候,意味着引用只想的堆内存中的对象被回收,通过这种方式,jvm允许我们在对象被销毁后,做一些我们自己想做的事情

## 3.7 GC Roots和四大引用小总结

![image-20200726114714133](assets\image-20200726114714133.png)

黑色箭头 强引用

# 4 OOM 的理解

## 4.1 java.long.StackOverflowError

```
Exception in thread "main" java.lang.StackOverflowError
	at oom.StackOverFlowErrorDemp.stackoverflow(StackOverFlowErrorDemp.java:15)
```

方法调用太多,把栈撑爆了

## 4.2 java.long.OutOfMemoryError:Java heap space

堆内存不足时会发生,堆干爆了

-Xms10m -Xmx 10m  

然后new一个50m的对象

就会报这个错

## 4.3 java.long.OutOfMemoryError:GC overhead limit exceeded

gc超过极限

GC回收超过时长会爆出outofmemoryerror,过长的定义是,超过98%的时间用来做GC,并且回收了不到2%的堆内存,连续多次GC,都只回收了不到2%的计算情况才会抛出这种异常,假如不抛出此异常,GC overhead limit 错误会导致什么情况呢?

那就是GC清理的这么点内存会瞬间被填满,迫使GC再次执行,以此往复,导致cpu使用率一直保持100%,并且GC没任何效果,导致性能急剧下降

 ```java
package oom;

import java.util.ArrayList;

/**
 * ClassName GCoverHeadDemo
 * Description
 * Create by Jason
 * Date 2020/7/26 12:32
 */
public class GCoverHeadDemo {
    public static void main(String[] args) {

        int i = 0;
        ArrayList<String> list = new ArrayList<>();
        /**
         * 将 内存调小
         * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
         */
        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Throwable e) {
            System.out.println("i+++++++++:" + i);
            e.printStackTrace();
            throw e;
        }
    }
}

 ```



```
[Full GC (Ergonomics) [PSYoungGen: 2047K->2047K(2560K)] [ParOldGen: 7074K->7074K(7168K)] 9122K->9122K(9728K), [Metaspace: 3259K->3259K(1056768K)], 0.0389056 secs] [Times: user=0.05 sys=0.00, real=0.04 secs]xxxxxxxxxx [Full GC (Ergonomics)[PSYoungGen: 2047K->0K(2560K)]   回收之前是2047 回收后是0[ParOldGen: 7119K->625K(7168K)] 9167K->625K(9728K),  [Metaspace: 3290K->3290K(1056768K)], 0.0080259 secs] [Full GC (Ergonomics) [PSYoungGen: 2047K->2047K(2560K)] [ParOldGen: 7074K->7074K(7168K)] 9122K->9122K(9728K), [Metaspace: 3259K->3259K(1056768K)], 0.0389056 secs] [Times: user=0.05 sys=0.00, real=0.04 secs][Times: user=0.09 sys=0.00, real=0.01 secs] 
```



## 4.4 java.long.OutOfMemoryError:Direct buffer memory

导致原因:

写NIO经常使用ByteBuffer来读取或者写入数据,这是基于通道channel与缓冲区的I/O方式

它可以使用native函数库直接分配堆外内存,然后通过一个存储在java堆里边的DirectByBuffer对象作为这块内存的引用进行操作,这样能在一些场景中显著提高性能,因为避免了,java堆和native堆中来回复制数据

- ByteBuffer.allocate(capability)  第一种方式是分配JVM堆内存,属于GC管辖范围,由于需要拷贝所以速度相对较慢

- ByteBuffer.allocateDirect(capability) 第一种方式是分配os本地内存,不属于GC管辖,不需要内存拷贝所以速度相对较快



但如果不断分配本地内存,堆内存使用很少,jvm就不需要执行GC,DirectByteBuffer对象们就不会被回收,这时候堆内存充足,但本地内存可能已经使用光了,再次尝试分配本地内存就会出现OutOfMemoryError,这样程序就会崩溃

```java
package oom;

import java.nio.ByteBuffer;

/**
 * ClassName DirectBufferMemoryDemo
 * Description
 * Create by Jason
 * Date 2020/7/26 12:50
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的maxDirectMemory:"+(sun.misc.VM.maxDirectMemory()/(double)1024/1024)+"MB");
        try {
         Thread.sleep(3000);
            ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
	
```

```java
配置的maxDirectMemory:5.0MB
[GC (Allocation Failure) [PSYoungGen: 2048K->504K(2560K)] 2048K->880K(9728K), 0.0116936 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[GC (System.gc()) [PSYoungGen: 1108K->496K(2560K)] 1484K->1132K(9728K), 0.0009250 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (System.gc()) [PSYoungGen: 496K->0K(2560K)] [ParOldGen: 636K->982K(7168K)] 1132K->982K(9728K), [Metaspace: 3751K->3751K(1056768K)], 0.0092544 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
	at java.nio.Bits.reserveMemory(Bits.java:694)
	at java.nio.DirectByteBuffer.<init>(DirectByteBuffer.java:123)
	at java.nio.ByteBuffer.allocateDirect(ByteBuffer.java:311)
	at oom.DirectBufferMemoryDemo.main(DirectBufferMemoryDemo.java:16)
Heap
 PSYoungGen      total 2560K, used 95K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
  eden space 2048K, 4% used [0x00000000ffd00000,0x00000000ffd17e10,0x00000000fff00000)
  from space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
  to   space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
 ParOldGen       total 7168K, used 982K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
  object space 7168K, 13% used [0x00000000ff600000,0x00000000ff6f5860,0x00000000ffd00000)
 Metaspace       used 3781K, capacity 4536K, committed 4864K, reserved 1056768K
  class space    used 414K, capacity 428K, committed 512K, reserved 1048576K
```

## 4.5 java.long.OutOfMemoryError:ubable to create new native thread

达到创建线程的上限了,会报这个错误

高并发请求服务器时,经常出现如下的异常java.long.OutOfMemoryError:ubable to create new native thread

这个异常是与对应的平台有关的 

导致原因:

- 应用程序创建太多线程了,一个应用进程创建多个线程,超过系统承载极限

- 服务器不允许应用程序创建这么多线程,linux系统默认允许单个进程可以创建的线程数是1024个,当应用程序员创建超过这个数量的时候就会报这个异常

解决方案:

	- 想办法降低程序创建线程的数量,分析是否需要创建这么多线程,如果不是将线程数量降低
	- 可以修改linux服务器的配置,加大linux默认线程数量限制

```java
package oom;

/**
 * ClassName ubableThreadDemo
 * Description
 * Create by Jason
 * Date 2020/7/26 13:03
 */
public class ubableThreadDemo {
    public static void main(String[] args) {
        for(int i=1;    ;i++){
            System.out.println("-------------i"+i);
        new Thread(()->{
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },""+i).start();
        }
    }
}

```



```java
-------------i208084
Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
	at java.lang.Thread.start0(Native Method)
	at java.lang.Thread.start(Thread.java:717)
	at oom.ubableThreadDemo.main(ubableThreadDemo.java:19)
```

通过 ulimit -u 查看当前用户可以创建线程数量

会看到普通用户都是1024上限,而root用户则没有上限



vim /etc/security/limits.d/90-nproc.conf

![image-20200726131519924](assets\image-20200726131519924.png)



## 4.6 java.long.OutOfMemoryError:Metspace

使用 java -XX:+PrintFlagInitial命令查看本季度初始化参数, -XX:Metaspacesize为20m左右

JVM参数设置

-XX:MetaspaceSize=8m -XX:Maxspace=8m

来替代永久代

Metaspace是方法区在HotSpot中的实现,它与持久代最大的区别在于:Metaspace并不在虚拟机内存中而是使用本地内存,也即在java8中,classe metadata(the virtual machines internal presentation of java class ),被存储在叫做Metaspace的native memory

元空间存放了如下信息

虚拟机加载的类信息

常量池

静态变量

即时编译后的代码

模拟metaspace溢出,不断生成类往元空间灌,类战局空间总是会超过metaspace指定的空间大小,就会报这个错

# 5 GC垃圾回收算法和垃圾收集器的关系?

垃圾回收算法:4种

GC算法是内存回收的方法论 ,垃圾收集器是算法落地的实现



目前没有完美的收集器,需要因地制宜,一般采用,新生代年老代元空间选用相符合的垃圾收集器



## 5.1 四种常见的收集器

串行回收 Serial 

并行回收 Parallel

并发标记清除  CMS

G1  

![image-20200726133753210](assets\image-20200726133753210.png)



- 串行垃圾回收器 serial

为单线程环境设计且只使用一个线程进行垃圾回收,会暂停所有的用户线程,不适合服务器环境

- 并行垃圾回收器 parallel

多个垃圾收集器线程并行工作,此时用户线程是暂停的,适用于弱交互场景,允许短暂停顿

- 并发垃圾回收器

 用户线程和垃圾收集线程同时执行,不一定并行也可能交替执行,不需要停顿用户线程,这个是互联网公司多用的垃圾回收器

适用于对相应时间有要求的场景

- 前三个总结

![image-20200726140210336](assets\image-20200726140210336.png)

- G1垃圾回收器

java8才有的

G1垃圾回收器将堆内存分割成不同的区域然后并发的对其进行垃圾回收

# 6 如果查看服务器默认的垃圾收集器?如何配置?谈谈理解

# 6.1 如何查看默认垃圾收集器

![image-20200726140845612](assets\image-20200726140845612.png)

可以用打印 也可以用jinfo去查看  如果是 `-` 号就是没用

![image-20200726160251850](assets\image-20200726160251850.png)





## 6.2 默认的垃圾收集器有哪些

UseSerialGC   UseParallelGC    UseConcMarkSweepGC     UseParNewGC    UseParallelOldGC  UseG1GC

![image-20200726155555254](assets\image-20200726155555254.png)



### 6.3 垃圾收集器

![image-20200726160611234](assets\image-20200726160611234.png) 

![image-20200726160330376](assets\image-20200726160330376.png)



年老区和新生区各自应用的垃圾收集器



### 6.3.1 参数说明

DefNew       Default New Generation   默认新生代

Tenured       Old   

ParNew        Parallel New Generation  新生代并行回收

PSYoungGen   Parallel Scavenge YoungGen   新生代并行回收

ParOldGen    Parallel  Old   Generation   老年代

### 6.3.2 server/client模式分别是什么意思

![image-20200726161252455](assets\image-20200726161252455.png)

- 适用范围

只需要掌握Server模式即可,client基本不会用

- 操作系统
  - 32windows,不论是什么硬件都是默认使用clinet
  - 32其他操作系统,2g内存同时cpu在2以上,会使用server模式,低配是client
  - 64位统一为server模式

### 6.3.3 新生代

我们配置参数设置垃圾收集器,通常是先限定新生代,然后通过限定新生代,老年代自己会使用默认的

####  串行GC(serial) / (Serial Copying)

一个单线程的收集器,在进行垃圾收集的时候,必须暂停其他所有的工作线程直到他收集结束

![image-20200726162021517](assets\image-20200726162021517.png)



串行收集器是最古老,最稳定的最高效的收集器,只是用一个线程去回收但其在进行垃圾收集过程中可能会产生较长的停顿(Stop-The-World),虽然在收集垃圾过程中需要暂停所有其他的工作线程,但是他简单高效,对于限定单个cpu环境来说,没有线程交互的开销可以获得最高的单线程垃圾收集效率,因此Serial垃圾收集器已然是java虚拟机运行在client模式下默认的新生代垃圾收集器



对应JVM参数 :     -XX:+UserSerialGC

开启后会使用,Serial( Young区用)  +Serial Old (Old区用)  的收集器组合

表示: 新生代,老年代,都会使用串行回收收集器,新生代使用复制算法,老年代使用标记整理算法

- 1

```java
-Xms10m  -Xmx10m  -XX:+PrintGCDetails     -XX:+PrintCommandLineFlags  -XX:+UseSerialGC   (DefNew  + Tenured)
    配置UseSerialGC  就相当于 Serial + Serial Old  上边的那个图
```

```java
-XX:InitialHeapSize=1048576 -XX:MaxHeapSize=1048576 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseSerialGC 
[GC (Allocation Failure) [DefNew: 504K->64K(576K), 0.0008672 secs] 504K->429K(1984K), 0.0008986 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [DefNew: 566K->64K(576K), 0.0007955 secs] 932K->537K(1984K), 0.0008184 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
模拟GC
[GC (Allocation Failure) [DefNew: 562K->60K(576K), 0.0010120 secs][Tenured: 535K->595K(1408K), 0.0015241 secs] 1035K->595K(1984K), [Metaspace: 3139K->3139K(1056768K)], 0.0025770 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [Tenured: 595K->575K(1408K), 0.0012139 secs] 595K->575K(1984K), [Metaspace: 3139K->3139K(1056768K)], 0.0012455 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
Heap
 def new generation   total 576K, used 74K [0x00000000ffe00000, 0x00000000ffea0000, 0x00000000ffea0000)
  eden space 512K,  14% used [0x00000000ffe00000, 0x00000000ffe129e0, 0x00000000ffe80000)
  from space 64K,   0% used [0x00000000ffe90000, 0x00000000ffe90000, 0x00000000ffea0000)
  to   space 64K,   0% used [0x00000000ffe80000, 0x00000000ffe80000, 0x00000000ffe90000)
 tenured generation   total 1408K, used 575K [0x00000000ffea0000, 0x0000000100000000, 0x0000000100000000)
   the space 1408K,  40% used [0x00000000ffea0000, 0x00000000fff2ffa8, 0x00000000fff30000, 0x0000000100000000)
 Metaspace       used 3205K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 347K, capacity 388K, committed 512K, reserved 1048576K
java.lang.OutOfMemoryError: Java heap space
	at oom.GCDemo.main(GCDemo.java:17)

Process finished with exit code 0

```







#### 并行GC (  ParNew )

使用多线程进行垃圾回收,在垃圾收集时,会Stop-the-world暂停其他所有的工作线程直到它收集结束

![image-20200726191731675](assets\image-20200726191731675.png)

ParNew收集器其实就是**Serial收集器新生代的并行多线程版本**,新生代多线程收集,养老带还是单线程,最常见的应用场景就是配合老年代的CMS GC工作,其余行为和Serial收集器完全一样,ParNew垃圾收集器在垃圾收集过程中同样也要暂停所有其他的工作线程,踏实很多java虚拟机运行在server模式下的默认垃圾收集器

常用对应参数是

 -XX:+UseParNewGC  启动ParNew收集器,只影响新生代的收集,不影响老年代

开启上述参数后,会使用:ParNew(Young区用) +Serial Old的收集器组合,新生代使用复制算法,老年代采用标记整理算法

但是,ParNew +Tenured 这样的搭配,java已经不在推荐



备注:

-XX:ParallelGCThreads  控制线程数量,默认开启和cpu核数相同的线程数

```
-Xms10m  -Xmx10m  -XX:+PrintGCDetails     -XX:+PrintCommandLineFlags -XX:+UseParNewGC
(ParNew+Tenured) 新生代变为了并行收集  老年代还是Tenured
```

```java
-XX:InitialHeapSize=1048576 -XX:MaxHeapSize=1048576 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC 
[GC (Allocation Failure) [ParNew: 504K->64K(576K), 0.0005329 secs] 504K->461K(1984K), 0.0005784 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [ParNew: 566K->64K(576K), 0.0004551 secs] 964K->622K(1984K), 0.0004838 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
模拟GC
[GC (Allocation Failure) [ParNew: 544K->64K(576K), 0.0004045 secs][Tenured: 647K->648K(1408K), 0.0013787 secs] 1102K->648K(1984K), [Metaspace: 3122K->3122K(1056768K)], 0.0018198 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [Tenured: 648K->563K(1408K), 0.0015009 secs] 648K->563K(1984K), [Metaspace: 3122K->3122K(1056768K)], 0.0015309 secs] [Times: user=0.00 sys=0.01, real=0.00 secs] 
Heap
 par new generation   total 576K, used 65K [0x00000000ffe00000, 0x00000000ffea0000, 0x00000000ffea0000)
  eden space 512K,  12% used [0x00000000ffe00000, 0x00000000ffe10730, 0x00000000ffe80000)
  from space 64K,   0% used [0x00000000ffe90000, 0x00000000ffe90000, 0x00000000ffea0000)
  to   space 64K,   0% used [0x00000000ffe80000, 0x00000000ffe80000, 0x00000000ffe90000)
 tenured generation   total 1408K, used 563K [0x00000000ffea0000, 0x0000000100000000, 0x0000000100000000)
   the space 1408K,  40% used [0x00000000ffea0000, 0x00000000fff2ce50, 0x00000000fff2d000, 0x0000000100000000)
 Metaspace       used 3172K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 346K, capacity 388K, committed 512K, reserved 1048576K
java.lang.OutOfMemoryError: Java heap space
	at oom.GCDemo.main(GCDemo.java:17)
Java HotSpot(TM) 64-Bit Server VM warning: Using the ParNew young collector with the Serial old collector is deprecated and will likely be removed in a future release

```

```
Java HotSpot(TM) 64-Bit Server VM warning: Using the ParNew young collector with the Serial old collector is deprecated and will likely be removed in a future release
注意看这里  ParNew 与 Serial old不再推荐使用,可以去看上边的图,这两个不会在一起使用 
```













#### 并行回收GC(Parallel )  / (Parallel Scavenge)

![image-20200726193224729](assets\image-20200726193224729.png)



Parallel Scavenge 收集器类似ParNew也是一个新生代垃圾收集器,使用复制算法,也是一个并行的多线程垃圾收集器,速成吐吞量优先收集器,一句话,串行收集器在新生代和老年代的并行化

它关注的重点是:

可控制的吞吐量,( `Thought=运行用户代码时间/(运行用户代码时间+垃圾收集时间),也即比如程序运行100分钟,垃圾收集器1分钟,吞吐量就是99%).高吞吐量意味着高效利用CPU时间,多用于后台运算而不需要太多交互的任务` )

自适应调节策略也是ParallelScavenge收集器与ParNew收集器的一个重要区别(`自适应调节策略:虚拟机会根据当前系统的运行情况收集性能监控信息,动态调整这些参数以提供最合适的停顿时间  -XX:MaxGCPauseMillis    `) 或最大的吞吐量

常用JVM参数

  -XX:+UseParallelGC 或者  -XX:+UseParallelOldGC  可互相激活  使用ParallelScavenge收集器

-XX:ParallelGCThreads =N, 就是启动N个GC线程

cpu>8    N=5/8

CPU<8     N=实际个数

```
-Xms1m -Xmx1m    -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC
```

```java    
-Xms1m -Xmx1m -XX:+printGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC
```

```JAVA
-XX:InitialHeapSize=1048576 -XX:MaxHeapSize=1048576 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC 
[GC (Allocation Failure) [PSYoungGen: 504K->488K(1024K)] 504K->496K(1536K), 0.0007041 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 991K->488K(1024K)] 999K->616K(1536K), 0.0013084 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
模拟GC
[GC (Allocation Failure) [PSYoungGen: 998K->504K(1024K)] 1126K->720K(1536K), 0.0008138 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 504K->504K(1024K)] 720K->776K(1536K), 0.0004509 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 504K->413K(1024K)] [ParOldGen: 272K->193K(512K)] 776K->606K(1536K), [Metaspace: 3141K->3141K(1056768K)], 0.0036699 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 413K->480K(1024K)] 606K->737K(1536K), 0.0004762 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 480K->359K(1024K)] [ParOldGen: 257K->228K(512K)] 737K->588K(1536K), [Metaspace: 3141K->3141K(1056768K)], 0.0035759 secs] [Times: user=0.11 sys=0.00, real=0.00 secs] 
Heap
 PSYoungGen      total 1024K, used 419K [0x00000000ffe80000, 0x0000000100000000, 0x0000000100000000)
  eden space 512K, 11% used [0x00000000ffe80000,0x00000000ffe8ee30,0x00000000fff00000)
  from space 512K, 70% used [0x00000000fff00000,0x00000000fff59f58,0x00000000fff80000)
  to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
 ParOldGen       total 512K, used 228K [0x00000000ffe00000, 0x00000000ffe80000, 0x00000000ffe80000)
  object space 512K, 44% used [0x00000000ffe00000,0x00000000ffe393c0,0x00000000ffe80000)
 Metaspace       used 3210K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 347K, capacity 388K, committed 512K, reserved 1048576K
java.lang.OutOfMemoryError: Java heap space
	at oom.GCDemo.main(GCDemo.java:19)

Process finished with exit code 0

```



### 6.3.4 年老代

####  串行GC(serial) / (Serial Copying)

Serial Old 是Serial垃圾收集器是老年代版本,它同样是个单线程的收集器,使用标记-整理算法,这个收集器也主要是运行在clinet默认的java虚拟机默认的年老代垃圾收集器

在Server模式下,主要有两个用途

	1. jdk1.5之前版本中与新生代的Parallel Scavenge收集器搭配使用,( Parallel Scavenge +Serial Old)
 	2. 作为年老代中使用CMS收集器的后备垃圾收集方案

参数配置  这个配置已经被优化掉了,实际中已经不适用了,所以配置了无法识别

```JAVA
-Xms1m -Xmx1m    -XX:+PrintGCDetails -XX:+PrintCommandLineFlags  -XX:+UseSerialOldGC
```







#### 并行回收GC(Parallel )  / (Parallel Scavenge)

Parallel  Old收集器是Parallel Scavenge的老年代版本,使用多线程的标记-整理算法,Parallel  Old收集器在1.6才开始提供

1.6之前,新生代使用Parallel Scavenge收集器只能搭配老年代的Serial old 收集器,只能保证新生代的吞吐量优先,无法保证整体的吞吐量

​	1.6之前是(Parallel Scavenge + Serial old )



JVM常用参数

-XX:+UseParallelOldGC,使用Parallel Old收集器,设置该参数后,新生代Parallel+老年代Serial old 

```
-Xms1m -Xmx1m    -XX:+PrintGCDetails -XX:+PrintCommandLineFlags 
//这种不加就是默认的使用UseParallelGC
```

```
-Xms1m -Xmx1m    -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC
```

```java
-XX:InitialHeapSize=1048576 -XX:MaxHeapSize=1048576 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelOldGC 
[GC (Allocation Failure) [PSYoungGen: 504K->488K(1024K)] 504K->504K(1536K), 0.0006275 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 991K->488K(1024K)] 1007K->612K(1536K), 0.0005252 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
模拟GC
[GC (Allocation Failure) [PSYoungGen: 986K->504K(1024K)] 1110K->708K(1536K), 0.0007653 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 504K->488K(1024K)] 708K->716K(1536K), 0.0004700 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 488K->421K(1024K)] [ParOldGen: 228K->172K(512K)] 716K->594K(1536K), [Metaspace: 3141K->3141K(1056768K)], 0.0043930 secs] [Times: user=0.05 sys=0.02, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 421K->488K(1024K)] 594K->668K(1536K), 0.0011050 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 488K->410K(1024K)] [ParOldGen: 180K->165K(512K)] 668K->576K(1536K), [Metaspace: 3141K->3141K(1056768K)], 0.0039746 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Heap
 PSYoungGen      total 1024K, used 479K [0x00000000ffe80000, 0x0000000100000000, 0x0000000100000000)
  eden space 512K, 13% used [0x00000000ffe80000,0x00000000ffe915c0,0x00000000fff00000)
  from space 512K, 80% used [0x00000000fff00000,0x00000000fff668d8,0x00000000fff80000)
  to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
 ParOldGen       total 512K, used 165K [0x00000000ffe00000, 0x00000000ffe80000, 0x00000000ffe80000)
  object space 512K, 32% used [0x00000000ffe00000,0x00000000ffe297c8,0x00000000ffe80000)
 Metaspace       used 3210K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 347K, capacity 388K, committed 512K, reserved 1048576K
java.lang.OutOfMemoryError: Java heap space
	at oom.GCDemo.main(GCDemo.java:19)

Process finished with exit code 0

```

#### 并发标记清除GC(CMS)

![image-20200726200033878](assets\image-20200726200033878.png)

CMS收集器(`Concurrent Mark Sweep : 并发标记清除`),是一种以获取最短回收停顿时间为目标的收集器,适合应用在互联网站或者B/S系统,希望停顿时间最短

CMS非常适合堆内存大 ,CPU核数多的服务器端使用,也是G1出现之前大型应用的首选收集器





4步过程

- 初始标记(  CMS initial  mark)

只是标记一下GC Roots能直接关联的对象,速度很快,仍然需要暂定所有的工作线程

- 并发标记( CMS concurrent mark )  和用户线程一起

 进行GC Roots跟踪的过程,和用户线程一起工作,不需要暂停工作线程,主要标记过程,标记全部对象

- 重新标记( CMS remark)

为了修正在并发标记期间,因用户程序继续运行而导致标记产生变动的那一部分对象的标记记录,仍然需要暂停所有的工作线程,由于并发标记时,用户线程仍然运行,因此在正式清理前,在做修正

- 并发清除(CMS concurrent sweep ) 和用户线程一起

清楚GC Roots不可达对象,和用户线程一起工作,不需要暂停工作线程,基于标记结果,直接清理对象

由于耗时最长的标记兵法和并发清除过程中,垃圾收集线程可以和用户一起并发工作,所以总体上来看CMS收集器的内存回收和用户线程是一起并发的执行

![image-20200726201459821](assets\image-20200726201459821.png)





Concurrent Mark Sweep 并发标记清除,并发收集低停顿,并发指的是与用户线程一起执行

开启该收集器的JVM参数,    -XX:+UseConcMarkSweepGC 开启参数后会自动将  -XX:+UseParNewGC打开

开启该参数后,ParNew(young区用)  +CMS(old区用)+Serial Old的收集器组合, serial old 将作为CMS出错的后备收集器

```
-Xms1m -Xmx1m    -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC
```

优点: 并发收集停顿低

缺点:对cpu资源压力较大,采用的标记清除算法会导致大量碎片,

​	CMS必须在老年代堆内存用尽之前完成垃圾回收,否则会造成CMS回收失败,会触发担保机制,串行老年代收集器将会以STW的方式进行一次GC,从而造成较大的停顿时间

​	标记清除算法无法整理空间碎片,老年代空间会随着应用时长被逐步耗尽,最后不得不通过担保机制对堆内存进行压缩,CMS也提供了参数 `-XX:CMSFullGCsBeForeCompaction`,默认是0,即每次都进行内存整理 来指定多少次CMS收集之后,进行一次压缩的FullGC

```java
-XX:InitialHeapSize=5242880 -XX:MaxHeapSize=5242880 -XX:MaxNewSize=1400832 -XX:MaxTenuringThreshold=6 -XX:NewSize=1400832 -XX:OldPLABSize=16 -XX:OldSize=2793472 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC 
[GC (Allocation Failure) [ParNew: 1088K->128K(1216K), 0.0021946 secs] 1088K->565K(6016K), 0.0022521 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
模拟GC
[GC (Allocation Failure) [ParNew: 524K->127K(1216K), 0.0009043 secs][CMS: 563K->589K(4800K), 0.0016309 secs] 961K->589K(6016K), [Metaspace: 3110K->3110K(1056768K)], 0.0025842 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [CMS: 589K->572K(4800K), 0.0013045 secs] 589K->572K(6016K), [Metaspace: 3110K->3110K(1056768K)], 0.0013290 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 4668K(4800K)] 4690K(6016K), 0.0006282 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[GC (Allocation Failure) [ParNew: 43K->21K(1216K), 0.0003399 secs][CMS[CMS-concurrent-mark: 0.001/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
 (concurrent mode failure): 4668K->568K(4800K), 0.0029228 secs] 4711K->568K(6016K), [Metaspace: 3117K->3117K(1056768K)], 0.0032999 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [ParNew: 43K->14K(1216K), 0.0003410 secs][CMS: 4664K->567K(4800K), 0.0012575 secs] 4708K->567K(6016K), [Metaspace: 3118K->3118K(1056768K)], 0.0016364 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 4663K(4800K)] 4663K(6016K), 0.0002081 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[GC (Allocation Failure) [ParNew: 65K->6K(1216K), 0.0005485 secs][CMS[CMS-concurrent-mark: 0.001/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
 (concurrent mode failure): 4663K->567K(4800K), 0.0025739 secs] 4728K->567K(6016K), [Metaspace: 3118K->3118K(1056768K)], 0.0031716 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [ParNew: 43K->10K(1216K), 0.0004042 secs][CMS: 4663K->567K(4800K), 0.0011560 secs] 4706K->567K(6016K), [Metaspace: 3123K->3123K(1056768K)], 0.0015949 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 4663K(4800K)] 4685K(6016K), 0.0001360 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[GC (Allocation Failure) [ParNew: 43K->12K(1216K), 0.0002285 secs][CMS[CMS-concurrent-mark: 0.001/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
 (concurrent mode failure): 4663K->567K(4800K), 0.0028184 secs] 4706K->567K(6016K), [Metaspace: 3130K->3130K(1056768K)], 0.0030837 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [ParNew: 43K->16K(1216K), 0.0002697 secs][CMS: 4663K->569K(4800K), 0.0011766 secs] 4707K->569K(6016K), [Metaspace: 3134K->3134K(1056768K)], 0.0014801 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 4665K(4800K)] 4665K(6016K), 0.0001446 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[GC (Allocation Failure) [ParNew: 43K->6K(1216K), 0.0002755 secs][CMS[CMS-concurrent-mark: 0.001/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
 (concurrent mode failure): 4665K->569K(4800K), 0.0030524 secs] 4708K->569K(6016K), [Metaspace: 3137K->3137K(1056768K)], 0.0033633 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [ParNew: 43K->14K(1216K), 0.0004807 secs][CMS: 4665K->570K(4800K), 0.0015798 secs] 4709K->570K(6016K), [Metaspace: 3139K->3139K(1056768K)], 0.0021058 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 4666K(4800K)] 4666K(6016K), 0.0002329 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[GC (Allocation Failure) [ParNew: 43K->12K(1216K), 0.0003555 secs][CMS[CMS-concurrent-mark: 0.001/0.003 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
 (concurrent mode failure): 4666K->578K(4800K), 0.0035267 secs] 4710K->578K(6016K), [Metaspace: 3139K->3139K(1056768K)], 0.0039377 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [CMS: 578K->577K(4800K), 0.0012316 secs] 578K->577K(6016K), [Metaspace: 3139K->3139K(1056768K)], 0.0012575 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Heap
 par new generation   total 1216K, used 99K [0x00000000ffa00000, 0x00000000ffb50000, 0x00000000ffb50000)
  eden space 1088K,   9% used [0x00000000ffa00000, 0x00000000ffa18f28, 0x00000000ffb10000)
  from space 128K,   0% used [0x00000000ffb30000, 0x00000000ffb30000, 0x00000000ffb50000)
  to   space 128K,   0% used [0x00000000ffb10000, 0x00000000ffb10000, 0x00000000ffb30000)
 concurrent mark-sweep generation total 4800K, used 577K [0x00000000ffb50000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 3215K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 347K, capacity 388K, committed 512K, reserved 1048576K
java.lang.OutOfMemoryError: Java heap space
	at oom.GCDemo.main(GCDemo.java:19)

Process finished with exit code 0

```

#### 6.4 如何选择垃圾收集器

![image-20200727100047656](assets\image-20200727100047656.png)

![image-20200727100138306](assets\image-20200727100138306.png)

# 7 G1垃圾收集器

## 7.1 以前收集器特点

年轻代和年老代是各自独立且连续的内存块

年轻代收集使用单eden+S0+S1进行复制算法

老年代收集必须扫描整个年老代区域

都是以尽可能少而快速的执行GC为设计原则

这些都是以前的特点

## 7.2 G1是什么 & 特点

![image-20200727110208153](assets\image-20200727110208153.png)

官网的描述中,可以看到,G1是一种服务器端的时垃圾收集器,应用在多处理器和大容量内存环境中,在实现高吞吐量的同事,尽可能满足垃圾收集暂停时间的要求,另外它还具有以下特性

- 像CMS收集器一样快,能与应用程序并发执行
-  整理空闲空间更快
- 需要更多的时间来预测GC停顿时间
- 不希望牺牲大量的吞吐性能
- 不需要更大的Java Heap

G1收集器的设计目标是取代CMS收集器,同CMS相比,在以下方面表现更为出色

他是一个有整理内存过程的垃圾收集器,不会产生很多内存碎片

他的Stop-The-World更可控 ,在停顿时间上添加了预测机制,用户可以指定希望停顿时间

 CMS垃圾收集器虽然少了暂停应用程序的运行时间,但是他还是存在内存碎片的问题,于是,为了取出内存碎片的问题,同时保留了CMS垃圾收集器低暂停时间的优点,JAVA7发布了G1垃圾收集器

jdk9中G1,默认替代了CMS,它是一款面向服务端应用的垃圾收集器,主应用于多cpu大内存的服务器环境,减少垃圾收集的停顿时间全面提高性能

---

明显的改变就是,Eden  Surivior Tenured 等内存区域不再是连续的,而是变成了一个个大小一样的region

每个region从1m 到32m不等,

一个region有可能属于Eden,Surivior或者Tenured内存区域

---

特点

G1充分利用多CPU,多核硬件优势,尽量缩短STW

G1在整体上采用标记-整理算法,局部是通过复制算法,不会产生内存碎片

宏观上看G1之中不再区分年轻代和年老代,把内存划分成多个独立的子区域(`region`),可以近似理解为一个围棋的棋盘

G1收集器里面讲整个内存区都混合在一起了,但其本身依然在小范围内要进行年轻代和年老代的区分,保留了新生代和年老代但是他们不在是物理隔离的,而是一部分region的集合且不需要region是连续的,也就是说依然会采用不同的GC方式来处理不同的区域

G1虽然也是分代收集器,但是整个内存分区不存在物理上的年轻代和老年代的区别,也不需要完全独立的survivior(to space) 堆做复制准备.G1只有逻辑上的分代概念,或者说每个分区都可能随G1运行在不同代之间的前后切换

## 7.3 底层原理

### 7.3.1 Region区域化垃圾收集器

最大的好处就是: 化整为零,避免全内存扫描,只需要按照区域来进行扫描即可  

---

区域化内存划片Region,整体编为了一系列不连续的内存区域,避免了全内存区的GC操作

核心思想就是整个堆内存区域分成大小相同的子区域region,在JVM启动时会自动设置这些子区域的大小,在堆的使用上,G1并不要求对象的存储一定是物理上连续的只要逻辑上连续即可,每个分区也不会固定的为某个代服务,可以按须在年轻代和年老代之间切换,启动时通过配置参数 -XX:G1HeapRegionSize=n,可指定分区大小(`1MB~~~32MB,必须是2的幂`),默认将整个堆划分为2048个分区

大小范围在1MB---32MB  ,最多能设置2048个区域,也即能够支持最大内存为:32MB*2048=64G内存



![image-20200727113133254](assets\image-20200727113133254.png)

![image-20200727113153386](assets\image-20200727113153386.png)

![image-20200727113249012](assets\image-20200727113249012.png)





### 7.3.2 回收步骤

G1收集器在YoungGC

针对Eden区进行收集,Eden区耗尽后会被处罚,主要是小区域收集+形成连续的内存块,避免内存碎片

- Eden区数据移动到Survivior区,假如出现Survivior区空间不够,Eden区数据会晋升到Old区
- survivor区的数据移动到新的survivor区,部分数据会晋升到Old区
- 最后Eden区收拾干净后,GC结束,用户的应用程序继续执行

![image-20200727113701118](assets\image-20200727113701118.png)

![image-20200727113733073](assets\image-20200727113733073.png)

### 7.3.3 4步过程

![image-20200727113801941](assets\image-20200727113801941.png)



## 7.4 案例

```
-Xms1m -Xmx1m    -XX:+PrintGCDetails -XX:+PrintCommandLineFlags  -XX:+UseG1GC
```

```java
-XX:InitialHeapSize=1048576 -XX:MaxHeapSize=1048576 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC -XX:-UseLargePagesIndividualAllocation 
[GC pause (G1 Evacuation Pause) (young), 0.0018479 secs]
   [Parallel Time: 0.9 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 189.2, Avg: 189.3, Max: 189.5, Diff: 0.3]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 1.1]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.4, Avg: 0.5, Max: 0.6, Diff: 0.2, Sum: 4.3]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.2]
         [Termination Attempts: Min: 1, Avg: 2.4, Max: 4, Diff: 3, Sum: 19]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.2]
      [GC Worker Total (ms): Min: 0.5, Avg: 0.7, Max: 0.8, Diff: 0.3, Sum: 5.9]
      [GC Worker End (ms): Min: 190.0, Avg: 190.0, Max: 190.0, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.3 ms]
   [Other: 0.6 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.4 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 1024.0K(1024.0K)->0.0B(1024.0K) Survivors: 0.0B->1024.0K Heap: 1024.0K(2048.0K)->648.1K(2048.0K)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC pause (G1 Humongous Allocation) (young) (initial-mark) (to-space exhausted), 0.0039800 secs]
   [Parallel Time: 2.6 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 233.3, Avg: 233.3, Max: 233.4, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 2.3, Avg: 2.3, Max: 2.4, Diff: 0.1, Sum: 18.4]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.7]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.6]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 2.4, Avg: 2.5, Max: 2.5, Diff: 0.1, Sum: 19.8]
      [GC Worker End (ms): Min: 235.8, Avg: 235.8, Max: 235.8, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.2 ms]
   [Other: 1.2 ms]
      [Evacuation Failure: 0.7 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.3 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 1024.0K(1024.0K)->0.0B(1024.0K) Survivors: 1024.0K->0.0B Heap: 1003.8K(2048.0K)->1003.8K(2048.0K)]
 [Times: user=0.02 sys=0.00, real=0.00 secs] 
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0000284 secs]
[GC concurrent-mark-start]
[GC pause (G1 Humongous Allocation) (young), 0.0014800 secs]
   [Parallel Time: 0.7 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 237.5, Avg: 237.5, Max: 237.7, Diff: 0.2]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.7]
      [Update RS (ms): Min: 0.0, Avg: 0.1, Max: 0.4, Diff: 0.4, Sum: 1.1]
         [Processed Buffers: Min: 0, Avg: 0.5, Max: 1, Diff: 1, Sum: 4]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Object Copy (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [Termination (ms): Min: 0.0, Avg: 0.2, Max: 0.4, Diff: 0.4, Sum: 2.0]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
      [GC Worker Total (ms): Min: 0.4, Avg: 0.5, Max: 0.5, Diff: 0.1, Sum: 3.8]
      [GC Worker End (ms): Min: 238.0, Avg: 238.0, Max: 238.0, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.3 ms]
   [Other: 0.5 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.3 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.0 ms]
   [Eden: 0.0B(1024.0K)->0.0B(1024.0K) Survivors: 0.0B->0.0B Heap: 1003.8K(2048.0K)->1003.8K(2048.0K)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure)  1003K->567K(2048K), 0.0024067 secs]
   [Eden: 0.0B(1024.0K)->0.0B(1024.0K) Survivors: 0.0B->0.0B Heap: 1003.8K(2048.0K)->567.9K(2048.0K)], [Metaspace: 2948K->2948K(1056768K)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure)  567K->553K(2048K), 0.0018185 secs]
   [Eden: 0.0B(1024.0K)->0.0B(1024.0K) Survivors: 0.0B->0.0B Heap: 567.9K(2048.0K)->553.7K(2048.0K)], [Metaspace: 2948K->2948K(1056768K)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC concurrent-mark-abort]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at oom.G1Demo.main(G1Demo.java:13)
Heap
 garbage-first heap   total 2048K, used 553K [0x00000000ffe00000, 0x00000000fff00010, 0x0000000100000000)
  region size 1024K, 1 young (1024K), 0 survivors (0K)
 Metaspace       used 3258K, capacity 4496K, committed 4864K, reserved 1056768K
  class space    used 353K, capacity 388K, committed 512K, reserved 1048576K

Process finished with exit code 1

```





## 7.5 常用参数配置

```
-XX:UseG1GC
-XX:G1HeapRegionSize=n 设置G1区域的大小,值是2的幂范围是1MB到32MB,目标是根据最小的java堆划分出约2048个区域

-XX:MXGCPauseMillis=n    最大GC停顿时间,这个是软目标,jvm尽可能停顿小于这个时间,单位是毫秒

-XX:InitiatingHeapOccupancyPercent=n  堆占用了多少的时候会触发GC,默认是45

-XX:ConcGCThreads=n  并发GC使用的线程数
 
-XX:G1ReservePercent=n  设置作为空闲空间的预留内存宝粉笔,以降低目标空间溢出的风险,默认值是10%

```

开始G1   +设置最大内存 +设置最大停顿时间

-XX:UseG1GC    -Xmx32g     -XX:MXGCPauseMillis=100 



## 7.6 和CMS相比较的优势

两个优势

- G1不会产生内存碎片
- 可以精确控制停顿.该收集器是把整个堆(新生代,养老代)划分成多个固定大小的区域,每次根据允许停顿的时间去收集垃圾最多的区域



 











