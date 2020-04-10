package com.weknowall.cn.wuwei.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * User: laomao
 * Date: 2019-03-25
 * Time: 17-51
 */
public class PermissionsUtils {

    // 查看权限是否已申请
    public static boolean isAppliedPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
    }

    private static String[] getFirstLaunchPermission(Context context, boolean isFirst) {
        // 创建一个权限列表，把需要使用而没用授权的的权限存放在这里
        List<String> permissionList = new ArrayList<>();

        // 判断权限是否已经授予，没有就把该权限添加到列表中
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED&&isFirst) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED&&isFirst) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        return permissionList.toArray(new String[permissionList.size()]);
    }

    private static String[] getOtherLaunchPermission(Activity context, boolean isFirst) {
        // 创建一个权限列表，把需要使用而没用授权的的权限存放在这里
        List<String> permissionList = new ArrayList<>();

        // 判断权限是否已经授予，没有就把该权限添加到列表中
        if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_PHONE_STATE)) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)&&isFirst) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_COARSE_LOCATION)&&isFirst) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        return permissionList.toArray(new String[permissionList.size()]);
    }

    /**
     * 获取所有的不再询问的权限
     * @param context
     * @return
     */
    public static String[] getAllLaunchPermissionNeverAsk(Activity context) {
        // 创建一个权限列表，把需要使用而没用授权的的权限存放在这里
        List<String> permissionList = new ArrayList<>();

        // 判断权限是否已经授予，没有就把该权限添加到列表中
        if (ifPermissionNeverAsk(context,Manifest.permission.READ_PHONE_STATE)) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ifPermissionNeverAsk(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ifPermissionNeverAsk(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (ifPermissionNeverAsk(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ifPermissionNeverAsk(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        return permissionList.toArray(new String[permissionList.size()]);
    }

    /**
     * 获取部分必须的不再询问的权限
     * @param context
     * @return
     */
    public static String[] getMustLaunchPermissionNeverAsk(Activity context) {
        // 创建一个权限列表，把需要使用而没用授权的的权限存放在这里
        List<String> permissionList = new ArrayList<>();

        // 判断权限是否已经授予，没有就把该权限添加到列表中
        if (ifPermissionNeverAsk(context,Manifest.permission.READ_PHONE_STATE)) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ifPermissionNeverAsk(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ifPermissionNeverAsk(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        return permissionList.toArray(new String[permissionList.size()]);
    }


    /**
     * 这个是权限没被允许且不再询问
     * @param context
     * @param permission
     * @return
     */
    private static boolean ifPermissionNeverAsk(Activity context, String permission){
        return !ActivityCompat.shouldShowRequestPermissionRationale(context, permission)&&ActivityCompat.checkSelfPermission(context,permission)== PackageManager.PERMISSION_DENIED;
    }

    /**
     * 跳转到对应应用的设置界面
     * @param context
     * @param packageName
     */
    public static void openAppInfo(Context context, String packageName) {
        try
        {
            //Open the specific App Info page:
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            context.startActivity(intent);
        }
        catch ( ActivityNotFoundException e )
        {
            //Open the generic Apps page:
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            context.startActivity(intent);
        }
    }

}
