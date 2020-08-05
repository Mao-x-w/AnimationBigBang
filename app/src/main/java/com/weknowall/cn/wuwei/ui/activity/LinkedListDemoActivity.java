package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.model.ListNode;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.Logs;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: laomao
 * Date: 2020/5/8
 * Time: 10:16 AM
 */
public class LinkedListDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_list);

//        systemLinkedList();

//        ListNode node = createLinkNode(0, 3);
//        ListNode node = createNodeList1(0, 3);

//        printNodes(node);
//
//        ListNode reverseNode = reverseNode(node);
//        Logs.e("反转后：：：：：：：：：：");
//        printNodes(reverseNode);

        ListNode node1 = creatOrderList1();
        ListNode node2 = creatOrderList2();

        ListNode concatList = concatList(node1, node2);

        printNodes(concatList);

    }

    private void printNodes(ListNode node) {
        ListNode temNode = node;
        while (temNode != null) {
            Logs.e("node值：" + temNode.val);
            temNode = temNode.next;
        }
    }

    private ListNode creatOrderList1() {
        ListNode node1 = new ListNode(1, null);
        ListNode node2 = new ListNode(2, null);
        ListNode node3 = new ListNode(5, null);
        node1.next = node2;
        node2.next = node3;
        return node1;
    }

    private ListNode creatOrderList2() {
        ListNode node1 = new ListNode(1, null);
        ListNode node2 = new ListNode(3, null);
        ListNode node3 = new ListNode(4, null);
        node1.next = node2;
        node2.next = node3;
        return node1;
    }

    private void deleteNode(ListNode node) {
    }

    private ListNode reverseNode(ListNode node) {
        if (node == null || node.next == null)
            return node;

        ListNode currentNode = reverseNode(node.next);
        node.next.next = node;
        node.next = null;
        return currentNode;
    }

    private ListNode createLinkNode(int i, int max) {
        if (i >= max - 1) {
            return new ListNode(i, null);
        }

        ListNode currentNode = new ListNode(i, createLinkNode(++i, max));

        return currentNode;
    }

    private ListNode reverse(ListNode head) {
        ListNode previous = null;

        while (head != null) {
            ListNode temp = head.next;
            head.next = previous;
            previous = head;
            head = temp;
        }
        return previous;
    }

    private void systemLinkedList() {
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>(0, 0.75f, true);

        linkedHashMap.put(0, 0);
        linkedHashMap.put(1, 1);
        linkedHashMap.put(2, 2);
        linkedHashMap.put(3, 3);
        linkedHashMap.put(4, 4);
        linkedHashMap.put(5, 5);
        linkedHashMap.put(6, 6);
        linkedHashMap.put(7, 7);

        Logs.d("取得的值：" + linkedHashMap.get(1));
        Logs.d("取得的值：" + linkedHashMap.get(2));

        linkedHashMap.put(7, 100);

        Map.Entry<Integer, Integer> toEvict = null;
        for (Map.Entry<Integer, Integer> entry : linkedHashMap.entrySet()) {
            Logs.d("遍历的值：" + entry.getKey() + ":" + entry.getValue());
            toEvict = entry;
        }

        Integer key = toEvict.getKey();
        Integer value = toEvict.getValue();
        linkedHashMap.remove(key);

        for (Map.Entry<Integer, Integer> entry : linkedHashMap.entrySet()) {
            Logs.d("移除后剩余的值：" + entry.getKey() + ":" + entry.getValue());
            toEvict = entry;
        }
    }

    /**
     * 合并2个有序列表
     *
     * @param node1
     * @param node2
     * @return
     */
    private ListNode concatList(ListNode node1, ListNode node2) {
        if (node1 == null)
            return node2;

        if (node2 == null)
            return node1;

        if (node1.val < node2.val) {
            node1.next = concatList(node1.next, node2);

            return node1;
        } else {
            node2.next = concatList(node1, node2.next);
            return node2;
        }
    }

}
