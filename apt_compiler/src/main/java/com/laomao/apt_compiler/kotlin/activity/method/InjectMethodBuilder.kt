package com.laomao.apt_compiler.kotlin.activity.method

import com.bennyhuo.aptutils.types.asJavaTypeName
import com.laomao.apt_compiler.kotlin.activity.ActivityClass
import com.laomao.apt_compiler.kotlin.activity.Field
import com.laomao.apt_compiler.kotlin.activity.OptionalField
import com.laomao.apt_compiler.kotlin.utils.ACTIVITY
import com.laomao.apt_compiler.kotlin.utils.BUNDLE
import com.laomao.apt_compiler.kotlin.utils.BUNDLE_UTILS
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import javax.lang.model.element.Modifier

/**
 * User: laomao
 * Date: 2020/2/26
 * Time: 下午6:35
 */
class InjectMethodBuilder(val activityClass: ActivityClass) {

    /**
     * \$S \$T \$L是javapoet定义的，将传递过来的东西进行转换。S:转换为string T:转换为type  L:不做任何处理
     */
    fun build(typeBuilder:TypeSpec.Builder){
        val injectMethodBuilder = MethodSpec.methodBuilder("inject")
                .addParameter(ACTIVITY.java,"instance")
                .addParameter(BUNDLE.java,"savedInstance")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(TypeName.VOID)
                .beginControlFlow("if(instance instanceof \$T)",activityClass.typeElement)
                .addStatement("\$T typeInstance=(\$T)instance",activityClass.typeElement,activityClass.typeElement)
                .addStatement("\$T extras=savedInstance==null?typeInstance.getIntent().getExtras():savedInstance", BUNDLE.java)
                .beginControlFlow("if(extras!=null)")

        activityClass.fields.forEach { field: Field ->
            val name = field.name
            val typeName = field.symbol.type.asJavaTypeName().box()

            if (field is OptionalField){
                injectMethodBuilder.addStatement("\$T \$LValue = \$T.<\$T>get(extras,\$S,\$L)",typeName,name, BUNDLE_UTILS.java,typeName,name,field.defaultValue)
            }else{
                injectMethodBuilder.addStatement("\$T \$LValue = \$T.<\$T>get(extras,\$S)",typeName,name, BUNDLE_UTILS.java,typeName,name)
            }

            if (field.isPrivate){
                injectMethodBuilder.addStatement("typeInstance.set\$L(\$LValue)",name.capitalize(),name)
            }else{
                injectMethodBuilder.addStatement("typeInstance.\$L=\$LValue",name,name)
            }
        }

        injectMethodBuilder.endControlFlow()
        injectMethodBuilder.endControlFlow()
        typeBuilder.addMethod(injectMethodBuilder.build())
    }
}