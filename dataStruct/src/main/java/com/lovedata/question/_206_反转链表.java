package com.lovedata.question;

/**
 * ClassName _206_反转链表
 * Description
 * Create by Jason
 * Date 2020/9/18 10:14
 */
public class _206_反转链表 {
    /**
     * 反转一个单链表。
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     * 进阶:
     * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
     *
     * @param args
     */
    public static void main(String[] args) {

        ListNode node = new ListNode(1);
        ListNode next = node.next;
    }

    public ListNode reverseList1(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
                ListNode nextTemp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextTemp;
        }

        return prev;
    }
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode p = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }


}
