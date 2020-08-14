import org.junit.Test;

import java.util.Arrays;

/**
 * @Classname selectSort
 * @Description TODO
 * @Date 2020/8/14 9:22 上午
 * @Created by jason
 */
public class selectSort {
    @Test
    public void select() {
        int arr[] = {8, 7, 6, 5, 4, 3, 2, 1};
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int minIndedx = i;
            int min = arr[i];
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j]) {
                    minIndedx = j;
                    min = arr[j];
                }
            }
            if (minIndedx != i) {
                arr[minIndedx] = arr[i];
                arr[i] = min;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

}

