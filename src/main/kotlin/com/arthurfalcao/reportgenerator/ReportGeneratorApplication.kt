package com.arthurfalcao.reportgenerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReportGeneratorApplication

fun main(args: Array<String>) {
	runApplication<ReportGeneratorApplication>(*args)
}
