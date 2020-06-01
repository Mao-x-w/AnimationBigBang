package com.weknowall.cn.wuwei.utils;

/**
 * User: laomao
 * Date: 2020/5/18
 * Time: 8:29 PM
 */
public class BSTManager<E extends Comparable<E>> {

    private class TreeNode {
        E e;
        TreeNode left;
        TreeNode right;

        public TreeNode(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }

    private TreeNode root;
    private int size;

    private void add(E e) {
        root = add(this.root, e);
    }

    private TreeNode add(TreeNode root, E e) {
        if (root == null) {
            return new TreeNode(e);
        }

        if (e.compareTo(root.e) < 0) {
            root.left = add(root.left, e);
        } else if (e.compareTo(root.e) > 0) {
            root.right = add(root.right, e);
        }

        return root;
    }

    private boolean contains(E e) {
        return contains(this.root, e);
    }

    private boolean contains(TreeNode root, E e) {
        if (root == null)
            return false;

        if (e.compareTo(root.e) == 0)
            return true;
        else if (e.compareTo(root.e) < 0)
            return contains(root.left, e);
        else
            return contains(root.right, e);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTSString(root, 0, res);
        return res.toString();
    }

    // 生成以node为根节点，深度为depth的描述二叉树的字符串
    private void generateBSTSString(TreeNode root, int depth, StringBuilder res) {
        if (root == null) {
            res.append(generateDepthString(depth) + "null\n");
            return;
        }
        res.append(generateDepthString(depth) + root.e + "\n");
        generateBSTSString(root.left, depth + 1, res);
        generateBSTSString(root.right, depth + 1, res);

    }

    private String generateDepthString(int depth) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++)
            res.append("--");
        return res.toString();
    }

    public void preLoad() {
        preLoad(this.root);
    }

    /**
     * 前序遍历
     * @param root
     */
    private void preLoad(TreeNode root) {
        if (root == null)
            return;

        Logs.e(root.e+"");
        preLoad(root.left);
        preLoad(root.right);

    }
}
