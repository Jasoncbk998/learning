package lock;

import java.util.concurrent.ExecutionException;

/**
 * ClassName Phone
 * Description
 * Create by Jason
 * Date 2020/7/22 14:11
 */
public class Phone  {
    public synchronized void sendSms() throws Exception {
        System.out.println(Thread.currentThread().getName()+"\t sendSms" );
        sendEmail();
    }

    public synchronized void  sendEmail()throws Exception {
        System.out.println(Thread.currentThread().getName()+"\t sendEmail:");
    }
}
