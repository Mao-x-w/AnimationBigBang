package com.laomao.apt_compiler.kotlin.utils

import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

/**
 * User: laomao
 * Date: 2020/2/21
 * Time: 上午11:22
 */

/**
 * TypeElement的扩展方法，返回类名的String形式
 */

var AAA="3A" // 可以在其他地方直接使用

/**
 * TypeElement扩展方法，获取类名
 */
fun TypeElement.simpleName()=this.simpleName.toString()

/**
 * TypeElement扩展方法，获取包名
 */
fun TypeElement.packageName():String{
    if (this.enclosingElement.kind==ElementKind.PACKAGE){
        return this.enclosingElement.asType().toString()
    }else{
        throw IllegalArgumentException(this.enclosingElement.toString())
    }
}

/**
 * String的扩展方法，驼峰转换为下划线
 */
fun String.camelToUnderline() :String{
    return this.fold(StringBuilder()){acc, c ->
        if (c.isUpperCase()){
            acc.append("_").append(c.toLowerCase())
        }else{
            acc.append(c)
        }
    }.toString()
}

/**
 *
 */
fun String.underlineToCamel():String{
    var upperNext=false
    return this.fold(StringBuilder()){acc, c ->
        if (c=='_'){
            upperNext=true
        }else{
            acc.append(if (upperNext) c.toUpperCase() else c)
        }
        acc
    }.toString()
}