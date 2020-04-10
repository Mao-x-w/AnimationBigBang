package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.Logs;
import com.weknowall.cn.wuwei.utils.image.ImageLoader;
import com.weknowall.cn.wuwei.utils.image.ImagePicker;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2020/4/9
 * Time: 10:01 AM
 */
public class OpenvcDemoActivity extends BaseActivity {

    @BindView(R.id.src_img)
    ImageView mSrcImg;
    @BindView(R.id.grey_img)
    ImageView mGreyImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openvc_demo);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.pick_img, R.id.grey_process})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pick_img:
                //自定义方法的单选
                ImagePicker.from(getContext())
                        .single(true)
                        .cropWHScale(1)
                        .listener(new ImagePicker.ImagePickCallback() {
                            @Override
                            public void onPicked(List<String> images) {
                                ImageLoader.display(getContext(),new File(images.get(0)),mSrcImg);
                            }

                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onError() {

                            }
                        }).picker();
                break;
            case R.id.grey_process:
                break;
        }
    }
}
