package com.lovedata._题目总结._02_链表;

import com.sun.org.apache.regexp.internal.REUtil;

import java.util.ArrayList;

/**
 * https://leetcode-cn.com/problems/palindrome-linked-list/
 * 找到中间点
 * 翻转
 * 比对
 * 即可
 */
public class _0234_回文链表 {

    public static boolean isPalindrome(ListNode head) {
        //空链表为回文链表   只有一个节点的链表也是回文链表
        if (head == null || head.next == null) return true;
        //存在两个节点,1 2 节点相同则是,不同则不是
        if (head.next.next == null) return head.val == head.next.val;

        // 找到中间节点
        ListNode mid = middleNode(head);
        // 翻转右半部分（中间节点的右边部分）
        ListNode rHead = reverseList(mid.next);
        ListNode lHead = head;
        ListNode rOldHead = rHead;

        // 从lHead、rHead出发，判断是否为回文链表
        boolean result = true;
        while (rHead != null) {
            if (lHead.val != rHead.val) {
                result = false;
                break;
            }
            rHead = rHead.next;
            lHead = lHead.next;
        }

        // 恢复右半部分（对右半部分再次翻转）
        reverseList(rOldHead);
        return result;
    }

    /**
     * 找到中间节点（右半部分链表头结点的前一个节点）
     * 比如 1>2>3>2>1中的3是中间节点
     * 比如 1>2>2>1中左边第一个2是中间节点
     * 快慢两个指针,快指针比慢指针多走一步
     * 快指针变为null时,慢指针所指元素为中间点
     *
     * @param head
     * @return
     */
    private static ListNode middleNode(ListNode head) {
        ListNode fast = head;//快
        ListNode slow = head;//慢
        //1,2,3,4,   5  ,6,7,8,9
        //保证fast走不到null
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 翻转链表
     *
     * @param head 原链表的头结点
     *             比如原链表：1>2>3>4>null，翻转之后是：4>3>2>1>null
     * @return 翻转之后链表的头结点（返回4）
     */
    private static ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode tmp = head.next;
            head.next = newHead;
            newHead = head;
            head = tmp;
        }
        return newHead;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
//        head.next.next.next = new ListNode(2);
//        head.next.next.next.next = new ListNode(1);
        System.out.println(head);
        middleNode(head);
//        System.out.println(isPalindrome(head));
    }


}
