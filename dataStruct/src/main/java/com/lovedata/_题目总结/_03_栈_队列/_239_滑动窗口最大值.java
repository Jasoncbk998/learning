package com.lovedata._题目总结._03_栈_队列;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode-cn.com/problems/sliding-window-maximum/
 * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
 * 输出: [3,3,5,5,6,7]
 * 解释:
 * <p>
 * 滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 * 1 [3  -1  -3] 5  3  6  7       3
 * 1  3 [-1  -3  5] 3  6  7       5
 * 1  3  -1 [-3  5  3] 6  7       5
 * 1  3  -1  -3 [5  3  6] 7       6
 * 1  3  -1  -3  5 [3  6  7]      7
 */
public class _239_滑动窗口最大值 {

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1) return new int[0];
        if (k == 1) return nums;
        //滑动窗口, 滑动窗口的滑动次数nums.length - (k - 1)
        int[] maxes = new int[nums.length - (k - 1)];
        // 当前滑动窗口的最大值索引
        int maxIdx = 0;
        // 求出前k个元素的最大值索引
        for (int i = 1; i < k; i++) {
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        // li是滑动窗口的最左索引
        for (int li = 0; li < maxes.length; li++) {
            // ri是滑动窗口的最右索引
            int ri = li + k - 1;
            if (maxIdx < li) { // 最大值的索引不在滑动窗口的合理范围内
                // 求出[li, ri]范围内最大值的索引
                maxIdx = li;
                for (int i = li + 1; i <= ri; i++) {
                    if (nums[i] > nums[maxIdx]) maxIdx = i;
                }
            } else if (nums[ri] >= nums[maxIdx]) { // 最大值的索引在滑动窗口的合理范围内
                maxIdx = ri;
            }
            maxes[li] = nums[maxIdx];
        }

        return maxes;
    }

    /**
     * 利用双端队列 可以从队列的头和尾进行添加元素
     * 利用窗口去存储索引,通过索引去比对元素
     * 举例:1,3,-1,-3,5,3,6,7   k=3
     * 1,3,-1,-3,5,3,6,7
     * | | r
     * 1,3,-1,-3,5,3,6,7
     * | | r
     * 窗口长度是3,窗口左端是l 右端是r,如下
     * r从数组中第一个元素开始: l小于0,r指向元素1,把1存入队列
     * 依次,当r移动到3时,l还是小于0,但是元素 3 > -1,所以将队列中存入的1取出,将3加入到队列中,保证队列的头元素是最大的
     * 当l继续向右移动,此时l指向1,r指向-1,这样窗口前后端都合法课,开始校验头元素是否在窗口内,不在则移除
     * @return
     */
    public int[] maxSlidingWindow_deque(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1) return new int[0];
        if (k == 1) return nums;

        int[] maxes = new int[nums.length - k + 1];

        // peek: 取值（偷偷瞥一眼）
        // poll: 删除（削）
        // offer: 添加（入队）
        //双端队列
        Deque<Integer> deque = new LinkedList<>();
        for (int ri = 0; ri < nums.length; ri++) {
            // 只要nums[队尾] <= nums[i]，就删除队尾
            //举例:
            /**
             * 此时队列中: 3,2
             * 现在进来的元素是8,则会把3,2都删除,然后走到下边将8 add进入队列
             */
            while (!deque.isEmpty() && nums[ri] >= nums[deque.peekLast()]) {
                // deque.pollLast();
                deque.removeLast();
            }

            // 将i加到队尾
            // deque.offerLast(ri);
            deque.addLast(ri);

            // 检查窗口的索引是否合法
            int li = ri - k + 1;
            if (li < 0) {
                continue;
            }

            // 检查队头的合法性
            //比较索引
            if (deque.peekFirst() < li) {
                // 队头不合法（失效，不在滑动窗口索引范围内）
                // deque.pollFirst();
                deque.removeFirst();
            }

            // 设置窗口的最大值
            //保证窗口的第一个元素是最大的
            maxes[li] = nums[deque.peekFirst()];
        }
        return maxes;
    }
}
