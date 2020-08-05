package com.weknowall.cn.wuwei.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weknowall.cn.wuwei.R;


/**
 * 自定义的Dialog
 * User: laomao
 * Date: 2017-06-29
 * Time: 10-18
 */

public class MessageDialogV2 extends Dialog {

    private TextView yes;//确定按钮
    private TextView no;//取消按钮
    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本
    private LinearLayout buttonRoot;// 按钮对应的那一行

    private String mTitle;
    private String mMessage;
    private String mYesText;
    private OnClickListener mYesListener;
    private String mNoText;
    private OnClickListener mNoListener;
    private boolean mIsSingle = false; // 是否是一个按钮
    private boolean mIsShowButton=true; // 是否显示按钮，默认是显示的
    private boolean mCanCancle=true;

    public MessageDialogV2(@NonNull Context context) {
        super(context,R.style.CustomDialog);
    }

    public MessageDialogV2(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_message_notify);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        initView();
        initEvent();
        refreshView();
    }

    private void initView() {
        yes = (TextView) findViewById(R.id.ensure);
        no = (TextView) findViewById(R.id.cancle);
        titleTv = (TextView) findViewById(R.id.title);
        messageTv = (TextView) findViewById(R.id.message);
        buttonRoot = (LinearLayout) findViewById(R.id.button_root);
    }


    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mYesListener != null) {
                    mYesListener.onClick(MessageDialogV2.this, 0);
                }
                dismiss();
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNoListener != null) {
                    mNoListener.onClick(MessageDialogV2.this, 0);
                }
                dismiss();
            }
        });
    }

    public MessageDialogV2 setPositiveButton(String yesText, OnClickListener listener) {
        mYesText = yesText;
        mYesListener = listener;
        return this;
    }

    public MessageDialogV2 setNegativeButton(String noText, OnClickListener listener) {
        mNoText = noText;
        mNoListener = listener;
        return this;
    }

    public MessageDialogV2 setTitle(String title) {
        mTitle = title;
        return this;
    }

    public MessageDialogV2 setMessage(String message) {
        mMessage = message;
        return this;
    }

    public MessageDialogV2 isSingle(boolean isSingle) {
        mIsSingle = isSingle;
        return this;
    }

    public MessageDialogV2 isShowButton(boolean isShowButton){
        mIsShowButton=isShowButton;
        return this;
    }

    /**
     * 设置是否可以按返回键取消对话框
     * @param canCancle
     * @return
     */
    public MessageDialogV2 setCanCancel(boolean canCancle) {
        mCanCancle = canCancle;
        return this;
    }


    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {
        //如果用户自定了title和message
        if (!TextUtils.isEmpty(mTitle)) {
            titleTv.setText(mTitle);
            titleTv.setVisibility(View.VISIBLE);
        } else {
            titleTv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mMessage)) {
            messageTv.setText(mMessage);
        }

        //如果设置按钮的文字
        if (!TextUtils.isEmpty(mYesText)) {
            yes.setText(mYesText);
        } else {
            yes.setText("确定");
        }
        if (!TextUtils.isEmpty(mNoText)) {
            no.setText(mNoText);
        } else {
            no.setText("取消");
        }

        /**
         * 只显示一个按钮的时候隐藏取消按钮，回掉只执行确定的事件
         */
        if (mIsSingle) {
            no.setVisibility(View.GONE);
        } else {
            no.setVisibility(View.VISIBLE);
        }

        buttonRoot.setVisibility(mIsShowButton?View.VISIBLE:View.GONE);

        setCancelable(mCanCancle);
    }

    @Override
    public void show() {
        super.show();
    }

    public class Builder{

        private Context mContext;

        public Builder(Context context) {
            mContext = context;
        }

//        public void build(){
//            new MessageDialogV2(mContext,this);
//        }
    }
}
