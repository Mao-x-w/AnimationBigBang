package com.weknowall.cn.wuwei.model;

/**
 * User: laomao
 * Date: 2020/5/18
 * Time: 8:13 PM
 */
public class TreeNode<E extends Comparable<E>> {
    public E e;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(E e) {
        this.e = e;
        left = null;
        right = null;
    }
}
