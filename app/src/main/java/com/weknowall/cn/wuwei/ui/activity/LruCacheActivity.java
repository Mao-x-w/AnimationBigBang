package com.weknowall.cn.wuwei.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.widget.ImageView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2020/5/7
 * Time: 7:15 PM
 */
public class LruCacheActivity extends BaseActivity {

    @BindView(R.id.lru_cache)
    ImageView mLruCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru_cache);
        ButterKnife.bind(this);

        long maxMemory = Runtime.getRuntime().maxMemory() / 1024;

        LruCache<String,Bitmap> lruCache=new LruCache<String, Bitmap>((int) (maxMemory/8)){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return super.sizeOf(key, value);
            }
        };
    }
}
