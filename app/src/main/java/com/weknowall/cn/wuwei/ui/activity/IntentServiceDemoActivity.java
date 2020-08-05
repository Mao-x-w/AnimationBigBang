package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.weknowall.cn.wuwei.Constants;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.UploadImageService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2018-11-12
 * Time: 17-48
 */

public class IntentServiceDemoActivity extends BaseActivity {

    @BindView(R.id.container)
    LinearLayout mContainer;
    private int index=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service_demo);
        ButterKnife.bind(this);
        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(Constants.RxExtra.UPLOAD_SUCCESS)},thread = EventThread.MAIN_THREAD)
    public void onGetBusData(String path) {
        resetViewState(path);
    }

    @OnClick(R.id.start_upload)
    public void onViewClicked() {
        String path="上传image"+index++;
        addView(path);
        UploadImageService.startUploadImage(getContext(),path);
    }

    private void addView(String path) {
        TextView tv=new TextView(getContext());
        tv.setText(path);
        tv.setTag(path);
        mContainer.addView(tv);
    }


    private void resetViewState(String path) {
        TextView textView = (TextView) mContainer.findViewWithTag(path);
        textView.setText("上传完成");
    }
}
