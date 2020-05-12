package com.laomao.apt_compiler.java.entity;

import com.laomao.apt_annotation.BindddView;

import javax.lang.model.element.VariableElement;

/**
 * User: laomao
 * Date: 2020/2/27
 * Time: 下午3:37
 */
public class FieldEntity {

    private String name;

    private int annoValue;

    public FieldEntity(VariableElement element) {
        name=element.getSimpleName().toString();
        annoValue=element.getAnnotation(BindddView.class).value();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAnnoValue() {
        return annoValue;
    }

    public void setAnnoValue(int annoValue) {
        this.annoValue = annoValue;
    }
}

