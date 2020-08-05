package com.weknowall.cn.wuwei.widget.dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

import com.weknowall.cn.wuwei.R;


/**
 * User: Joy
 * Date: 2016/10/21
 * Time: 20:17
 */

public class MessageDialog extends AlertDialog {

	protected MessageDialog(@NonNull Context context) {
		super(context);
	}

	protected MessageDialog(@NonNull Context context, @StyleRes int themeResId) {
		super(context, themeResId);
	}

	public static class Builder extends AlertDialog.Builder {

		public Builder(@NonNull Context context) {
			super(context, R.style.MeiShi_Widget_Dialog_Message);
		}

		public Builder(@NonNull Context context, @StyleRes int themeResId) {
			super(context, themeResId);
		}


	}
}
