package com.weknowall.cn.wuwei.algorithm;

/**
 * User: laomao
 * Date: 2020/5/25
 * Time: 4:54 PM
 */
public class MaxChildArray {

    public static void main(String[] args) {
        int[] arr = {6, -3, -2, 7, -15, 1, 2, 2};
        int i = maxChildArray2(arr);
        System.out.println(i);

    }

    /**
     * 实现的关键思路：从1开始，判断前一个数对当前达到最大值有没有贡献（>0就表示有贡献），有贡献就带上，没贡献就不带+0；
     * 而从1开始，每个i对应的数组存的就是当前几个加起来的值，而下一次循环到了之后，就会判断这个存的值（i-1）对当前有没有贡献，有
     * 的话就加上，可以断点一下就理解了。
     * @param arr
     * @return
     */
    private static int maxChildArray2(int[] arr) {
        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            arr[i]+=arr[i-1]>0?arr[i-1]:0;
            if (arr[i]>max)
                max=arr[i];
        }

        return max;
    }

    public static int maxChildArray(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            array[i] += array[i - 1] > 0 ? array[i - 1] : 0;
            max = Math.max(max, array[i]);
        }
        return max;
    }
}
