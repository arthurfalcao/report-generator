package com.arthurfalcao.reportgenerator.model

import com.fasterxml.jackson.databind.util.ClassUtil

class ParameterizedClass<A>(private val value: A) {
    fun getValue(): String {
        val packageName = ClassUtil.getPackageName(ParameterizedClass::class.java)
        return "${packageName}.${value}"
    }
}