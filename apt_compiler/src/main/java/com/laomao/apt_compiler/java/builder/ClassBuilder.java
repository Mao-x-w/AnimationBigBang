package com.laomao.apt_compiler.java.builder;

import com.laomao.apt_compiler.java.entity.ClassEntity;
import com.laomao.apt_compiler.java.utils.AptCompileUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

/**
 * User: laomao
 * Date: 2020/2/27
 * Time: 下午4:33
 */
public class ClassBuilder {

    private static final String POSTFIX="Helper";
    private ClassEntity classEntity;

    public ClassBuilder(ClassEntity activityClass) {
        this.classEntity = activityClass;
    }

    public void build(Filer filer){
        // 通过包名和类名获取其对应的ClassName类型。因为这里获取不到其对象，只能使用这种方式
        ClassName iBindHelper = ClassName.get("com.laomao.apt_api.template", "IBindHelper");

        // public final class AptDemoActivityHelper implement IBindHelper{}
        // javapoet会为我们自动导包
        TypeSpec.Builder builder = TypeSpec.classBuilder(classEntity.getSimpleName() + POSTFIX)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(iBindHelper);

        new InjectMethodBuilder(classEntity).build(builder);

        writeToFile(filer,builder.build());
    }

    private void writeToFile(Filer filer, TypeSpec typeSpec) {
        try {
            JavaFile.builder(classEntity.getPackageName(),typeSpec).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
