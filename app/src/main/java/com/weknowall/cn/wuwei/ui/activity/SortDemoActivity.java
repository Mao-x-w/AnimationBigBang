package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.model.ListNode;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.Logs;

/**
 * User: laomao
 * Date: 2020/5/8
 * Time: 7:38 PM
 */
public class SortDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_demo);

        int[] ints = {2, 9, 4, 6, 3, 9};

//        bubbleSort(ints);
//        selectSort(ints);
//
        quickSort(ints, 0, ints.length - 1);
        for (int anInt : ints) {
            Logs.e("排序后：" + anInt);
        }
//
//        int index = binarySearch(ints, 3);
//        Logs.e("二分查找到的位置是：" + index + "   对应值为：" + ints[index]);

//        ListNode nodeList = createNodeList();
//        sortLinkedList(nodeList);
//        printNodes(nodeList);

    }

    private void printNodes(ListNode node) {
        ListNode temNode = node;
        while (temNode != null) {
            Logs.e("node值：" + temNode.val);
            temNode = temNode.next;
        }
    }

    private ListNode createNodeList() {
        ListNode node1 = new ListNode(5, null);
        ListNode node2 = new ListNode(3, null);
        ListNode node3 = new ListNode(1, null);

        node1.next = node2;
        node2.next = node3;

        return node1;
    }

    private void bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tem = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tem;
                }
            }
        }
    }

    private void selectSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int tem = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tem;
                }
            }
        }
    }

    private int binarySearch(int[] arr, int num) {
        int start = 0;
        int end = arr.length - 1;

        while (start < end) {
            int mid = (start + end) / 2;
            if (arr[mid] == num) {
                return mid;
            } else if (arr[mid] < num) {
                start = mid;
            } else if (arr[mid] > num) {
                end = mid;
            }
        }
        return -1;
    }

    /**
     * 对列表进行冒泡排序
     *
     * @param node
     * @return
     */
    private ListNode sortLinkedList(ListNode node) {
        if (node == null || node.next == null)
            return node;

        ListNode current = node;
        ListNode end = null;

        while (current.next != end) {
            while (current.next != end) {
                if (current.val > current.next.val) {
                    int tem = current.val;
                    current.val = current.next.val;
                    current.next.val = tem;
                }
                current = current.next;
            }

            end = current;
            current = node;
        }

        return node;
    }

    /**
     * 快速排序，思想：选取第一个作为基准对比，先拿最右边的元素和它比较，遇到比它小的，把它放到左边
     * （因为左边第一个已经被拿出来作为参考，所以，可以把左边第一个用来存储遇到的那个值）
     * <p>
     * 之后就拿左边的元素和基准值比较，如果遇到比它大的，就把它放到右边
     * （这个时候右边的那个值已经在上面赋值给左边，所以现在可以用来接收左边的）
     * <p>
     * 之后接着循环比较，直到左右比较碰头。说明以基准值为比较，左边是比它小的，右边是比它大的，之后递归比较即可
     *
     * @param arr
     * @param low
     * @param high
     */
    private void quickSort(int[] arr, int low, int high) {
        if (low > high)
            return;

        int splitPosition = split(arr, low, high);
        quickSort(arr, low, splitPosition - 1);
        quickSort(arr, splitPosition + 1, high);
    }

    private int split(int[] arr, int low, int high) {
        int splitValue = arr[low];

        while (low < high) {
            while (low < high && arr[high] >= splitValue) {
                high--;
            }
            arr[low] = arr[high];

            while (low < high && arr[low] <= splitValue) {
                low++;
            }
            arr[high] = arr[low];
        }

        arr[low] = splitValue;

        return low;
    }

}
