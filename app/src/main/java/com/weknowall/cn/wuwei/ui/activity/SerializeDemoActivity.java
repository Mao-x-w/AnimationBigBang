package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.weknowall.app_common.sharedPrefrence.GeneralSharePreference;
import com.weknowall.app_common.utils.JsonParser;
import com.weknowall.app_common.utils.Logs;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.model.User;
import com.weknowall.cn.wuwei.ui.BaseActivity;

import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2020/5/28
 * Time: 10:14 AM
 */
public class SerializeDemoActivity extends BaseActivity {

    @BindView(R.id.save)
    Button mSave;
    @BindView(R.id.read)
    Button mRead;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serialize_demo);
        ButterKnife.bind(this);


        // 这里使用User没问题，如果使用User2就会有问题，因为User2没有实现序列化
        HashMap<Integer, User> map = (HashMap) getIntent().getSerializableExtra("map");

        Set<Integer> integers = map.keySet();
        for (Integer integer : integers) {
            User user = map.get(integer);
            Logs.d("key:" + integer + "   value:" + user.getUserName());
        }

    }

    @OnClick({R.id.save, R.id.read})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                User user = new User();
                user.setUserName("张三");
                GeneralSharePreference.getInstance().saveValue(GeneralSharePreference.KEY_SAVA_USER, JsonParser.toJson(user));
                break;
            case R.id.read:
                String value = GeneralSharePreference.getInstance().getValue(GeneralSharePreference.KEY_SAVA_USER);
                User user1 = JsonParser.parseObject(value, User.class);
                Logs.d("用户信息：::::" + user1.getUserName());
                break;
        }
    }
}
