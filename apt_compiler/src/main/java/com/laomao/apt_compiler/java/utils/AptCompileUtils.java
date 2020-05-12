package com.laomao.apt_compiler.java.utils;

import com.bennyhuo.aptutils.AptContext;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * User: laomao
 * Date: 2020/2/27
 * Time: 下午3:48
 */
public class AptCompileUtils {

    /**
     * 获取包名
     * @param typeElement
     * @return
     */
    public static String getPackageName(TypeElement typeElement){
        if (typeElement.getEnclosingElement().getKind()==ElementKind.PACKAGE){
            return typeElement.getEnclosingElement().asType().toString();
        }else {
            throw new IllegalArgumentException(typeElement.getEnclosedElements().toString());
        }
    }
}
