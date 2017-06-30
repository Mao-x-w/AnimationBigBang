package com.weknowall.cn.wuwei.utils.image;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.weknowall.app_common.utils.DeviceHelper;
import com.weknowall.cn.wuwei.R;

/**
 * User: Joy
 * Date: 2016/11/9
 * Time: 11:00
 */

public class ImagePickerDialog extends BottomSheetDialog implements View.OnClickListener {

    protected TextView mImagePickerGallery;
    protected TextView mImagePickerCamera;
    protected Button mImagePickerCancel;
    private ImagePicker mImagePicker;
    private ImagePicker.ImagePickCallback listener;
    private boolean mCameraCrop=false;
    private boolean mSingleCrop;

    public ImagePickerDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_image_picker);
        initView();
        mImagePicker = ImagePicker.from(context);
    }


    public ImagePickerDialog setImagePickerListener(ImagePicker.ImagePickCallback listener) {
        this.listener = listener;
        return this;
    }

    public ImagePickerDialog setImageCount(int count) {
        mImagePicker.multi(count);
        return this;
    }

    /**
     * 当调用照相机是否裁剪 默认是不裁剪
     * @param crop
     * @return
     */
    public ImagePickerDialog isCameraCrop(boolean crop){
        mCameraCrop = crop;
        return this;
    }

    /**
     * 当只从图库中选择一张图片是否裁剪 默认是不裁剪
     * @param crop
     * @return
     */
    public ImagePickerDialog isSingleCrop(boolean crop){
        mSingleCrop = crop;
        return this;
    }

    public ImagePickerDialog cropWHScale(float scale){
        mImagePicker.cropWHScale(scale);
        return this;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.image_picker_gallery) {
            if (mSingleCrop){
                mImagePicker.single(mSingleCrop);
            }
            mImagePicker.listener(listener).picker();
        } else if (view.getId() == R.id.image_picker_camera) {
            if (!DeviceHelper.checkPermissions(getContext(), Manifest.permission.CAMERA)){
                return;
            }
            mImagePicker.camera(mCameraCrop).listener(listener).picker();
        } else if (view.getId() == R.id.image_picker_cancel) {

        }
        dismiss();
    }

    private void initView() {
        mImagePickerGallery = (TextView) findViewById(R.id.image_picker_gallery);
        mImagePickerGallery.setOnClickListener(ImagePickerDialog.this);
        mImagePickerCamera = (TextView) findViewById(R.id.image_picker_camera);
        mImagePickerCamera.setOnClickListener(ImagePickerDialog.this);
        mImagePickerCancel = (Button) findViewById(R.id.image_picker_cancel);
        mImagePickerCancel.setOnClickListener(ImagePickerDialog.this);
    }
}
