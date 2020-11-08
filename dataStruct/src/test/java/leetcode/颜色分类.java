package leetcode;

import java.util.Arrays;

/**
 * @Classname 颜色分类
 * @Description TODO
 * @Date 2020/9/3 8:20 下午
 * @Created by jason
 */
public class 颜色分类 {
    public static void main(String[] args) {
        int[] nums = {1,0,2,1,1,0,2, 0, 1, 2};
        sort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void sort(int[] nums) {
        int i = 0;//遍历所有元素
        int l = 0;
        int r = nums.length - 1;//在最后,放2使用,发现是2就用r放置
       while(i<=r){
           if(nums[i]==0){
               swap(nums,i++,l++);
           }else if(nums[i]==1){
               i++;
           }else {
               swap(nums,i,r--);
           }
       }
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
