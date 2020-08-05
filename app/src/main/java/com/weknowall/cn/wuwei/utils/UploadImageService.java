package com.weknowall.cn.wuwei.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.hwangjr.rxbus.RxBus;
import com.weknowall.cn.wuwei.Constants;

/**
 * User: laomao
 * Date: 2018-11-12
 * Time: 17-38
 */

public class UploadImageService extends IntentService{

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public UploadImageService() {
        super("UploadImageService");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public static void startUploadImage(Context context, String path){
        Intent intent=new Intent(context,UploadImageService.class);
        intent.putExtra("path",path);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null){
            String path = intent.getStringExtra("path");
            startUpload(path);
            Logs.e("onHandleIntent....."+path);
        }
    }

    private void startUpload(String path) {
        try {
            Thread.sleep(3000);
            RxBus.get().post(Constants.RxExtra.UPLOAD_SUCCESS, path);
            Logs.e("rxbus调用了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
