package leetcode;

import com.lovedata.pro._03_图.MinHeap;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Classname 排序
 * @Description TODO
 * @Date 2020/9/5 6:51 下午
 * @Created by jason
 */
public class sort {
//    static int[] array = new int[]{6, 5, 4, 3, 2, 1};
    static int[] array = new int[]{ 3, 2, 1};
    @Test
    public void tttt(){
        int i = 28 / 10;
        System.out.println(i);
    }

    @Test
    public void sort3() {
        for (int begin = 1; begin < array.length; begin++) {
            //把begin插入到合适的位置
            //search是位置
            insert(begin, search(begin));
        }
        System.out.println(Arrays.toString(array));

    }

    private void insert(int begin, int dest) {
        int source = array[begin];
        for (int i = begin; i > dest; i--) {
            array[i] = array[i - 1];
        }
        array[dest] = source;
    }

    private int search(int index) {
        int begin = 0;
        int end = index;
        //end=1
        //3,2,1
        while (begin < end) {
            int mid = (begin + end) >> 1;//0
            if (array[index] < array[mid]) {
                //
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }

    @Test
    public void test() {
        System.out.println(((1 + 3) >> 1) >> 1);//相当于除2
        System.out.println((4)<<1);
    }

    @Test
    public void sort2() {
//        int[] array = new int[]{6,5,4, 6,5,4, 3,2,1};
        int[] array = new int[]{3, 2, 1};
        for (int end = array.length - 1; end > 0; end--) {
            int max = 0;//最大值索引
            for (int begin = 1; begin <= end; begin++) {
                if (array[max] < array[begin]) {
                    max = begin;
                }
            }
            int tmp = array[max];
            array[max] = array[end];
            array[end] = tmp;
        }
        System.out.println(Arrays.toString(array));
    }

    /**
     * 冒泡
     */
    @Test
    public void sort1() {
        int[] array = new int[]{3, 2, 1};
//        int length = array.length;
//        System.out.println(length);//3
        for (int end = array.length - 1; end > 0; end--) {
            int sortIndex = 1;
            for (int begin = 1; begin <= end; begin++) {
                if (array[begin] < array[begin - 1]) {
                    int tmp = array[begin];
                    array[begin] = array[begin - 1];
                    array[begin - 1] = tmp;
                    sortIndex = begin;
                }
            }
            end = sortIndex;
        }
        System.out.println(Arrays.toString(array));
    }
}
