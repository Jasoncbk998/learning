/**
 * ClassName RefCountGC
 * Description
 * Create by Jason
 * Date 2020/7/25 12:53
 */
public class RefCountGC {
    private byte[] bigSize = new byte[2 * 1024 * 1024];//这个成员属性唯一的作用就是占用一点内存
    Object instance = null;

    public static void main(String[] args)
    {
        RefCountGC objectA = new RefCountGC();
        RefCountGC objectB = new RefCountGC();
        objectA.instance = objectB;
        objectB.instance = objectA;
        objectA = null;
        objectB = null;

        System.gc();
    }
}
