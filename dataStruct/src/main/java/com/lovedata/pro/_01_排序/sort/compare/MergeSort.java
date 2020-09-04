package com.lovedata.pro._01_排序.sort.compare;


import com.lovedata.pro._01_排序.sort.Sort;

/**
 * 归并跑排序
 *
 * @param <T>
 */
public class MergeSort<T extends Comparable<T>> extends Sort<T> {
    private T[] leftArray;

    @Override
    protected void sort() {
        //取原数组的一半
        leftArray = (T[]) new Comparable[array.length >> 1];
        sort(0, array.length);
    }

    // T(n) = T(n/2) + T(n/2) + O(n)

    /**
     * 对 [begin, end) 范围的数据进行归并排序
     */
    private void sort(int begin, int end) {
        if (end - begin < 2) return;
        int mid = (begin + end) >> 1;
        sort(begin, mid);
        sort(mid, end);
        merge(begin, mid, end);
    }

    /**
     * 将 [begin, mid) 和 [mid, end) 范围的序列合并成一个有序序列
     * li : left index  左边开始索引
     * le : left end  左边end索引
     * ri : right index 右边开始索引
     * re : right end 右边end索引
     * ai : array index 比较后的数组索引
     */
    private void merge(int begin, int mid, int end) {
        int li = 0, le = mid - begin;
        int ri = mid, re = end;
        int ai = begin;

        // 备份左边数组
        for (int i = li; i < le; i++) {
            leftArray[i] = array[begin + i];
        }

        // 如果左边还没有结束
        //left index < left end
        while (li < le) {
            //进行比对
            //array[ri] 右边的数组
            //如果 cmp(array[ri], leftArray[li]) <= 0 则会失去稳定性
            if (ri < re && cmp(array[ri], leftArray[li]) < 0) {
                array[ai++] = array[ri++];
            } else {
                array[ai++] = leftArray[li++];
            }
        }
    }
}
