package com.weknowall.cn.wuwei.ui.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.R.attr.fragment;

public class MyWebViewHelper {

	Context mContext;
	WebView myweb;
	String srcType;

	public MyWebViewHelper(Context mContext, WebView myweb) {
		this.mContext = mContext;
		this.myweb = myweb;

	}

	/**
	 * 初始化webVIew
	 */
	@SuppressLint("SetJavaScriptEnabled")
	public void initWeb() {
		myweb.setScrollBarStyle(0);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
		WebSettings ws = myweb.getSettings();
		String userAgent = ws.getUserAgentString() + " meishij model:android;Version:meishij"  + ";";
		ws.setUserAgentString(userAgent);
		ws.setJavaScriptEnabled(true); // 设置支持javascript脚本
		ws.setAllowFileAccess(true); // 允许访问文件
		ws.setBuiltInZoomControls(true); // 设置不显示缩放按钮
		ws.setSupportZoom(true); // 支持缩放
		ws.setUseWideViewPort(true);// 设置此属性，可任意比例缩放。
		ws.setDomStorageEnabled(true);// 支持天猫新协议html5,2015-4-30
		// 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}

		myweb.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView webView, String url) {
				if (url.startsWith("http://") || url.startsWith("https://")) {
					// webView.loadUrl(url);
					// return true;
				} else if ("about:blank".equals(url)) {// 华为荣耀3c特有
					// webView.loadUrl(url);
					// return false;
				} else {
					try {
						Uri uri = Uri.parse(url);
						Intent i = new Intent(Intent.ACTION_VIEW, uri);
						mContext.startActivity(i);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// return true;
				}
				return super.shouldOverrideUrlLoading(webView, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				// 接收网站的证书
				handler.proceed();
			}
		});
		myweb.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
				// loadingTv.setText(progress + "%");
				super.onProgressChanged(view, progress);
			}
		});
		myweb.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				Uri uri = Uri.parse(url);
				Intent i = new Intent(Intent.ACTION_VIEW, uri);
				mContext.startActivity(i);
			}
		});

		myweb.addJavascriptInterface(fragment, "androidWeb");

	}

	/**
	 * 载入链接
	 * @param url
	 */
	public void loadurl(String url) {
		if (url.contains("tel:")) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
			mContext.startActivity(intent);
		} else {
			myweb.loadUrl(url);
		}
	}


}
