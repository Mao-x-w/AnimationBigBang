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
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
 * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class checkNumInArray {

    public static void main(String[] args) {
//        checkNum();
//
//        int[] arr = {1, 4, 7, 9, 11};
//        int i = binarySearch(arr, 10);
//        System.out.println("位置：" + i);

        int[][] arr = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};

        boolean isFind = findNum2(1, arr);

        System.out.println("是否找到该值？：" + isFind);
    }

    private static boolean findNum(int target, int[][] arr) {

        for (int i = 0; i < arr.length; i++) {
            int[] rows = arr[i];

            if (binarySearch(target, rows)) {
                return true;
            }
        }

        return false;
    }

    private static boolean binarySearch(int target, int[] rows) {
        int start = 0;
        int end = rows.length - 1;

        while (start <= end) {
            int middle = (start + end) / 2;

            if (rows[middle] == target) {
                return true;
            } else if (rows[middle] > target) {
                end = middle - 1;
            } else if (rows[middle] < target) {
                start = middle + 1;
            }
        }

        return false;
    }

    private static boolean findNum2(int target, int[][] arr) {
        int row = 0;
        int col = arr[0].length - 1;

        while (row < arr.length && col >= 0) {
            if (arr[row][col] == target) {
                return true;
            } else if (arr[row][col] < target) {
                row++;
            } else if (arr[row][col] > target) {
                col--;
            }
        }
        return false;
    }
}
