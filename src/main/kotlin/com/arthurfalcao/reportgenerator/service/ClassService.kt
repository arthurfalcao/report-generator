package com.arthurfalcao.reportgenerator.service

import com.google.common.base.CaseFormat
import org.springframework.stereotype.Service
import com.arthurfalcao.reportgenerator.model.ParameterizedClass

@Service
class ClassService {

    fun getClassModelByTemplate(template: String): Class<*>? {
        val className = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, template)
        val classModel = ParameterizedClass("${className}Model").getValue()

        return Class.forName(classModel)
    }
}