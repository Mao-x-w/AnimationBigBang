package com.laomao.apt_compiler.kotlin.activity

import com.bennyhuo.aptutils.types.asTypeMirror
import com.laomao.apt_annotation.Optional
import com.sun.tools.javac.code.Symbol
import javax.lang.model.type.TypeKind

/**
 * User: laomao
 * Date: 2020/2/22
 * Time: 下午1:02
 */

class OptionalField(symbol: Symbol.VarSymbol) : Field(symbol) {

    var defaultValue: Any? = null
        private set

    override val prefix: String = "OPTIONAL_"

    init {
        val optional = symbol.getAnnotation(Optional::class.java)

        when (symbol.type.kind) {
            TypeKind.BOOLEAN -> defaultValue = optional.booleanValue
            TypeKind.BYTE, TypeKind.CHAR, TypeKind.SHORT, TypeKind.INT, TypeKind.LONG -> defaultValue = optional.intValue
            TypeKind.FLOAT, TypeKind.DOUBLE -> defaultValue = optional.floatValue
            else ->if (symbol.type==String::class.java.asTypeMirror()){
                defaultValue=""""${optional.stringValue}""""
            }

        }
    }

    override fun compareTo(other: Field): Int {
        return if (other is OptionalField){
            super.compareTo(other)
        }else{
            1
        }
    }
}