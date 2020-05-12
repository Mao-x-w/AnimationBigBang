package com.laomao.apt_compiler.kotlin.activity

import com.laomao.apt_compiler.kotlin.utils.camelToUnderline
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/**
 * User: laomao
 * Date: 2020/2/25
 * Time: 上午10:57
 */
class ConstantBuilder(val activityClass: ActivityClass) {

    // public static final REQUIRED_NAME="name"
    fun build(specBuilder:TypeSpec.Builder) {
        activityClass.fields.forEach { field ->
            specBuilder.addField(FieldSpec.builder(String::class.java,field.prefix+field.name.camelToUnderline().toUpperCase(),Modifier.PUBLIC,Modifier.STATIC,Modifier.FINAL)
                    .initializer("\$S",field.name).build()
            )
        }
    }

}