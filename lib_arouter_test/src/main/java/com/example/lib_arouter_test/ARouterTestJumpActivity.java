package com.example.lib_arouter_test;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * User: laomao
 * Date: 2020/6/30
 * Time: 10:35 AM
 */
@Route(path = "/test/testjump")
public class ARouterTestJumpActivity extends AppCompatActivity {

    @Autowired(name = "key1")
    int key1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_arouter_jump);

        ARouter.getInstance().inject(this);

        findViewById(R.id.jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/activitya").navigation();
            }
        });

        Toast.makeText(this, key1+"啊啊啊", Toast.LENGTH_SHORT).show();
    }
}
