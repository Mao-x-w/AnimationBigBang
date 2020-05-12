package com.weknowall.cn.wuwei.ui.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.Logs;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2020/5/7
 * Time: 4:59 PM
 */
public class BigImageActivity extends BaseActivity {

    @BindView(R.id.img)
    ImageView mImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_img);
        ButterKnife.bind(this);

        long maxMemory = Runtime.getRuntime().maxMemory() / 1024;
        Logs.d("应用的可用最大内存：：："+maxMemory+"kb");

        loadBigImg();
        Logs.d("==================");
        loadSmallImg();
    }

    private void loadBigImg() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.big_img_test);
        Logs.d("图片宽高：：：："+bitmap.getWidth()+"*"+bitmap.getHeight());
        Logs.d("图片占用内存大小：：：："+bitmap.getAllocationByteCount()/1024+"kb");
        mImg.setImageBitmap(bitmap);
    }

    private void loadSmallImg() {
        Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), R.drawable.big_img_test, 200, 200);

        Logs.d("图片宽高：：：："+bitmap.getWidth()+"*"+bitmap.getHeight());
        Logs.d("图片占用内存大小：：：："+bitmap.getAllocationByteCount()/1024+"kb");
        mImg.setImageBitmap(bitmap);
    }

    private Bitmap decodeSampledBitmapFromResource(Resources resources, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;

        BitmapFactory.decodeResource(resources,resId,options);

        // 这里的inSampleSize是4的话，就是宽高变为原来1/4
        options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds=false;

        return BitmapFactory.decodeResource(resources, resId, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int inSampleSize=1;

        if (outWidth>reqWidth|| outHeight>reqHeight){
            int widthRatio = Math.round((float) outWidth / (float) reqWidth);
            int heightRatio = Math.round((float) outHeight / (float) reqHeight);
            inSampleSize=widthRatio<heightRatio?widthRatio:heightRatio;
        }
        return inSampleSize;
    }
}
