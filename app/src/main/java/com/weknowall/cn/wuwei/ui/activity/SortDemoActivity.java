package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weknowall.cn.wuwei.R;
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

        int[] ints = {2,9,4,6,3,9};

        bubbleSort(ints);

        for (int anInt : ints) {
            Logs.e("排序后："+anInt);
        }
    }

    private void bubbleSort(int[] arr) {
        for (int i = arr.length-1; i >0 ; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j]>arr[j+1]){
                    int tem=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=tem;
                }
            }
        }
    }


}
