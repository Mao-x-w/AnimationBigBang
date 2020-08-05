package com.weknowall.cn.wuwei.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.Logs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2020/6/12
 * Time: 3:51 PM
 */
public class SAFDemoActivity extends BaseActivity {

    @BindView(R.id.image_view)
    ImageView mImageView;
    @BindView(R.id.crop_image_view)
    ImageView mCropImageView;
    private Uri mUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_saf_demo);
        ButterKnife.bind(this);

    }

    private void searchFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    private void createFile(String mimeType, String fileName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            mUri = data.getData();
            Logs.d("URI:::" + mUri.toString());

            mImageView.setImageURI(mUri);
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Uri cropUri = data.getData();

        } else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            Uri createUri = data.getData();
            Logs.d("URI:::" + createUri.toString());

        }

    }

    @OnClick({R.id.get_image, R.id.crop_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_image:
                searchFile();
                break;
            case R.id.crop_image:
                createFile("text/*", "aaaaa");
                break;
        }
    }
}
