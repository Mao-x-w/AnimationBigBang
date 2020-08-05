package com.weknowall.mao.growthdemo;

import android.app.Instrumentation;
import android.graphics.Point;
import android.os.RemoteException;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * User: laomao
 * Date: 2019-04-19
 * Time: 11-14
 */
@RunWith(AndroidJUnit4.class)
public class FirstTest {

    private Instrumentation mInstrumentation;
    private UiDevice mUiDevice;

    /**
     * 保证它在测试代码之前运行
     */
    @Before
    public void setUp(){
        mInstrumentation = InstrumentationRegistry.getInstrumentation();
        mUiDevice = UiDevice.getInstance(mInstrumentation);
    }

    @Test
    public void demoTest() throws RemoteException, InterruptedException {
        mUiDevice.pressKeyCode(KeyEvent.KEYCODE_HOME);
        mUiDevice.pressKeyCode(KeyEvent.KEYCODE_HOME);

        Thread.sleep(500);

        mUiDevice.click(550,1600);
        Thread.sleep(500);
        mUiDevice.click(900,400);
        Thread.sleep(500);

        mUiDevice.findObject(By.res("com.android.calculator2:id/pad_advanced")).drag(new Point(100,800));
        Thread.sleep(1000);
        mUiDevice.findObject(By.res("com.android.calculator2:id/op_add")).click();

        mUiDevice.pressKeyCode(KeyEvent.KEYCODE_BACK);
        Thread.sleep(500);
        mUiDevice.findObject(By.res("com.android.calculator2:id/digit_4")).click();
        Thread.sleep(500);
        mUiDevice.findObject(By.res("com.android.calculator2:id/del")).longClick();

    }


}
