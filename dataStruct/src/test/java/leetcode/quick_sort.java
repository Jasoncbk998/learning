package leetcode;

import java.util.Arrays;

/**
 * @Classname quick_sort
 * @Description TODO
 * @Date 2020/9/17 9:35 下午
 * @Created by jason
 */
public class quick_sort {
    public static void main(String[] args) {

        int[] nums = {7, 3, 7, 7, 7, 7, 7, 1};
        sort(nums, 0, nums.length);
        System.out.println(Arrays.toString(nums));
    }

    public static void sort(int[] nums, int begin, int end) {
        if (end - begin < 2) return;
        int mid = middle(nums, begin, end);
        sort(nums, begin, mid);
        sort(nums, mid + 1, end);
    }

    private static int middle(int[] nums, int begin, int end) {
        int pivot = nums[begin];
        end--;

        //7, 3,6,5 1
        /**
         * begin =0 end =4
         *  1<7
         *  7,3,6,5,1
         *  1,3,6,5,7
         *
         */
        while (begin < end) {
            //右
            while (begin < end) {
                if (nums[end] > pivot) {
                    end--;
                } else {
                    nums[begin++] = nums[end];
                    break;
                }
            }
        }
        /**
         * 1 7,3,6,5,4
         *
         */
        while (begin < end) {
            if (nums[begin] < pivot) {
                begin++;
            } else {
                nums[end--] = nums[begin];
                break;
            }
        }
        nums[begin] = pivot;
        return begin;

    }


}
