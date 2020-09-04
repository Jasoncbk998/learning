package com.lovedata.high_level._01_排序.sort.compare;

import com.lovedata.high_level._01_排序.sort.Sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@SuppressWarnings("unused")
public class ShellSort<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {
        List<Integer> stepSequence = sedgewickStepSequence();
        for (Integer step : stepSequence) {
            sort(step);
        }
    }

    /**
     * 分成step列进行排序
     */
    private void sort(int step) {
        // col : 第几列，column的简称
        for (int col = 0; col < step; col++) { // 对第col列进行排序
            // col、col+step、col+2*step、col+3*step
            for (int begin = col + step; begin < array.length; begin += step) {
                int cur = begin;
                //当前值和自己前一个值比较
                while (cur > col && cmp(cur, cur - step) < 0) {
                    swap(cur, cur - step);
                    cur -= step;
                }
            }
        }
    }

    /**
     * 生成步长step
     *   n/2^k
     *   k从0开始依次取
     * @return
     */
    private List<Integer> shellStepSequence() {
        List<Integer> stepSequence = new ArrayList<>();
        int step = array.length;
        while ((step >>= 1) > 0) {
            //	32 /2
            // 	16/2
            // 	8/2
            // 	...
            //不断除以2
            stepSequence.add(step);
        }
        return stepSequence;
    }

    /**
     * 最优步长
     * @return
     */
    private List<Integer> sedgewickStepSequence() {
        List<Integer> stepSequence = new LinkedList<>();
        int k = 0, step = 0;
        while (true) {
            if (k % 2 == 0) {
                int pow = (int) Math.pow(2, k >> 1);
                step = 1 + 9 * (pow * pow - pow);
            } else {
                int pow1 = (int) Math.pow(2, (k - 1) >> 1);
                int pow2 = (int) Math.pow(2, (k + 1) >> 1);
                step = 1 + 8 * pow1 * pow2 - 6 * pow2;
            }
            if (step >= array.length) break;
            stepSequence.add(0, step);
            k++;
        }
        return stepSequence;
    }
}
