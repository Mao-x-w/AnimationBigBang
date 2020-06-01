package com.weknowall.cn.wuwei.algorithm;

/**
 * User: laomao
 * Date: 2020/5/23
 * Time: 9:21 PM
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {4, 6, 9, 2, 1, 5};
        quickSort(arr, 0, arr.length - 1);

        for (int i : arr) {
            System.out.println(i + "");
        }
    }

    /**
     * 快速排序的思想：
     * 1.取第一个作为基准参数，然后以它为参考分为大于它的一波和小于它的一波
     * 2.分的时候，由于把第一个数取出来当基准了，所以可以先比较最后一个数和比较数的大小，大的话，让end--。小的话和第一个对换
     * 因为第一个已经空出来了，没啥问题。之后就拿第一个进行比较，因为最后一个也空了，也可以进行交互，直到最后比完
     * 3.拿到分割点的index后，左右部分再通过递归进行排序，直到顺序没问题。
     *
     * 注意：上面1、2排完之后，记得把比较值放到中间那个位置，也就是start位置（这个时候start和end相等）
     *
     * @param arr
     * @param start
     * @param end
     */
    private static void quickSort(int[] arr, int start, int end) {
        if (start >= end)
            return;

        int splitIndex = getSplitIndex(arr, start, end);

        quickSort(arr, 0, splitIndex - 1);
        quickSort(arr, splitIndex + 1, end);
    }

    private static int getSplitIndex(int[] arr, int start, int end) {
        int splitValue = arr[start];

        while (start < end) {
            while (start < end && arr[end] >= splitValue) {
                end--;
            }
            arr[start] = arr[end];

            while (start < end && arr[start] <= splitValue) {
                start++;
            }
            arr[end] = arr[start];
        }

        arr[start] = splitValue;

        return start;
    }


}
