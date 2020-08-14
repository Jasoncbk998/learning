import org.junit.Test;

import java.util.Arrays;

/**
 * @Classname insertSort
 * @Description TODO
 * @Date 2020/8/14 9:42 上午
 * @Created by jason
 */
public class insertSort {
    @Test
    public void test() {
        int arr[] = {8, 7, 5,6};
        int index = 0;
        int value = 0;
        for (int i = 1; i < arr.length; i++) {
            value = arr[i];
            index = i - 1;
            while (index >= 0 && value < arr[index]) {
                arr[index + 1] = arr[index];
                index--;
            }
            arr[index+1]=value;
        }
        System.out.println(Arrays.toString(arr));
    }
}
