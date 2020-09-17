package com.lovedata._题目总结._02_链表;

import com.sun.prism.RenderTarget;

/**
 * https://leetcode-cn.com/problems/add-two-numbers/
 * <p>
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
public class _0002_两数相加 {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(3);
        l1.setNext(l2);
        l2.setNext(l3);

        ListNode r1 = new ListNode(5);
        ListNode r2 = new ListNode(6);
        ListNode r3 = new ListNode(4);
        r1.setNext(r2);
        r2.setNext(r3);
        ListNode listNode = addTwoNumbers2(l1, r1);
        System.out.println(listNode);

    }

    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
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
            int sum = v1 + v2 + carry;
            //进位
            carry = sum / 10;
            last.next = new ListNode(sum % 10);
            last = last.next;
        }
        if (carry > 0) {
            last.next = new ListNode(carry);
        }
        return dummyHead.next;
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode dummyHead = new ListNode(0);
        ListNode last = dummyHead;
        // 进位值
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
            int sum = v1 + v2 + carry;
            // 设置进位值
            carry = sum / 10;
            // sum的个位数作为新节点的值
            //只存放个位
            last.next = new ListNode(sum % 10);
            last = last.next;
        }
        // 检查最后的进位
        if (carry > 0) {
            // carry == 1
            last.next = new ListNode(carry);
        }
        return dummyHead.next;
    }
}
