package com.weknowall.cn.wuwei.algorithm;

/**
 * User: laomao
 * Date: 2020/6/24
 * Time: 2:58 PM
 */
public class DynamicProgram {

    public static void main(String[] args) {


        /**
         * 给定一组数字，从这组数字中选择不挨着的数字，能组成的最大值
         *
         * 例如：1，2，4，1，7，8，3  最大值为15， 由1，4，7，3组成
         */

        int[] arr = {1, 2, 4, 1, 7, 8, 3};
        int opt = opt2(arr);
        System.out.println("最大值：" + opt);

//        System.out.println("fib数列的第5个值："+fib(6));
    }

    /**
     * 斐波那契数列的第n个数是多少  1 1 2 3 5 8.....
     *
     * @param num
     * @return
     */
    private static int fib(int num) {
        if (num<=2)
            return 1;

        int[] opt = new int[num];
        opt[0] = 1;
        opt[1] = 1;

        for (int i = 2; i < num; i++) {
            opt[i]=opt[i-1]+opt[i-2];
        }

        return opt[num-1];

    }

    /**
     * 使用递归实现
     * @param arr
     * @param index
     * @return
     */
    private static int opt(int[] arr, int index) {
        if (index == 0) {
            return arr[0];
        }

        if (index == 1) {
            return Math.max(arr[0], arr[1]);
        }

        int maxA = arr[index] + opt(arr, index - 2);
        int maxB = opt(arr, index - 1);

        return Math.max(maxA, maxB);

    }

    /**
     * 使用动态规划实现
     * @param arr
     * @return
     */
    private static int opt2(int[] arr){
        int[] opt =new int[arr.length];

        opt[0]=arr[0];
        opt[1]=Math.max(arr[0],arr[1]);

        for (int i=2;i<arr.length;i++){
            int optA=arr[i]+opt[i-2];
            int optB=opt[i-1];
            opt[i]=Math.max(optA,optB);
        }

        return opt[arr.length-1];
    }


    /**
     * 获取一串数字中的最长递增子序列
     * 例如： 2 5 1 5 4 5
     * 最长为3  2 4 5或1 4 5
     *
     * 思想：1.创建一个dp数组，用于存放最优解
     * 2.从第一个开始，得出每一个的最优解，后面的每一个，都要和前面的进行判断（因为前面的可能跳过多个后大小小于你），而
     * 如果满足条件，就拿这个最优解+1，并判断是否是最大的
     * 3.根据每一个最优解和max值做比较，得出最大值。
     * @param arr
     * @param num
     * @return
     */
    private static int getResult(int[] arr,int num){

        int[] dp=new int[num];
        int max=1;

        for (int i = 0; i < arr.length; i++) {
            dp[i]=1;

            for (int j = 0; j < i; j++) {
                if (arr[j]<arr[i]){
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
            }

            max=Math.max(max,dp[i]);
        }

        return max;
    }

}
