package com.laomao.apt_compiler.kotlin.activity

import kotlin.test.Test

/**
 * User: laomao
 * Date: 2020/2/22
 * Time: 下午2:56
 */
class AAA() {


    @Test
    fun getAge() {
        var username = "李"
        when (username) {
            "张三", "李四" -> print(27)
            "王五" -> {
                print(28)
            }
            else -> print(100)
        }

        when {
            username.startsWith("李") -> print(27)
            username.length==1-> print(28)
            else -> print(100)
        }
    }

}