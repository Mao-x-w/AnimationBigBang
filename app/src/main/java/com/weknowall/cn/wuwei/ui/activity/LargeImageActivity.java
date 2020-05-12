package com.weknowall.cn.wuwei.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.widget.LargeImageView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2020/5/8
 * Time: 8:44 PM
 */
public class LargeImageActivity extends BaseActivity {

    @BindView(R.id.large_img)
    ImageView mLargeImg;
    @BindView(R.id.large_img_view)
    LargeImageView mLargeImgView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_img);
        ButterKnife.bind(this);

        try {
            InputStream inputStream = getAssets().open("large_img.jpg");

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeStream(inputStream,null,options);
            int outWidth = options.outWidth;
            int outHeight = options.outHeight;

            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options1=new BitmapFactory.Options();
            options1.inPreferredConfig= Bitmap.Config.RGB_565;

            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(new Rect(outWidth / 2 - 200, outHeight / 2 - 200, outWidth / 2 + 200, outHeight / 2 + 200), options1);
            mLargeImg.setImageBitmap(bitmap);

            mLargeImgView.setInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
