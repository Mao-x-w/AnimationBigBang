package com.laomao.apt_compiler.kotlin.activity

import com.sun.tools.javac.code.Symbol

/**
 * User: laomao
 * Date: 2020/2/22
 * Time: 下午12:07
 */
open class Field(val symbol:Symbol.VarSymbol):Comparable<Field> {

    val name:String=symbol.qualifiedName.toString()

    open val prefix="REQUIRED_"

    val isPrivate=symbol.isPrivate

    val isPrimitive=symbol.type.isPrimitive

    override fun compareTo(other: Field): Int {
        return name.compareTo(other.name)
    }

    override fun toString(): String {
        return "$name:${symbol.type}"
    }
}