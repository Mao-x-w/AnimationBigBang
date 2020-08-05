package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.ui.helper.MyWebViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2017-04-07
 * Time: 11-34
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView mWebview;

    private MyWebViewHelper helper;
    //    private String url = "http://shopapi.meishi.cc/mobile/index.php?act=index&op=recommend";
    private String url = "https://tapph5.meishi.cc/king_of_food/food_king.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        helper = new MyWebViewHelper(getContext(), mWebview);
        helper.initWeb();
        helper.loadurl(url);

        mWebview.postDelayed(new Runnable() {
            @Override
            public void run() {
                helper = new MyWebViewHelper(getContext(), mWebview);
                helper.initWeb();
                helper.loadurl(url);
            }
        }, 2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview != null && mWebview.canGoBack()) {
            goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回
     */
    private void goBack() {
        // TODO Auto-generated method stub
        if (mWebview != null && mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            Toast.makeText(this, "不能再后退了", 0).show();
        }
    }
}
