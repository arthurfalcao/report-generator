package com.arthurfalcao.reportgenerator.model

import javax.validation.constraints.NotEmpty

data class HelloWorldModel(
        @NotEmpty
        val helloWorld: String
)