package oom;

/**
 * ClassName G1Deemo
 * Description
 * Create by Jason
 * Date 2020/7/27 11:38
 */
public class G1Demo {
    public static void main(String[] args) {
        String str = "abc";
        while(true){
            str = str + new byte[4 * 1024 * 1024];
        }


    }
}
