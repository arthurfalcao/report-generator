package com.arthurfalcao.reportgenerator.controller

import com.arthurfalcao.reportgenerator.service.ReportService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.FileInputStream
import java.util.*

@RestController
@RequestMapping("/reports")
class ReportController {

    @Autowired
    lateinit var reportService: ReportService

    @PostMapping("/{template}", produces = [MediaType.APPLICATION_PDF_VALUE])
    fun getReportByTemplate(
            @PathVariable template: String,
            @RequestBody jsonData: String
    ): ResponseEntity<Any> {
        val report = reportService.generateReport(template, jsonData) ?: return ResponseEntity.notFound().build<Any>()

        val resource = InputStreamResource(FileInputStream(report))
        val id = UUID.randomUUID()

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=${template}${id}.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource)
    }
}