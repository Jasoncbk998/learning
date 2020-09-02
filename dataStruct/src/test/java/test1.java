import java.util.ArrayList;
import java.util.Arrays;

/**
 * ClassName test1
 * Description
 * Create by Jason
 * Date 2020/8/3 22:30
 */
public class test1 {
    public static  int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        int[] ints = Arrays.copyOf(nums, i+1);
        System.out.println(Arrays.toString(ints));
        return i + 1;
    }


    public  static  int aaa(int[]  nums){
        if(nums == null || nums.length == 0) return 0;
        int p = 0;
        int q = 1;
        while(q < nums.length){
            if(nums[p] != nums[q]){
                nums[p + 1] = nums[q];
                p++;
            }
            q++;
        }
        System.out.println(Arrays.toString(nums));
        return p + 1;
    }

    public static void main(String[] args) {
        int nums[]={1,2,2,3,4,4};
        int i = removeDuplicates(nums);
        System.out.println(i);
    }
}
