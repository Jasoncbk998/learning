package leetcode;

import com.lovedata._题目总结.printer.LevelOrderPrinter;

import java.util.Arrays;

/**
 * @Classname test_088
 * @Description TODO
 * @Date 2020/9/3 7:07 下午
 * @Created by jason
 */
public class test_088 {
    public static void main(String[] args) {
        int[] nums1 = {1,2,3, 0, 0, 0};
        int[] nums2 = {6, 7, 6};
        sort(nums1, 3, nums2, 3);
        System.out.println(Arrays.toString(nums1));

    }

    public static void sort(int[] nums1, int m, int[] nums2, int n) {
     int i1=m-1;
     int i2=n-1;
     int cur=m+n-1;
     while(i1>=0 && i2>=0){
         if(i1>=0 && nums1[i1]<nums2[i2]){
             nums1[cur--]=nums2[i2--];
         }else {
             nums1[cur--]=nums1[i1--];
         }
     }
    }
}
