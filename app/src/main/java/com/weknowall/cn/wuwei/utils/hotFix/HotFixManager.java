package com.weknowall.cn.wuwei.utils.hotFix;

import android.content.Context;

import com.example.jni.JniUtils;
import com.weknowall.cn.wuwei.utils.Logs;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import dalvik.system.DexFile;

public class HotFixManager {
    private Context context;

    public HotFixManager(Context context) {
        this.context = context;
    }

    public void fix(File dexFilePath){
        File outFilePath=new File(context.getCacheDir(),dexFilePath.getName());
        if (outFilePath.exists())
            outFilePath.delete();

        try {
            DexFile dexFile = DexFile.loadDex(dexFilePath.getAbsolutePath(), outFilePath.getAbsolutePath(), Context.MODE_PRIVATE);
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()){
                String className = entries.nextElement();
                Class realClass = dexFile.loadClass(className, context.getClassLoader());
                Logs.d("找到了正确的类："+realClass.getName());
                fixClass(realClass);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fixClass(Class realClass) {
        Method[] methods = realClass.getMethods();
        for (Method method : methods) {
            Replace replace = method.getAnnotation(Replace.class);

            if (replace==null)
                continue;

            String wrongClassName = replace.clasName();
            String wrongMethodName = replace.methodName();

            try {
                Class wrongClass = Class.forName(wrongClassName);
                Method wrongMethod = wrongClass.getMethod(wrongMethodName,method.getParameterTypes());

                JniUtils.replace(wrongMethod,method);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

}
