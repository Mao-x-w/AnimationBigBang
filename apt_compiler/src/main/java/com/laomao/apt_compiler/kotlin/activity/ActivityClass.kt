package com.laomao.apt_compiler.kotlin.activity

import com.laomao.apt_compiler.kotlin.utils.packageName
import com.laomao.apt_compiler.kotlin.utils.simpleName
import java.util.*
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

/**
 * 用于存储类信息
 * User: laomao
 * Date: 2020/2/21
 * Time: 上午11:00
 */
class ActivityClass(val typeElement: TypeElement) {

    val simpleName: String = typeElement.simpleName()
    val packageName: String = typeElement.packageName()

    val fields = TreeSet<Field>()

    val activityClassBuilder = ActivityClassBuilder(this)

    val isAbstract = typeElement.modifiers.contains(Modifier.ABSTRACT)

    val isKotlin = typeElement.getAnnotation(META_DATA) != null

    companion object {
        val META_DATA = Class.forName("kotlin.Metadata") as Class<Annotation>
    }

    override fun toString(): String {
        return "$packageName.$simpleName[${fields.joinToString()}]"
    }
}