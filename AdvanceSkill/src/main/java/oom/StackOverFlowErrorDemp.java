package oom;

/**
 * ClassName StackOverFlowErrorDemp
 * Description
 * Create by Jason
 * Date 2020/7/26 11:57
 */
public class StackOverFlowErrorDemp {
    public static void main(String[] args) {
        stackoverflow();
    }

    /**
     * Exception in thread "main" java.lang.StackOverflowError
     * 	at oom.StackOverFlowErrorDemp.stackoverflow(StackOverFlowErrorDemp.java:15)
     */
    private static void stackoverflow() {
        stackoverflow();
    }
}
