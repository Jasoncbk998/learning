import org.junit.Test;

import java.util.Arrays;

/**
 * @Classname shellsort
 * @Description TODO
 * @Date 2020/8/7 2:58 下午
 * @Created by jason
 */
public class shellsort {
    @Test
    public void test3() {
        int ints[] = {8, 1, 3, 4, 2, 5,10};
        for (int gap = ints.length/2; gap < ints.length; gap /= 2) {
            for (int i = gap; i < ints.length; i++) {
                int j = i;
                int temp = ints[j];
                if (ints[j] < ints[j - gap]) {
                    while (j - gap >= 0 && temp < ints[j - gap]) {
                        ints[j] = ints[j - gap];
                        j -= gap;
                    }
                    ints[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(ints));
    }

    @Test
    public void test2() {
        int arr[] = {8, 1, 3, 4, 2, 5};
        for (int gap = arr.length / 2; gap < arr.length; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];//给temp找到合适的位置
                //说明arr[j]对应的值,位置不对,进入判断,通过while找到合适的位置并插入
                if (arr[j] < arr[j - gap]) {
                    //{8, 1, 3, 4, 2, 7, 9, 4, 1, 3};
                    //temp依次与其同一有序数组中的元素进行比较,找到适合位置进行插入
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    //j-=gap  重新给arr[j]赋值
                    arr[j] = temp;
                }
            }
        }


    }

    @Test
    public void test1() {
        int arr[] = {8, 1, 3, 4, 2, 7, 9, 4, 1, 3};
        //确定步长
        for (int gap = arr.length / 2; gap >= 0; gap /= 2) {
            //根据步长匹配元素
            for (int i = gap; i < arr.length; i++) {
                int j = i;//
                int temp = arr[j];//临时值
                if (arr[j] > arr[j - gap]) {
                    //排序
                    // temp < arr[j - gap] 说明 还没有给temp找到适合的位置
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        //移动
                        arr[j] = arr[i - gap];//发现j<j-gap 则赋值
                        j -= gap;//按照步长进行移位
                    }

                }


            }
        }


    }

    @Test
    public void test() {
        int arr[] = {8, 1, 3, 4, 2, 7, 9, 4, 1, 3};
        System.out.println(arr.length);
        int temp = 0;
        //gap=5
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            // i =5
            for (int i = gap; i < arr.length; i++) {
                //j=0
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j + gap];
                        arr[j + gap] = arr[j];
                        arr[j] = temp;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void sort2() {
        int arr[] = {8, 1, 3, 4, 2, 7, 9, 4, 1, 3};
        //gap=5
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //i = 5 6 7 8 9
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                //temp=arr[5]
                int temp = arr[j];
                //第一次j=5 arr[j=5]=7 arr[j-gap=0]=8 8>7
                //{8, 1, 3, 4, 2, 7, 9, 4, 1, 3};
                if (arr[j] < arr[j - gap]) {
                    //j=5 gap=5  temp=arr[5]=7   <   arr[j-gap=0]=8
                    while (j - gap >= 0 && temp < arr[j - gap]) {
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    //插入子数组内的数据
                    arr[j] = temp;
                }

            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
