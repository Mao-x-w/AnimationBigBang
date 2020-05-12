package com.laomao.apt_compiler.java.builder;

import com.laomao.apt_compiler.java.entity.ClassEntity;
import com.laomao.apt_compiler.java.entity.FieldEntity;
import com.laomao.apt_compiler.java.utils.AptCompileUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * User: laomao
 * Date: 2020/2/27
 * Time: 下午8:44
 */
public class InjectMethodBuilder {

    private ClassEntity classEntity;

    public InjectMethodBuilder(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    /**
     * public void inject(Object obj) {
     *     AptDemoActivity instance=(AptDemoActivity)obj;
     *     instance.mTitle=instance.findViewById(2131361834);
     * }
     * @param builder
     */
    public void build(TypeSpec.Builder builder) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(Object.class, "obj")
                .addStatement("$T instance=($T)obj", classEntity.typeElement, classEntity.typeElement);

        for (FieldEntity fieldEntity : classEntity.getFields()) {
            methodBuilder.addStatement("instance.$L=instance.findViewById($L)",fieldEntity.getName(),fieldEntity.getAnnoValue());
        }

        builder.addMethod(methodBuilder.build());
    }
}
