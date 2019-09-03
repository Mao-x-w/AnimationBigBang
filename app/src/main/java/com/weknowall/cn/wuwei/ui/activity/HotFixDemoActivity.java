package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.jni.JniUtils;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.calculate.Calculater;
import com.weknowall.cn.wuwei.utils.hotFix.HotFixManager;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotFixDemoActivity extends BaseActivity {

    @BindView(R.id.calculate_result)
    TextView mCalculateResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_fix_demo);
        ButterKnife.bind(this);

        mCalculateResult.setText(JniUtils.getString());
    }

    @OnClick({R.id.calculate, R.id.repaire})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calculate:
//                Calculater calculater=new Calculater();
//                mCalculateResult.setText(calculater.calculate()+"");
                break;
            case R.id.repaire:
                HotFixManager manager=new HotFixManager(getContext());
                manager.fix(getDexFilePath());
                break;
        }
    }

    private File getDexFilePath(){
        return new File(Environment.getExternalStorageDirectory()+"/out.dex");
    }
}
