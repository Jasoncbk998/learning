package com.lovedata.question;

/**
 * ClassName ListNode
 * Description
 * Create by Jason
 * Date 2020/9/18 10:15
 *
 *         ListNode l1 = new ListNode(2);
 *         ListNode l2 = new ListNode(4);
 *         ListNode l3 = new ListNode(3);
 *         l1.setNext(l2);
 *         l2.setNext(l3);
 *         System.out.println(l1);
 *         ListNode r1 = new ListNode(5);
 *         ListNode r2 = new ListNode(6);
 *         ListNode r3 = new ListNode(4);
 */
public class ListNode {
    int val;
    ListNode next;

    public ListNode() {
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    ListNode(int x) {
        val = x;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return val + " -> " + next;
    }
}