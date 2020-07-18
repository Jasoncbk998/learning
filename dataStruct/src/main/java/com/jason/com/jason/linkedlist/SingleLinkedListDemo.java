package com.jason.com.jason.linkedlist;



/**
 * ClassName SingleLinkedListDemo
 * Description
 * Create by Jason
 * Date 2020/7/18 12:23
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
    //测试
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        //创建链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();

        //加入 有序排序
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);

        System.out.println("原来链表的情况");
        singleLinkedList.list();
        /**
         * HeroNode{no=1, name='宋江', next='HeroNode{no=2, name='卢俊义', next='HeroNode{no=3, name='吴用', next='HeroNode{no=4, name='林冲', next='null'}'}'}'}
         * HeroNode{no=2, name='卢俊义', next='HeroNode{no=3, name='吴用', next='HeroNode{no=4, name='林冲', next='null'}'}'}
         * HeroNode{no=3, name='吴用', next='HeroNode{no=4, name='林冲', next='null'}'}
         * HeroNode{no=4, name='林冲', next='null'}
         */

        System.out.println("反转单链表");
//        System.out.println(singleLinkedList.getHead());
        singleLinkedList.reversetList(singleLinkedList.getHead());
        /**
         * HeroNode{no=4, name='林冲'}
         * HeroNode{no=3, name='吴用'}
         * HeroNode{no=2, name='小卢'}
         * HeroNode{no=1, name='宋江'}
         */

        //测试修改节点的代码
        HeroNode newHeroNode = new HeroNode(2, "小卢", "玉麒麟~~");
        singleLinkedList.update(newHeroNode);
        System.out.println("修改后的链表情况~~");
        singleLinkedList.list();
        /**
         * 修改后的链表情况~~
         * HeroNode{no=1, name='宋江', next='HeroNode{no=2, name='小卢', next='HeroNode{no=3, name='吴用', next='HeroNode{no=4, name='林冲', next='null'}'}'}'}
         * HeroNode{no=2, name='小卢', next='HeroNode{no=3, name='吴用', next='HeroNode{no=4, name='林冲', next='null'}'}'}
         * HeroNode{no=3, name='吴用', next='HeroNode{no=4, name='林冲', next='null'}'}
         * HeroNode{no=4, name='林冲', next='null'}
         */


        //测试删除
        //删除一个节点
        singleLinkedList.del(1);
        singleLinkedList.del(4);
        System.out.println("删除后的链表情况~~");
        singleLinkedList.list();

        //测试一下 求单链表中有效节点的个数
        System.out.println("有效的节点个数=" + singleLinkedList.getLength(singleLinkedList.getHead()));//2
        /**
         * 删除后的链表情况~~
         * HeroNode{no=2, name='小卢'}
         * HeroNode{no=3, name='吴用'}
         * 有效的节点个数=2
         */




        //测试一下看看是否得到了倒数第K个节点
        HeroNode res = singleLinkedList.findLastIndexNode(singleLinkedList.getHead(), 2);
        System.out.println("res=" + res);
    }
}
