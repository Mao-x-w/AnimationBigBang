package com.laomao.apt_compiler.kotlin.activity.method

import com.laomao.apt_compiler.kotlin.activity.ActivityClass
import com.laomao.apt_compiler.kotlin.activity.ActivityClassBuilder
import com.laomao.apt_compiler.kotlin.activity.Field
import com.laomao.apt_compiler.kotlin.activity.OptionalField
import com.squareup.javapoet.TypeSpec

/**
 * User: laomao
 * Date: 2020/2/25
 * Time: 下午6:12
 */
class StartMethodBuilder(val activityClass: ActivityClass) {

    fun build(typeBuilder: TypeSpec.Builder) {
        val startMethod = StartMethod(activityClass, ActivityClassBuilder.METHOD_NAME)

        val groupedFields = activityClass.fields.groupBy { it is OptionalField }

        var requiredFields = groupedFields[false] ?: emptyList()
        var optinalFields = groupedFields[true] ?: emptyList()

        startMethod.addAllFields(requiredFields)

        // 这个调用顺序不能乱，因为它只拷贝requiredFields
        val startMethodNoOptional = startMethod.copy(ActivityClassBuilder.METHOD_NAME_NO_OPTIONAL)

        // 构建start方法
        startMethod.addAllFields(optinalFields)
        startMethod.build(typeBuilder)

        // 如果optinalFields不为空，那么说明有optional的field，这个时候就需要构建不带optional的启动方法
        if (optinalFields.isNotEmpty()) {
            startMethodNoOptional.build(typeBuilder)
        }


        // 如果optinalFields小于3个的话，就每个都生成一个start方法，否则使用builder模式
        if (optinalFields.size < 3) {
            optinalFields.forEach { field: Field ->
                startMethodNoOptional.copy(ActivityClassBuilder.METHOD_NAME_FOR_OPTIONAL + field.name.capitalize())
                        .also { it.addField(field) }
                        .build(typeBuilder)
            }
        } else{
            //  https://www.bilibili.com/video/av32905508?p=5
        }
    }
}