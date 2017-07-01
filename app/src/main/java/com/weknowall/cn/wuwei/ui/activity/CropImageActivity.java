package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.image.ImagePicker;
import com.weknowall.cn.wuwei.utils.image.ImagePickerDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2017-06-30
 * Time: 10-19
 */

public class CropImageActivity extends BaseActivity {

    @BindView(R.id.select_image)
    TextView mSelectImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.select_image)
    public void onViewClicked() {
        new ImagePickerDialog(getContext())
                .setImageCount(1)
                .isCameraCrop(true)
                .isSingleCrop(true)
                .cropWHScale((float) (16.0/9.0))
                .setImagePickerListener(new ImagePicker.ImagePickCallback() {
                    @Override
                    public void onPicked(List<String> images) {

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError() {

                    }
                })
                .show();
    }
}
