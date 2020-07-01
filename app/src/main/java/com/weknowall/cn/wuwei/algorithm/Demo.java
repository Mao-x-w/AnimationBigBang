package com.weknowall.cn.wuwei.algorithm;

import java.util.Scanner;

/**
 * User: laomao
 * Date: 2020/6/24
 * Time: 11:21 AM
 */
public class Demo {

    public static void main(String[] args){
        Scanner s=new Scanner(System.in);
        int num=s.nextInt();
        int[] arr=new int[num];
        for(int i=0;i<num;i++){
            arr[i]=s.nextInt();
        }

        System.out.print(getResult(arr,num));

    }

    public static int getResult(int[] arr,int num){
        int[] dp=new int[num];
        int max=1;
        for(int i=0;i<num;i++){
            dp[i]=1;
            for(int j=0;j<i;j++){
                if(arr[j]<arr[i]){
                    dp[i]=Math.max(dp[i],dp[j]+1);
                }
            }
            max=Math.max(max,dp[i]);
        }
        return max;
    }
}
