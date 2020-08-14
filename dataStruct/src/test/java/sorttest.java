import java.util.Arrays;

/**
 * @Classname sorttest
 * @Description TODO
 * @Date 2020/8/7 1:52 下午
 * @Created by jason
 */
public class sorttest {
    public static void main(String[] args) {
int arr[]={5,4,6,1};
sor(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void sor(int[] arr) {
        int value = 0;
        int index = 0;
        for (int i = 1; i < arr.length; i++) {
            value=arr[i];
            index=i-1;
            while(index>=0 && value<arr[index]){
                arr[index+1]=arr[index];
                index--;
            }
            arr[index+1]=value;


        }
    }
}
