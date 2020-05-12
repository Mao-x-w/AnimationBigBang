package com.laomao.apt_compiler.kotlin.activity

import com.laomao.apt_compiler.kotlin.activity.method.InjectMethodBuilder
import com.laomao.apt_compiler.kotlin.activity.method.StartMethodBuilder
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.Filer
import javax.lang.model.element.Modifier

/**
 * User: laomao
 * Date: 2020/2/25
 * Time: 上午10:25
 */
class ActivityClassBuilder(val activityClass: ActivityClass) {

    companion object {
        const val POSTFIX="Builder"
        const val METHOD_NAME="start"
        const val METHOD_NAME_NO_OPTIONAL= METHOD_NAME+"WithoutOptional"
        const val METHOD_NAME_FOR_OPTIONAL= METHOD_NAME+"WithOptional"
        const val METHOD_NAME_FOR_OPTIONALS= METHOD_NAME+"WithOptionals"
    }

    fun build(filer:Filer){
        if (activityClass.isAbstract)
            return

        val typeBuilder=TypeSpec.classBuilder(activityClass.simpleName+POSTFIX)
                .addModifiers(Modifier.PUBLIC,Modifier.FINAL)

        ConstantBuilder(activityClass).build(typeBuilder)
        StartMethodBuilder(activityClass).build(typeBuilder)
        InjectMethodBuilder(activityClass).build(typeBuilder)

        writeJavaToFile(filer,typeBuilder.build())
    }

    private fun writeJavaToFile(filer:Filer,typeSpec: TypeSpec){
        try {
            JavaFile.builder(activityClass.packageName,typeSpec).build().writeTo(filer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}