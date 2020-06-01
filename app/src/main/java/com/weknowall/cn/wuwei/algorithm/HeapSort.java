package com.weknowall.cn.wuwei.algorithm;

import org.jetbrains.annotations.TestOnly;

/**
 * User: laomao
 * Date: 2020/5/23
 * Time: 1:18 PM
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] arr={2,4,3,7,9,5,1};
        heapSrot(arr);

        for (int i : arr) {
            System.out.println(i+"  ");
        }
    }

    /**
     * 堆排序思想：
     * 1.构建大顶堆
     * 2.拿堆顶第一个元素和最后一个置换
     * 3.堆顶进行一次堆的构建，再次让堆顶是最大值
     * 4.再次置换
     *
     * @param arr
     */
    private static void heapSrot(int[] arr) {
        createHeap(arr, arr.length - 1);

        for (int i = 0; i < arr.length; i++) {
            swap(arr,arr.length-1-i,0);
            heapify(arr,arr.length-2-i,0);
        }
    }

    /**
     * 构建堆
     * <p>
     * 1.拿到最后一个节点的父节点
     * 2.从该节点开始到起始位置，挨个构建堆
     *
     * @param arr
     * @param n
     */
    private static void createHeap(int[] arr, int n) {
        int lastIndex = n;
        int parentIndex = getParentIndex(lastIndex);

        for (int j = parentIndex; j >= 0; j--) {
            heapify(arr, n, j);
        }
    }

    private static void swap(int[] arr, int maxValueIndex, int j) {
        int temp = arr[j];
        arr[j] = arr[maxValueIndex];
        arr[maxValueIndex] = temp;
    }

    /**
     * 从上至下，递归构建堆
     *
     * @param arr
     * @param parentIndex
     */
    private static void heapify(int[] arr, int n, int parentIndex) {
        if (parentIndex >= n)
            return;

        int childLeft = getChildLeft(parentIndex);
        int childRight = getChildRight(parentIndex);

        int maxIndex = parentIndex;

        if (childLeft <= n && arr[childLeft] > arr[maxIndex]) {
            maxIndex = childLeft;
        }

        if (childRight <= n && arr[childRight] > arr[maxIndex]) {
            maxIndex = childRight;
        }

        if (maxIndex != parentIndex) {
            swap(arr, maxIndex, parentIndex);
            heapify(arr, n, maxIndex);
        }
    }

    private static int getParentIndex(int i) {
        return (i - 1) / 2;
    }

    private static int getChildLeft(int i) {
        return 2 * i + 1;
    }

    private static int getChildRight(int i) {
        return 2 * i + 2;
    }
}
