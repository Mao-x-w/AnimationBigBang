package com.weknowall.cn.wuwei.ui.activity;

import android.annotation.TargetApi;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.ui.webview.SonicJavaScriptInterface;
import com.weknowall.cn.wuwei.ui.webview.SonicRuntimeImpl;
import com.weknowall.cn.wuwei.ui.webview.SonicSessionClientImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * User: laomao
 * Date: 2017-04-07
 * Time: 11-34
 */

public class SonicWebViewActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView mWebview;

    private SonicSession sonicSession;

//    private String url = "http://shopapi.meishi.cc/mobile/index.php?act=index&op=recommend";
    private String url = "https://tapph5.meishi.cc/king_of_food/food_king.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        initWebView();
    }

    private void initWebView() {
        // step 1: Initialize sonic engine if necessary, or maybe u can do this when application created
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }

        SonicSessionClientImpl sonicSessionClient = null;

        // step 2: Create SonicSession
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sessionConfigBuilder.setSupportLocalServer(true);

        // create sonic session and run sonic flow
        sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
        if (null != sonicSession) {
            sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
        } else {
            // this only happen when a same sonic session is already running,
            // u can comment following codes to feedback as a default mode.
            // throw new UnknownError("create session fail!");
            Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
        }



        // step 3: BindWebView for sessionClient and bindClient for SonicSession
        // in the real world, the init flow may cost a long time as startup
        // runtime、init configs....
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    //step 6: Call sessionClient.requestResource when host allow the application
                    // to return the local data .
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }
        });

        WebSettings webSettings = mWebview.getSettings();
        // step 4: bind javascript
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webSettings.setJavaScriptEnabled(true);
        mWebview.removeJavascriptInterface("searchBoxJavaBridge_");
        getIntent().putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        mWebview.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, getIntent()), "sonic");

        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


        // step 5: webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(mWebview);
            sonicSessionClient.clientReady();
        } else { // default mode
            mWebview.loadUrl(url);
        }
    }
}
