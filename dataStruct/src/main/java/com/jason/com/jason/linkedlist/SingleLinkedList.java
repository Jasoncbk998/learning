package com.jason.com.jason.linkedlist;

import java.util.Stack;

/**
 * ClassName SingleLinkedList
 * Description  定义SingleLinkedList 管理我们的英雄
 * Create by Jason
 * Date 2020/7/18 12:33
 */
public class SingleLinkedList {
    //初始化头节点,不存放具体数据
    private HeroNode head = new HeroNode(0, "", "");

    //返回头节点
    public HeroNode getHead() {
        return head;
    }

    //添加节点到单向链表
    //如果不考虑编号顺序时

    /**
     * 1.找到当前链表的最后节点
     * 2.将最后这节点的next指向新的节点
     */
    public void add(HeroNode heroNode) {
        //因为head节点不能动,因此我们需要一个辅助遍历temp
        HeroNode temp = head;//
        while (true) {
            //找到链表的最后
            if (temp.next == null) {
                break;
            }
            //如果没有找到最后,将temp后移
            temp = temp.next;
        }
        //当推出while循环时,temp就指向了链表的最后
        //将最后的这个节点next指向新的节点
        temp.next = heroNode;
    }

    //第二种方式在添加英雄时，根据排名将英雄插入到指定位置
    //(如果有这个排名，则添加失败，并给出提示)
    public void addByOrder(HeroNode heroNode) {
        //因为头节点不能动，因此我们仍然通过一个辅助指针(变量)来帮助找到添加的位置
        //因为单链表，因为我们找的temp 是位于 添加位置的前一个节点，否则插入不了
        HeroNode temp = this.head;
        boolean flag = false;//flag标志添加的编号是否存在,默认为false
        while (true) {
            if (temp.next == null) {//说明temp已经在链表的最后
                break;
            }
            /**
             * temp 是头结点
             * temp的下一个节点 跟传进来这个节点进行比较
             * temp和temp.next  是两个节点   把heroNode 夹在中间
             *
             */
            if (temp.next.no > heroNode.no) {//位置找到,就在temp后面加入
                break;
            } else if (temp.next.no == heroNode.no) {//说明希望添加的hearnode编号已经存在
                flag = true;//编号存在
                break;
            }
            temp = temp.next;// 后移,遍历当前链表
        }
        //判断flag的值
        if (flag) {//不能添加,说明编号已经存在
            System.out.printf("准备插入的英雄的编号,%d 已经存在,不能加入 \n ", heroNode.no);
        } else {
            //插入到链表中,temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    //修改节点的信息, 根据no编号来修改，即no编号不能改.
    //说明
    //1. 根据 newHeroNode 的 no 来修改即可
    public void update(HeroNode newHeroNode) {
        //判断是否是空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        //找到需要修改的节点,根据no编号
        //定义一个辅助变量
        HeroNode temp = head.next;
        boolean flag = false;//表示是否找到该节点
        while (true) {
            if (temp == null) {
                break;//已经遍历完链表
            }
            if (temp.no == newHeroNode.no) {
                //找到节点
                flag = true;
                break;
            }
            //推移节点
            temp = temp.next;//temp后移
        }
        //根据flag判断是否找到需要修改的节点
        if (flag) {
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        } else {
            //没有找到
            System.out.printf("没有找到编号 %d 的节点 ,不能修改 \n ", newHeroNode.no);
        }
    }

    //删除节点  找到删除该节点的前一个节点
    //思路
    //1. head 不能动，因此我们需要一个temp辅助节点找到待删除节点的前一个节点
    //2. 说明我们在比较时，是temp.next.no 和  需要删除的节点的no比较
    //删除 1 3 4 6  我要删除3  那我就找到1  然后 1 指向4  就相当于删除   被删除节点没有引用指向,删除的节点会被垃圾回收机制进行回收
    public void del(int no) {
        HeroNode temp = this.head;
        boolean flag = false;//标志是否找到待删除节点
        while (true) {
            if (temp.next == null) {//已经找到链表的最后
                break;
            }
            if (temp.next.no == no) {
                //找到待删除的节点签一个节点temp
                flag = true;
                break;
            }
            temp = temp.next;//temp后移,遍历
        }
        //判断flag
        if (flag) {
            //找到
            //可以删除
            temp.next = temp.next.next;
        } else {
            System.out.printf("要删除的 %d 节点不存在 \n ", no);
        }
    }

    //显示链表[遍历]
    public void list() {
        //判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        //因为头节点，不能动，因此我们需要一个辅助变量来遍历
        HeroNode temp = head.next;
        while (true) {
            //判断是否到链表最后
            if (temp == null) {
                break;
            }
            //输出节点的信息
            System.out.println(temp);
            //将temp后移， 一定小心
            temp = temp.next;
        }
    }

    //方式2：
    //这样不会破坏单链表的数据结构,可以做到逆序打印的效果
    //可以利用栈这个数据结构，将各个节点压入到栈中，然后利用栈的先进后出的特点，就实现了逆序打印的效果
    public void reversePrint(HeroNode head) {
        if (head.next == null) {
            return;// 空链表  不能打印
        }
        //创建一个栈,将各个节点压入栈
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.next;
        //将链表的所有节点压入栈
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;//cur后移,这样就可以压入下一个节点
        }
        //将栈中的节点进行打印 pop出栈
        while (stack.size() > 0) {
            System.out.println(stack.pop());//stack 特点是 先进后出
        }
    }


    //将单链表反转
    public void reversetList(HeroNode head) {
        //如果当前链表为空，或者只有一个节点，无需反转，直接返回
        if (head.next == null || head.next.next == null) {
            return;
        }
        //定义一个辅助的指针(变量),帮助我们遍历原来的链表
        HeroNode cur = head.next;
        HeroNode next = null;//指向当前节点cur的下一个节点
        HeroNode reverserHead = new HeroNode(0, "", "");

        //遍历原来的链表,每遍历一个节点,就将其取出,并放在新的链表reverserHead的最前端
        while (cur != null) {
            next = cur.next;//先暂时保存当前节点的下一个节点,因为后面需要使用
            cur.next = reverserHead.next;
        //将cur的下一个节点指向新的链表的最前端
            reverserHead.next = cur;//将cur连接到新的链表上
            cur = next;//将cur后移
        }
        //讲head.next 指向 reverseHead .next 实现单向链表的反转
        head.next = reverserHead.next;
    }

    //查找单链表中的倒数第k个结点 【新浪面试题】
    //思路
    //1. 编写一个方法，接收head节点，同时接收一个index
    //2. index 表示是倒数第index个节点
    //3. 先把链表从头到尾遍历，得到链表的总的长度 getLength
    //4. 得到size 后，我们从链表的第一个开始遍历 (size-index)个，就可以得到
    //5. 如果找到了，则返回该节点，否则返回nulll
    public HeroNode findLastIndexNode(HeroNode head, int index) {
        //判断如果链表为空，返回null
        if (head.next == null) {
            return null;//没有找到
        }
        //第一个遍历得到链表的长度(节点个数)
        int size = getLength(head);
        //第二次遍历  size-index位置,就是我们倒数的第k个节点
        if (index <= 0 || index > size) {
            return null;
        }
        //定义给辅助变量,for 循环定位到 倒数的index
        HeroNode cur = head.next;// 3   // 3-1= 2
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;

    }


    //方法：获取到单链表的节点的个数(如果是带头结点的链表，需求不统计头节点)

    /**
     * @param head 链表的头节点
     * @return 返回的就是有效节点的个数
     */
    public int getLength(HeroNode head) {
        if (head.next == null) { //空链表
            return 0;
        }
        int length = 0;
        //定义一个辅助的变量, 这里我们没有统计头节点
        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            cur = cur.next; //遍历   指向下一个节点
        }
        return length;
    }




}
