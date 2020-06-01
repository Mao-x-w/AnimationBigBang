package com.weknowall.cn.wuwei.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.widget.dialog.MessageDialog;
import com.weknowall.cn.wuwei.widget.dialog.MessageDialog1;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2020/5/26
 * Time: 2:00 PM
 */
public class BuilderDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.dialog)
    public void onClick() {
//        new MessageDialog1(getContext()).setMessage("hahahhah").show();
        new MessageDialog.Builder(getContext())
                .setTitle("hahah")
                .setMessage("1111111")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
