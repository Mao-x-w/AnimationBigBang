package com.weknowall.cn.wuwei.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.weknowall.app_domain.entity.general.Temp;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2019-11-18
 * Time: 10-06
 */
public class UrlJumpActivity extends BaseActivity {

    @BindView(R.id.edit_text)
    EditText mEditText;
    @BindView(R.id.jump)
    Button mJump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_jump);
        ButterKnife.bind(this);
//
//        String trim = mEditText.getText().toString().trim();
//        try {
//            String encode = URLEncoder.encode(trim, "utf-8");
//            mEditText.setText("http://calendar.miui.com/event/insert?title=测试&startTimeMillis=1462852800000&endTimeMillis=1462867200000&url="+encode+"&urlText=美食杰");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @OnClick(R.id.jump)
    public void onClick() {
        Temp temp=new Temp();
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(mEditText.getText().toString().trim()));
        intent.putExtra("sdfsdf",temp);
        startActivity(intent);
    }
}
