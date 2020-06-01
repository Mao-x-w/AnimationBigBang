package com.weknowall.cn.wuwei.algorithm;

/**
 * User: laomao
 * Date: 2020/6/1
 * Time: 10:31 AM
 * <p>
 * 1, 5, 10
 * 3, 6, 20
 * 9, 10, 50
 * <p>
 * <p>
 * 1,4,7,9,11
 */
public class checkNumInArray {

    public static void main(String[] args) {
//        checkNum();

        int[] arr = {1, 4, 7, 9, 11};
        int i = binarySearch(arr, 10);
        System.out.println("位置：" + i);
    }

    private static int binarySearch(int[] arr, int checkNum) {
        int start = 0;
        int end = arr.length - 1;

        while (start < end) {
            int middle = (start + end) / 2;

            if (arr[middle] == checkNum) {
                return middle;
            } else if (arr[middle] < checkNum) {
                start = middle + 1;
            } else if (arr[middle] > checkNum) {
                end = middle - 1;
            }
        }

        return -1;
    }


}
