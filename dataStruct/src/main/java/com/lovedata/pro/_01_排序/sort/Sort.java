package com.lovedata.pro._01_排序.sort;

import com.lovedata.pro._01_排序.Student;
import com.lovedata.pro._01_排序.sort.compare.SelectionSort;
import com.lovedata.pro._01_排序.sort.compare.ShellSort;

import java.text.DecimalFormat;

//T extends Comparable<T> 使T具有可比较性
public abstract class Sort<T extends Comparable<T>> implements Comparable<Sort<T>> {

    protected T[] array;
    private int cmpCount;//记录比较多少次
    private int swapCount;//记录交换次数
    private long time;//排序用时
    private DecimalFormat fmt = new DecimalFormat("#.00");

    public void sort(T[] array) {
        if (array == null || array.length < 2) return;

        this.array = array;

        long begin = System.currentTimeMillis();
        sort();
        time = System.currentTimeMillis() - begin;
    }

    @Override
    public int compareTo(Sort<T> o) {
        int result = (int) (time - o.time);
        if (result != 0) return result;
        //比较次数
        result = cmpCount - o.cmpCount;
        if (result != 0) return result;
        //交换次数
        return swapCount - o.swapCount;
    }

    protected abstract void sort();

    /*
     * 返回值等于0，代表 array[i1] == array[i2]
     * 返回值小于0，代表 array[i1] < array[i2]
     * 返回值大于0，代表 array[i1] > array[i2]
     */
    protected int cmp(int i1, int i2) {
        cmpCount++;
        return array[i1].compareTo(array[i2]);
    }

    protected int cmp(T v1, T v2) {
        cmpCount++;
        return v1.compareTo(v2);
    }

    /**
     * 交换
     * @param i1
     * @param i2
     */
    protected void swap(int i1, int i2) {
        swapCount++;
        /**
         * temp=A
         * A=B
         * B=temp
         */
        T tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(cmpCount);
        String swapCountStr = "交换：" + numberString(swapCount);
        String stableStr = "稳定性：" + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
                + stableStr + " \t"
                + timeStr + " \t"
                + compareCountStr + "\t "
                + swapCountStr + "\n"
                + "------------------------------------------------------------------";

    }

    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }

    /**
     * 是否稳定
     * @return
     */
    private boolean isStable() {
        if (this instanceof RadixSort) return true;
        if (this instanceof CountingSort) return true;
        if (this instanceof ShellSort) return false;
        if (this instanceof SelectionSort) return false;
        Student[] students = new Student[20];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(i * 10, 10);
        }
        sort((T[]) students);
        for (int i = 1; i < students.length; i++) {
            int score = students[i].score;
            int prevScore = students[i - 1].score;
            if (score != prevScore + 10) return false;
        }
        return true;
    }
}
