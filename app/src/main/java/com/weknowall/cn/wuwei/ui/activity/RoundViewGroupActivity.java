package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.widget.image.WebImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2019-03-05
 * Time: 15-14
 */
public class RoundViewGroupActivity extends BaseActivity {

    @BindView(R.id.image_view)
    WebImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_view_group);
        ButterKnife.bind(this);
        mImageView.setImageUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551781262151&di=629668bb4c6a77889a4506d82758df5c&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fa08b87d6277f9e2f362df4ef1530e924b899f339.jpg");
    }
}
