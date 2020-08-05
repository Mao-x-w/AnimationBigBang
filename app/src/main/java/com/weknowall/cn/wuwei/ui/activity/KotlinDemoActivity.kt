package com.weknowall.cn.wuwei.ui.activity

import android.os.Bundle
import android.widget.TextView
import com.laomao.apt_annotation.Builder
import com.laomao.apt_annotation.Optional
import com.laomao.apt_annotation.Required
import com.weknowall.cn.wuwei.R
import com.weknowall.cn.wuwei.ui.BaseActivity
import com.weknowall.cn.wuwei.utils.Logs

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

//        findViewById<TextView>(R.id.id).setText(id)
//        findViewById<TextView>(R.id.age).setText("年龄：$age")
//        findViewById<TextView>(R.id.user_name).setText(userName)

        val result1 = operateNum(1, 2) { a, b ->
            a + b
        }
        val result2 = operateNum(1, 2, ::minus)

        Logs.d("计算结果result1:::$result1")
        Logs.d("计算结果result2:::$result2")
    }

    fun add(a: Int, b: Int): Int {
        return a + b;
    }

    fun minus(a: Int, b: Int): Int {
        return a - b;
    }

    fun operateNum(a: Int, b: Int, opetate: (a: Int, b: Int) -> Int): Int {
        return opetate(a, b);
    }

}
