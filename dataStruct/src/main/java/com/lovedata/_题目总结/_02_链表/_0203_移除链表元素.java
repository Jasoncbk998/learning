package com.lovedata._题目总结._02_链表;

/**
 * https://leetcode-cn.com/problems/remove-linked-list-elements/
 * 删除给定值对应的节点
 * 删除链表中等于给定值 val 的所有节点。
 * 输入: 1->2->6->3->4->5->6, val = 6
 * 输出: 1->2->3->4->5
 */
public class _0203_移除链表元素 {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(3);
        ListNode l3 = new ListNode(4);
        ListNode l4 = new ListNode(5);
        ListNode l5 = new ListNode(6);
        l1.setNext(l2);
        l2.setNext(l3);
        l3.setNext(l4);
        l4.setNext(l5);

        ListNode listNode = removeElements(l1, 3);
        System.out.println(listNode);

    }
    public static ListNode removeElements(ListNode head, int val) {
        if (head == null) return null;
        // 新链表的头结点
        ListNode newHead = new ListNode(0);//这个是头结点,只是防止newHead和newTail为null
        // 新链表的尾结点
        ListNode newTail = newHead;
        while (head != null) {
            if (head.val != val) {
                newTail.next = head;
                newTail = head;
            }
            head = head.next;
        }
        newTail.next = null;
        //newHead的下一个节点就是我们找出来的新节点
        return newHead.next;
    }

    public ListNode removeElements2(ListNode head, int val) {
        if (head == null) return null;
        // 新链表的头结点
        ListNode newHead = null;
        // 新链表的尾结点
        ListNode newTail = null;
        while (head != null) {
            if (head.val != val) {
                // 将head拼接到newTail的后面
                if (newTail == null) {
                    newHead = head;
                    newTail = head;
                } else {
                    newTail.next = head;
                    newTail = head;
                }
            }
            head = head.next;
        }
        if (newTail == null) {
            return null;
        } else {
            // 尾结点的next要清空
            newTail.next = null;
        }
        return newHead;
    }
}
