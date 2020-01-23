package com.laomao.apt_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: laomao
 * Date: 2020-01-20
 * Time: 15-48
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Optional {

    String stringValue() default "";

    int intValue() default 0;

    float floatValue() default 0f;

    boolean booleanValue() default false;
}
