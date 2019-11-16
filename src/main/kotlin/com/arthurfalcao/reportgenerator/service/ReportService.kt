package com.arthurfalcao.reportgenerator.service

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.xml.JRXmlLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.ui.jasperreports.JasperReportsUtils
import java.io.File
import java.io.FileOutputStream
import java.util.*

@Service
class ReportService {

    @Autowired
    lateinit var classService: ClassService

    fun generateReport(template: String, json: String): File? {
        val params = getParams(template, json)
        val pdfFile = File.createTempFile(template, ".pdf")

        FileOutputStream(pdfFile).use { pos ->
            val report = loadTemplate(template)
            val dataSource = JRBeanCollectionDataSource(Collections.singletonList("Report"))

            JasperReportsUtils.renderAsPdf(report, params, dataSource, pos)

            return pdfFile
        }
    }

    private fun getParams(template: String, json: String): HashMap<String, Any> {
        val classModel = classService.getClassModelByTemplate(template)

        val params = HashMap<String, Any>()

        return if (JsonParser().parse(json).isJsonArray) {
            val typeList = TypeToken.getParameterized(List::class.java, classModel).type
            val reportData = Gson().fromJson<List<Any>>(json, typeList)

            val itemsJRBean = JRBeanCollectionDataSource(reportData)
            params["CollectionDataSource"] = itemsJRBean
            params
        } else {
            val reportData = Gson().fromJson<Any>(json, classModel)
            params["json"] = reportData
            params
        }
    }

    private fun loadTemplate(template: String): JasperReport? {
        val reportInputStream = javaClass.getResourceAsStream("/templates/${template}.jrxml");
        val jasperDesign = JRXmlLoader.load(reportInputStream)

        return JasperCompileManager.compileReport(jasperDesign)
    }
}