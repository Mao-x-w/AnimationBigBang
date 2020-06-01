package com.weknowall.cn.wuwei.algorithm;

import com.weknowall.cn.wuwei.model.ListNode;

/**
 * User: laomao
 * Date: 2020/5/27
 * Time: 2:20 PM
 */
public class LinkedList {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1, null);
        ListNode node2 = new ListNode(2, null);
        ListNode node3 = new ListNode(3, null);
        node1.next = node2;
        node2.next = node3;

        ListNode reverseNode = reverse1(node1);

        printNode(reverseNode);
    }

    private static void printNode(ListNode reverseNode) {
        ListNode currentNode = reverseNode;
        while (currentNode != null) {
            System.out.println(currentNode.val + "  ");
            currentNode = currentNode.next;
        }
    }

    private static ListNode reverse(ListNode node) {
        if (node == null || node.next == null)
            return node;

        ListNode currentNode = reverse(node.next);
        node.next.next = node;
        node.next = null;
        return currentNode;
    }

    private static ListNode reverse1(ListNode node) {
        ListNode previous = null;
        while (node != null) {
            ListNode temp = node.next;
            node.next = previous;
            previous = node;
            node = temp;
        }
        return previous;
    }

    private static void sort(ListNode node){

    }

}
