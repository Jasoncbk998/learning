package com.jason.tree.arrbinarytree;

/**
 * @Classname ArrayBinaryTreeDemo
 * @Description TODO
 * @Date 2020/8/18 4:02 下午
 * @Created by jason
 */
public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 4, 5, 6, 7 };
        //创建一个 ArrBinaryTree
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        arrBinaryTree.preOrder(); // 1,2,4,5,3,6,7
    }
}
