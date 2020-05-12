package com.laomao.apt_compiler;

import com.google.auto.service.AutoService;
import com.laomao.apt_annotation.BindddView;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

/**
 * User: laomao
 * Date: 2019-12-02
 * Time: 16-09
 */
//@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    private Filer mFilerUtil;
    private Elements mElementUtils;
    private Types mTypeUtils;

    private Map<TypeElement, Set<ViewInfo>> mToBindMap = new HashMap<>(); //用于记录需要绑定的View的名称和对应的id

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        // 文件管理工具类，在后面生成java文件时会用到
        mFilerUtil = processingEnv.getFiler();
        // Element处理类，获取包名用到
        mElementUtils = processingEnv.getElementUtils();
        // 类型处理工具类，这里没用到
        mTypeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes=new HashSet<>();
        // 提供我们自定义的apt能够处理的注解
        supportTypes.add(BindddView.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // apt支持的版本号
        return SourceVersion.latestSupported();
    }

    /**
     * 最重要的方法，用来处理注解
     * @param set 要处理的注解的类型集合
     * @param roundEnv 表示运行环境，可以通过这个参数获得被注解标注的代码块
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        if (set != null && set.size() != 0) {
            // 获取所有被@BindView标记的Element
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindddView.class);//获得被BindView注解标记的element

            categories(elements);//对不同的Activity进行分类

            //对不同的Activity生成不同的帮助类
            for (TypeElement typeElement : mToBindMap.keySet()) {
                String code = generateCode(typeElement);    //获取要生成的帮助类中的所有代码
                String helperClassName = typeElement.getQualifiedName() + "$$Autobind"; //构建要生成的帮助类的类名

                //输出帮助类的java文件，在这个例子中就是MainActivity$$Autobind.java文件
                //输出的文件在build->source->apt->目录下
                try {
                    JavaFileObject jfo = mFilerUtil.createSourceFile(helperClassName, typeElement);
                    Writer writer = jfo.openWriter();
                    writer.write(code);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return true;
        }
        return false;
    }


    /**
     * 将elements进行分类，按照所在的Activity，放到map中，其中，
     * key是Activity名字
     * value是ViewInfo对应的集合，而ViewInfo中包含要绑定View的id，和外面定义的名称
     * @param elements
     */
    private void categories(Set<? extends Element> elements) {
        for (Element element : elements) {  //遍历每一个element
            VariableElement variableElement = (VariableElement) element;    //被@BindView标注的应当是变量，这里简单的强制类型转换
            TypeElement enclosingElement = (TypeElement) variableElement.getEnclosingElement(); //获取代表Activity的TypeElement
            Set<ViewInfo> views = mToBindMap.get(enclosingElement); //views储存着一个Activity中将要绑定的view的信息
            if (views == null) {    //如果views不存在就new一个
                views = new HashSet<>();
                mToBindMap.put(enclosingElement, views);
            }
            BindddView bindAnnotation = variableElement.getAnnotation(BindddView.class);    //获取到一个变量的注解
            int id = bindAnnotation.value();    //取出注解中的value值，这个值就是这个view要绑定的xml中的id
            views.add(new ViewInfo(variableElement.getSimpleName().toString(), id));    //把要绑定的View的信息存进views中
        }
    }

    /**
     * 生成注解类及注解方法，注解类实现了IBindHelper接口，以便外面知道它的方法
     * 在方法中，外面传入要被注解的类对象，之后强转为对应的对象rawClassName
     * 然后通过对象.变量进行赋值操作，以实现findViewById的操作
     * @param typeElement
     * @return
     */
    private String generateCode(TypeElement typeElement) {
        String rawClassName = typeElement.getSimpleName().toString(); //获取要绑定的View所在类的名称
        String packageName = ((PackageElement) mElementUtils.getPackageOf(typeElement)).getQualifiedName().toString(); //获取要绑定的View所在类的包名
        String helperClassName = rawClassName + "$$Autobind";   //要生成的帮助类的名称

        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packageName).append(";\n");   //构建定义包的代码
        builder.append("import com.laomao.apt_api.template.IBindHelper;\n\n"); //构建import类的代码

        builder.append("public class ").append(helperClassName).append(" implements ").append("IBindHelper");   //构建定义帮助类的代码
        builder.append(" {\n"); //代码格式，可以忽略
        builder.append("\t@Override\n");    //声明这个方法为重写IBindHelper中的方法
        builder.append("\tpublic void inject(" + "Object" + " target ) {\n");   //构建方法的代码
        for (ViewInfo viewInfo : mToBindMap.get(typeElement)) { //遍历每一个需要绑定的view
            builder.append("\t\t"); //代码格式，可以忽略
            builder.append(rawClassName + " substitute = " + "(" + rawClassName + ")" + "target;\n");    //强制类型转换

            builder.append("\t\t"); //代码格式，可以忽略
            builder.append("substitute." + viewInfo.viewName).append(" = ");    //构建赋值表达式
            builder.append("substitute.findViewById(" + viewInfo.id + ");\n");  //构建赋值表达式
        }
        builder.append("\t}\n");    //代码格式，可以忽略
        builder.append('\n');   //代码格式，可以忽略
        builder.append("}\n");  //代码格式，可以忽略

        return builder.toString();
    }


    //要绑定的View的信息载体
    class ViewInfo {
        String viewName;    //view的变量名
        int id; //xml中的id

        public ViewInfo(String viewName, int id) {
            this.viewName = viewName;
            this.id = id;
        }
    }

}
