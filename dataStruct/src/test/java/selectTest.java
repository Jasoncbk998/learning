import java.util.Arrays;

/**
 * @Classname selectTest
 * @Description TODO
 * @Date 2020/8/7 11:08 上午
 * @Created by jason
 */
public class selectTest {
    public static void main(String[] args) {
        int nums[] = {5, 4, 3};
        select(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void select(int[] nums) {
        int min = 0;
        int minIndex = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            min = nums[i];
            minIndex = i;
            //找到最小值
            for (int j = i+1; j < nums.length; j++) {
                if (min > nums[j]) {
                    min = nums[j];
                    minIndex = j;
                }
            }
            //交换
            if (minIndex != i) {
                nums[minIndex] = nums[i];
                nums[i] = min;
            }
        }
    }
}
