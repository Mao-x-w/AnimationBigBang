package com.laomao.apt_compiler.java.entity;

import com.laomao.apt_compiler.java.builder.ClassBuilder;
import com.laomao.apt_compiler.java.utils.AptCompileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.lang.model.element.TypeElement;

/**
 * User: laomao
 * Date: 2020/2/27
 * Time: 下午3:37
 */
public class ClassEntity {

    private String simpleName;
    private String packageName;
    private List<FieldEntity> fields = new ArrayList<>();
    public TypeElement typeElement;

    public ClassBuilder classBuilder;

    public ClassEntity(TypeElement typeElement) {
        this.typeElement=typeElement;
        simpleName=typeElement.getSimpleName().toString();
        packageName=AptCompileUtils.getPackageName(typeElement);

        classBuilder=new ClassBuilder(this);
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<FieldEntity> getFields() {
        return fields;
    }

    public void addFields(TreeSet<FieldEntity> fields) {
        this.fields.addAll(fields);
    }

    public void addField(FieldEntity field){
        this.fields.add(field);
    }
}

