package com.laomao.apt_compiler

import com.bennyhuo.aptutils.AptContext
import com.bennyhuo.aptutils.logger.Logger
import com.bennyhuo.aptutils.types.isSubTypeOf
import com.laomao.apt_annotation.Builder
import com.laomao.apt_annotation.Optional
import com.laomao.apt_annotation.Required
import com.laomao.apt_compiler.kotlin.activity.ActivityClass
import com.laomao.apt_compiler.kotlin.activity.Field
import com.laomao.apt_compiler.kotlin.activity.OptionalField
import com.sun.tools.javac.code.Symbol
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
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
class BuilderProcessor : AbstractProcessor() {

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val hasSet = HashSet<String>()
        hasSet.add(Builder::class.java.canonicalName)
        hasSet.add(Required::class.java.canonicalName)
        hasSet.add(Optional::class.java.canonicalName)
        return hasSet
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.RELEASE_7
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {

        val activityClasses=HashMap<Element,ActivityClass>()

        roundEnv.getElementsAnnotatedWith(Builder::class.java)
                .filter { it.kind.isClass }
                .forEach {element: Element ->
                    try {
                        if (element.asType().isSubTypeOf("android.app.Activity")){
                            activityClasses[element]=ActivityClass(element as TypeElement)
                        }else{
                            Logger.error(element,"Unsupported typeElement: ${element.simpleName}")
                        }
                    }catch (e:Exception){
                        Logger.logParsingError(element,Builder::class.java,e)
                    }
                }

        roundEnv.getElementsAnnotatedWith(Required::class.java)
                .filter { it.kind.isField }
                .forEach { element: Element ->
                    try {
                        activityClasses[element.enclosingElement]?.fields?.add(Field(element as Symbol.VarSymbol))
                        ?:Logger.error(element,"Field $element annotated as Required while ${element.enclosingElement} not annotated")
                    }catch (e:Exception){
                        Logger.logParsingError(element,Required::class.java,e)
                    }
                }

        roundEnv.getElementsAnnotatedWith(Optional::class.java)
                .filter { it.kind.isField }
                .forEach { element: Element ->
                    try {
                        activityClasses[element.enclosingElement]?.fields?.add(OptionalField(element as Symbol.VarSymbol))
                                ?:Logger.error(element,"Field $element annotated as Required while ${element.enclosingElement} not annotated")
                    }catch (e:Exception){
                        Logger.logParsingError(element,Required::class.java,e)
                    }
                }

        activityClasses.values.forEach {activityClass: ActivityClass ->
            activityClass.activityClassBuilder.build(AptContext.filer)
        }

        return true
    }
}