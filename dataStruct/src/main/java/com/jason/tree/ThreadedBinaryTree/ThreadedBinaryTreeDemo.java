package com.jason.tree.ThreadedBinaryTree;

import com.jason.tree.binarytree.HeroNode;

/**
 * ClassName ThreadedBinaryTreeDemo
 * Description
 * Create by Jason
 * Date 2020/8/18 16:29
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
//测试一把中序线索二叉树的功能
        HeroNode root = new HeroNode(1, "tom");
        HeroNode node2 = new HeroNode(3, "jack");
        HeroNode node3 = new HeroNode(6, "smith");
        HeroNode node4 = new HeroNode(8, "mary");
        HeroNode node5 = new HeroNode(10, "king");
        HeroNode node6 = new HeroNode(14, "dim");
        //二叉树，后面我们要递归创建, 现在简单处理使用手动创建
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);
        //测试中序线索化
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
//        threadedBinaryTree.setRoot(root);
        threadedBinaryTree.threadedNodes();
    }
}

