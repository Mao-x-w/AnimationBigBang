package com.weknowall.cn.wuwei.ui.activity

import android.os.Bundle
import com.laomao.apt_annotation.Builder
import com.weknowall.cn.wuwei.R
import com.weknowall.cn.wuwei.ui.BaseActivity

@Builder
class KotlinDemoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo)

    }
}
