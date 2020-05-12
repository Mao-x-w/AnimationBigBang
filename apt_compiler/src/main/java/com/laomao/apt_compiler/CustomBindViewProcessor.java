package com.laomao.apt_compiler;

import com.bennyhuo.aptutils.AptContext;
import com.laomao.apt_annotation.BindddView;
import com.laomao.apt_compiler.java.entity.ClassEntity;
import com.laomao.apt_compiler.java.entity.FieldEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * User: laomao
 * Date: 2020/2/27
 * Time: 下午12:55
 */
public class CustomBindViewProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        AptContext.INSTANCE.init(processingEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set=new HashSet<>();
        set.add(BindddView.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Map<TypeElement,ClassEntity> dataMap=new HashMap<>();

        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(BindddView.class);

        categoryElement(dataMap, annotatedElements);

        for (TypeElement typeElement : dataMap.keySet()) {
            dataMap.get(typeElement).classBuilder.build(AptContext.filer);
        }

        return false;
    }

    /**
     * 将Element放到对应的entity中，并封装到map中
     * @param dataMap
     * @param annotatedElements
     */
    private void categoryElement(Map<TypeElement, ClassEntity> dataMap, Set<? extends Element> annotatedElements) {
        for (Element annotatedElement : annotatedElements) {

            VariableElement variableElement = (VariableElement) annotatedElement;
            TypeElement typeElement = (TypeElement) annotatedElement.getEnclosingElement();

            ClassEntity classEntity = dataMap.get(typeElement);

            if (classEntity==null){
                classEntity=new ClassEntity(typeElement);
                dataMap.put(typeElement,classEntity);
            }
            classEntity.addField(new FieldEntity(variableElement));
        }
    }
}
