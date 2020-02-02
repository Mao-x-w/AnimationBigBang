package com.laomao.apt_compiler

import com.bennyhuo.aptutils.AptContext
import com.bennyhuo.aptutils.logger.Logger
import com.laomao.apt_annotation.Builder
import com.laomao.apt_annotation.Optional
import com.laomao.apt_annotation.Required
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * User: laomao
 * Date: 2020-01-20
 * Time: 16-16
 *
 */

/**
 * 使用AutoService这个注解就不用创建Resources文件夹，在META-INFO中。。。。
 * 但是kotlin要想使用得
 * apply plugin: 'kotlin'
 * apply plugin: 'kotlin-kapt'
 *
 * kapt 'com.google.auto.service:auto-service:1.0-rc4'
 * implementation'com.google.auto.service:auto-service:1.0-rc4'
 */
class BuilderProcessor: AbstractProcessor() {

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val hasSet=HashSet<String>()
        hasSet.add(Builder::class.java.canonicalName)
        hasSet.add(Required::class.java.canonicalName)
        hasSet.add(Optional::class.java.canonicalName)
        return hasSet
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_7
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWith(Builder::class.java).forEach {
            Logger.warn(it,"到此一游："+it.simpleName.toString())
        }
        Logger.warn("haha","到此一游：")
        return true
    }
}