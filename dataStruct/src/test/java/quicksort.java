import org.junit.Test;

/**
 * @Classname quicksort
 * @Description TODO
 * @Date 2020/8/14 1:47 下午
 * @Created by jason
 */
public class quicksort {
    @Test
    public void test() {
        int[] arr = {-9, 78, 0, 23};
        int l = 0;//左
        int r = arr.length;//右
        int left=l;
        int right=r;
        //中间值
        int middle = arr[(l + r) / 2];
        int temp = 0;
        //我们要保证 中间值左方的元素均小于中间值   其右方的元素均大于中间值
        while (l < r) {
            //左 < 中间
            while (arr[l] < middle) {
                l += 1;
            }
            while(arr[r]> middle){
                r-=1;
            }
            if( l >=r){
                break;
            }
            temp=arr[l];
            arr[l]=arr[r];
            arr[r]=temp;

            if(arr[l] == middle){
                r-=1;
            }
            if(arr[r] == middle){
                l+=1;
            }
        }
        if( l == r){
            l+=1;
            r-=1;
        }
        if(left<r){

        }

    }
}
