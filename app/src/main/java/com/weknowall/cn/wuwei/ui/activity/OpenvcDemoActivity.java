package com.weknowall.cn.wuwei.ui.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.Logs;
import com.weknowall.cn.wuwei.utils.image.ImageLoader;
import com.weknowall.cn.wuwei.utils.image.ImagePicker;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

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
    private String mPathname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openvc_demo);
        ButterKnife.bind(this);

        iniLoadOpenCV();
    }

    private void iniLoadOpenCV() {
        boolean success = OpenCVLoader.initDebug();
        if(success) {
            Logs.d( "OpenCV Libraries loaded...");
        } else {
            Toast.makeText(this.getApplicationContext(), "WARNING: Could not load OpenCV Libraries!", Toast.LENGTH_LONG).show();
        }
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
                                mPathname = images.get(0);
                                ImageLoader.display(getContext(),new File(mPathname),mSrcImg);
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
                if (TextUtils.isEmpty(mPathname))
                    return;
                convert2Gray();
                break;
        }
    }

    private void convert2Gray() {
        Mat src = Imgcodecs.imread(mPathname);
        if(src.empty()) {
            return;
        }
        Mat dst = new Mat();

        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGR2GRAY);
        Bitmap bitmap = grayMat2Bitmap(dst);
        mGreyImg.setImageBitmap(bitmap);
        src.release();
        dst.release();
    }

    private Bitmap grayMat2Bitmap(Mat result) {
        Mat image = null;
        if(result.cols() > 1000 || result.rows() > 1000) {
            image = new Mat();
            Imgproc.resize(result, image, new Size(result.cols() / 4, result.rows() / 4));
        } else {
            image = result;
        }
        Bitmap bitmap = Bitmap.createBitmap(image.cols(),image.rows(), Bitmap.Config.ARGB_8888);
        Imgproc.cvtColor(image, image, Imgproc.COLOR_GRAY2RGBA);
        Utils.matToBitmap(image, bitmap);
        image.release();
        return bitmap;
    }

    static {
        System.loadLibrary("opencv_java3");
    }
}
