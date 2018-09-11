package com.weknowall.cn.wuwei.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2018-08-29
 * Time: 15-18
 */

public class UriSchemeActivity extends BaseActivity {

    @BindView(R.id.edit_text)
    EditText mEditText;
    @BindView(R.id.button)
    Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uri_scheme);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mEditText.getText().toString().trim()));
        startActivity(intent);
    }
}
