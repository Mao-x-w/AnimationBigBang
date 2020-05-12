package com.weknowall.cn.wuwei.ui.activity

import android.os.Bundle
import android.widget.TextView
import com.laomao.apt_annotation.Builder
import com.laomao.apt_annotation.Optional
import com.laomao.apt_annotation.Required
import com.weknowall.cn.wuwei.R
import com.weknowall.cn.wuwei.ui.BaseActivity

@Builder
class KotlinDemoActivity : BaseActivity() {

    @Required
    var id: String = ""

    @Required
    var age: Int = 10

    @Optional
    var userName: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_demo)

        findViewById<TextView>(R.id.id).setText(id)
        findViewById<TextView>(R.id.age).setText("年龄：$age")
        findViewById<TextView>(R.id.user_name).setText(userName)

    }
}
