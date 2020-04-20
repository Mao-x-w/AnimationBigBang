package com.weknowall.cn.wuwei.ui.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
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
    @BindView(R.id.page_width)
    EditText mPageWidth;
    @BindView(R.id.page_height)
    EditText mPageHeight;

    private String mPathname;
    private int mWidth;
    private int mHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openvc_demo);
        ButterKnife.bind(this);

        iniLoadOpenCV();
    }

    private void getData() {
        mWidth = Integer.valueOf(mPageWidth.getText().toString().trim());
        mHeight = Integer.valueOf(mPageHeight.getText().toString().trim());
    }

    private void iniLoadOpenCV() {
        boolean success = OpenCVLoader.initDebug();
        if (success) {
            Logs.d("OpenCV Libraries loaded...");
        } else {
            Toast.makeText(this.getApplicationContext(), "WARNING: Could not load OpenCV Libraries!", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick({R.id.pick_img, R.id.grey_process, R.id.contours_detect})
    public void onClick(View view) {
        getData();
        switch (view.getId()) {
            case R.id.pick_img:
                //自定义方法的单选
                ImagePicker.from(getContext())
                        .single(true)
                        .cropWHScale((float) (mWidth*1.0/mHeight*1.0))
                        .listener(new ImagePicker.ImagePickCallback() {
                            @Override
                            public void onPicked(List<String> images) {
                                mPathname = images.get(0);
                                ImageLoader.display(getContext(), new File(mPathname), mSrcImg);
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
            case R.id.contours_detect:
                // read image
                Mat src = Imgcodecs.imread(mPathname);
                if (src.empty()) {
                    return;
                }
                Mat dst = new Mat();

                measureContours(src, dst);

                // 转换为Bitmap，显示
                Bitmap bm = Bitmap.createBitmap(dst.cols(), dst.rows(), Bitmap.Config.ARGB_8888);
                Mat result = new Mat();
                Imgproc.cvtColor(dst, result, Imgproc.COLOR_BGR2RGBA);
                Utils.matToBitmap(result, bm);

                // show
                mGreyImg.setImageBitmap(bm);

                // release memory
                src.release();
                dst.release();
                result.release();
                break;
        }
    }

    private void convert2Gray() {
        Mat src = Imgcodecs.imread(mPathname);
        if (src.empty()) {
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
        if (result.cols() > 1000 || result.rows() > 1000) {
            image = new Mat();
            Imgproc.resize(result, image, new Size(result.cols() / 4, result.rows() / 4));
        } else {
            image = result;
        }
        Bitmap bitmap = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888);
        Imgproc.cvtColor(image, image, Imgproc.COLOR_GRAY2RGBA);
        Utils.matToBitmap(image, bitmap);
        image.release();
        return bitmap;
    }

    private void measureContours(Mat src, Mat dst) {
        Mat gray = new Mat();
        Mat binary = new Mat();

        // 二值
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY_INV | Imgproc.THRESH_OTSU);

        // 轮廓发现
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        // 测量轮廓
        dst.create(src.size(), src.type());
        for (int i = 0; i < contours.size(); i++) {
            Rect rect = Imgproc.boundingRect(contours.get(i));
            double w = rect.width;
            double h = rect.height;
            double rate = Math.min(w, h) / Math.max(w, h);
            Log.i("Bound Rect", "rate : " + rate);
            RotatedRect minRect = Imgproc.minAreaRect(new MatOfPoint2f(contours.get(i).toArray()));
            w = minRect.size.width;
            h = minRect.size.height;
            rate = Math.min(w, h) / Math.max(w, h);
            Log.i("Min Bound Rect", "rate : " + rate);

            double area = Imgproc.contourArea(contours.get(i), false);
            double arclen = Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()), true);
            Log.i("contourArea", "area : " + area);
            Log.i("arcLength", "arcLength : " + arclen);
            Imgproc.drawContours(dst, contours, i, new Scalar(0, 0, 255), 1);
        }

        // 释放内存
        gray.release();
        binary.release();
    }

    private void findContoursDemo(Mat src, Mat dst) {
        Mat gray = new Mat();
        Mat binary = new Mat();

        // 二值
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(gray, binary, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

        // 轮廓发现
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));

        // 绘制轮廓
        dst.create(src.size(), src.type());
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(dst, contours, i, new Scalar(0, 0, 255), 2);
        }

        // 释放内存
        gray.release();
        binary.release();
    }

    static {
        System.loadLibrary("opencv_java3");
    }
}
