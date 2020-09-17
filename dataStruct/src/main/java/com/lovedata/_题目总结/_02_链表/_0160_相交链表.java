package com.lovedata._题目总结._02_链表;

/**
 * https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
 * AAABB
 * BBB
 * 变成这样
 * AAABB BBB
 * BBB AAABB
 * 整理一下
 * AAABBBBB
 * BBBAAABB
 * ^_^
 * 使用两个指针curA curB
 */
public class _0160_相交链表 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode curA = headA, curB = headB;
        while (curA != curB) {
            //如果curA是空则指向headB,完成替换链表
            curA = (curA == null) ? headB : curA.next;
            //如果curB是空则指向headA,替换链表
            curB = (curB == null) ? headA : curB.next;
            //不能这样,因为这样就会无限次拼接比较
            //  curB = (curB.next == null) ? headA : curB.next;
        }
        return curA;
    }
}
