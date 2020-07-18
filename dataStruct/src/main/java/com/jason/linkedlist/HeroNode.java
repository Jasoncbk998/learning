package com.jason.linkedlist;

/**
 * ClassName HeroNode
 * Description
 * Create by Jason
 * Date 2020/7/18 12:32
 */
public class HeroNode {
    public int no;   //编号
    public String name;//名字
    public String nickname;//昵称
    public HeroNode next; //指向下一个节点

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public HeroNode getNext() {
        return next;
    }

    public void setNext(HeroNode next) {
        this.next = next;
    }

    /**
     * 不用吧节点也给加进去,但是他在内存也有存储
     * @return
     */
    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    //构造器
    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }
}
