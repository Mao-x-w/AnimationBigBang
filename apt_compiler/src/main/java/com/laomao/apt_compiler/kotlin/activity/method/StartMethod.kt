package com.laomao.apt_compiler.kotlin.activity.method

import com.bennyhuo.aptutils.types.asJavaTypeName
import com.laomao.apt_compiler.kotlin.activity.ActivityClass
import com.laomao.apt_compiler.kotlin.activity.Field
import com.laomao.apt_compiler.kotlin.utils.ACTIVITYBUILDER
import com.laomao.apt_compiler.kotlin.utils.CONTEXT
import com.laomao.apt_compiler.kotlin.utils.INTENT
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/**
 * User: laomao
 * Date: 2020/2/25
 * Time: 下午5:01
 */
class StartMethod(val activityClass: ActivityClass, val name: String) {

    private var fields = ArrayList<Field>()

    private var isStaticMethod = true

    fun staticMethod(isStaticMethod: Boolean): StartMethod {
        this.isStaticMethod = isStaticMethod
        return this
    }

    fun addAllFields(fields: List<Field>) {
        this.fields.addAll(fields)
    }

    fun addField(field: Field) {
        this.fields.add(field)
    }

    /**
     * also和let的区别在于also返回的是当前对象，而let则是返回最后一行。also适用于链式调用
     * let其实就是一个作用域函数，当你需要定义一个变量在一个特定作用域的时候可以用它，此外，
     * 还可以用来作为非空判断，不为空才执行
     */
    fun copy(name: String) = StartMethod(activityClass, name).also {
        it.fields.addAll(fields)
    }

    /**
     * public void start(Context context,int age){
     *    Intent intent=new Intent(context,KotlinDemoActivity.class);
     *    intent.putExtra("age",age);
     *    context.startActivity(intent);
     * }
     */
    fun build(typeBuilder: TypeSpec.Builder) {
        var methodBuilder = MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addParameter(CONTEXT.java,"context")

        methodBuilder.addStatement("\$T intent = new \$T(context,\$T.class)", INTENT.java, INTENT.java,activityClass.typeElement)

        fields.forEach { field: Field ->
            var name=field.name
            methodBuilder.addParameter(field.symbol.type.asJavaTypeName(),name)
            methodBuilder.addStatement("intent.putExtra(\$S,\$L)",name,name)
        }

        if (isStaticMethod){
            methodBuilder.addModifiers(Modifier.STATIC)
        }else{
            methodBuilder.addStatement("fillIntent(intent)")
        }

        methodBuilder.addStatement("\$T.INSTANCE.startActivity(context,intent)", ACTIVITYBUILDER.java)

        typeBuilder.addMethod(methodBuilder.build())
    }
}