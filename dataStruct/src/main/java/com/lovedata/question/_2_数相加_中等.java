package com.lovedata.question;

import javax.sound.midi.Soundbank;

/**
 * @Classname _2_数相加_中等
 * @Description TODO
 * @Date 2020/9/19 9:20 上午
 * @Created by jason
 */
public class _2_数相加_中等 {
    /**
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * <p>
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * <p>
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     *
     * @param args
     */
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(3);
        l1.setNext(l2);
        l2.setNext(l3);
//        System.out.println(l1);
        ListNode r1 = new ListNode(5);
        ListNode r2 = new ListNode(6);
        ListNode r3 = new ListNode(4);
        r1.setNext(r2);
        r2.setNext(r3);
        // 2,4,3
        // 5,6,4
        ListNode listNode = addTwoNumbers2(l1, r1);
        System.out.println(listNode);

    }

    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode node = new ListNode(0);
        int carry = 0;
        ListNode last = node;
        while (l1 != null || l2 != null) {
            int v1 = 0;
            if (l1 != null) {
                v1 = l1.val;
                l1 = l1.next;
            }
            int v2 = 0;
            if (l2 != null) {
                v2 = l2.val;
                l2 = l2.next;
            }
            int sum = carry + v1 + v2;
            carry = sum / 10;
            last.next = new ListNode(sum % 10);
            last = last.next;
        }
        if (carry > 0) {
            last.next = new ListNode(carry);
        }
        return node.next;
    }

    public static ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode dummyHead = new ListNode(0);
        ListNode last = dummyHead;
        int carry = 0;
        while (l1 != null || l2 != null) {

            int v1 = 0;
            if (l1 != null) {
                v1 = l1.val;
                l1 = l1.next;
            }
            int v2 = 0;
            if (l2 != null) {
                v2 = l2.val;
                l2 = l2.next;
            }
            // 2,4,3
            // 5,6,4
            //取出val
            /**
             * 第一次取值
             * 2+5+0=7
             * l1和l2分别指向下一个节点,7没有十位,则直接添加新节点为7
             *  carry=0
             * 第二次取值
             * 4+6+0=10
             * 进位carry=1
             * 个位=0
             * 所以添加新节点为0
             * 第三次取值
             * 3+4+1=8
             * 以此类推
             */
            int sum = carry + v1 + v2;
            carry = sum / 10;//取出十位
            //sum%10 个位
            last.next = new ListNode(sum % 10);//放入个位,如果大于10 则取个位,进位放在carry
            last = last.next;
        }
        if (carry > 0) {
            last.next = new ListNode(carry);
        }


        return dummyHead.next;
    }

}
