package com.lovedata.pro._09_布隆过滤器;

public class BloomFilter<T> {
    /**
     * 二进制向量的长度(一共有多少个二进制位)
     */
    private int bitSize;
    /**
     * 二进制向量
     * long是八个字节可以表示64个二进制位置   int是4个字节
     * 1个字bai符等于1个字节 ，1个字节对应8位。
     * 在long内部是这样的
     * [63,0][127,64][131,128]
     * 整体上是右到左,但是内部是左到右
     */
    private long[] bits;//
    /**
     * 哈希函数的个数
     */
    private int hashSize;

    /**
     * 通过传递误判率和数据规模去判断合适的二进制向量长度和hash函数的个数
     *
     * @param n 数据规模
     * @param p 误判率, 取值范围(0, 1)
     */
    public BloomFilter(int n, double p) {
        if (n <= 0 || p <= 0 || p >= 1) {
            throw new IllegalArgumentException("wrong n or p");
        }

        double ln2 = Math.log(2);
        // 求出二进制向量的长度
        bitSize = (int) (-(n * Math.log(p)) / (ln2 * ln2));
        // 求出哈希函数的个数
        hashSize = (int) (bitSize * ln2 / n);
        // bits数组的长度
        bits = new long[(bitSize + Long.SIZE - 1) / Long.SIZE];
        // 每一页显示100条数据, pageSize
        // 一共有999999条数据, n
        // 请问有多少页 pageCount = (n + pageSize - 1) / pageSize
    }

    /**
     * 添加元素1
     */
    public boolean put(T value) {
        nullCheck(value);

        // 利用value生成2个整数
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        boolean result = false;
        for (int i = 1; i <= hashSize; i++) {
            //尽可能生成不同的整数值
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0) {
                //保证非负
                combinedHash = ~combinedHash;
            }
            // 生成一个二进位的索引
            int index = combinedHash % bitSize;
            // 设置index位置的二进位为1
            if (set(index)) {
                result = true;
            }
            //   101010101010010101
            // | 000000000000000100   1 << index
            //   101010111010010101
        }
        return result;
    }

    /**
     * 判断一个元素是否存在
     */
    public boolean contains(T value) {
        nullCheck(value);
        // 利用value生成2个整数
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        for (int i = 1; i <= hashSize; i++) {
            int combinedHash = hash1 + (i * hash2);
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            // 生成一个二进位的索引
            int index = combinedHash % bitSize;
            // 查询index位置的二进位是否为0
            if (!get(index)) return false;
        }
        return true;
    }

    /**
     * 设置index位置的二进位为1
     */
    private boolean set(int index) {
        long value = bits[index / Long.SIZE];
        /**
         * 比如 001 向左移1位,就是010  ,移2位是100
         * 通过这种办法在二进制中进行或运算,保证了第N个位可以设置为1
         */
        int bitValue = 1 << (index % Long.SIZE);//通过向左移位index % Long.SIZE

        bits[index / Long.SIZE] = value | bitValue; //或运算
        return (value & bitValue) == 0;
    }

    /**
     * 查看index位置的二进位的值
     *
     * @return true代表1, false代表0
     */
    private boolean get(int index) {
        long value = bits[index / Long.SIZE];
        return (value & (1 << (index % Long.SIZE))) != 0;
    }

    private void nullCheck(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null.");
        }
    }
}
