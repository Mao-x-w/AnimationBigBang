package com.weknowall.cn.wuwei.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weknowall.app_common.utils.DeviceHelper;
import com.weknowall.cn.wuwei.ui.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.functions.Consumer;

/**
 * User: laomao
 * Date: 2020/6/10
 * Time: 11:05 AM
 */
public class AndroidQDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testAllExternalStorage();
        showToast("mac值："+DeviceHelper.getMac(getContext()));
    }

    private void testAllExternalStorage() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            save2File("1111111111111111");
                        }
                    }
                });
    }

    private File save2File(String crashReport) {

        String fileName = "crash-" + System.currentTimeMillis() + ".txt";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(getRootDir() + "crash");
                if (!dir.exists())
                    dir.mkdir();
                File file = new File(dir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(crashReport.toString().getBytes());
                fos.close();
                return file;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getRootDir() {
        String rootDir = Environment.getExternalStorageDirectory() + "/mvp/";

        File f = new File(rootDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        return rootDir;
    }
}
