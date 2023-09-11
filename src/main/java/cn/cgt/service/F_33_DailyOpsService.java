package cn.cgt.service;

import cn.cgt.config.ReportConfiguration;
import cn.cgt.store.MachineInfoStore;
import cn.cgt.util.ReportFileUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 二、日常巡检/日常巡检报告/3.3、日常巡检报告.docx
 *
 * @author HuangZh
 * @date 2022/09/26
 */
@Service
public class F_33_DailyOpsService {

    @Autowired
    private ReportConfiguration reportConfiguration;
    @Autowired
    private F_33_DailyExcelReadService dailyExcelReadService;


    /**
     * 报告
     *
     * @param date 日期
     */
    public void report(String date) {

        String templatePath = reportConfiguration.getTemplatePath() + "项目运维资料/二、日常巡检/日常巡检报告/3.3、日常巡检报告.docx";
        String excelReportPath = reportConfiguration.getTemplatePath() + "excel/";

        //加载文档
        Document doc = new Document();
        doc.loadFromFile(templatePath);

        DateTime dateTime = DateUtil.parseDate(date);
        int year = dateTime.year();
        int month = dateTime.monthBaseOne();
        Map<String, String> templatesParams = new HashMap<>();
        templatesParams.put("{year}", String.valueOf(year));
        templatesParams.put("{month}", String.valueOf(month));

        Calendar calendar = dateTime.toCalendar();
        int endDay = DateUtil.getEndValue(calendar, DateField.DAY_OF_MONTH);
        templatesParams.put("{startDate}", year + "-" + month + "-01");
        templatesParams.put("{endDate}", year + "-" + month + "-" + endDay);
        //服务操作系统巡检结果
        dailyExcelReadService.execution(excelReportPath + year + "-" + month + "/");
        Map<String, String> cpuInfoCompute = MachineInfoStore.CPU_INFO_REPORT;
        Set<Map.Entry<String, String>> cpuEntries = cpuInfoCompute.entrySet();
        for (Map.Entry<String, String> entry : cpuEntries) {
            templatesParams.put("{" + entry.getKey() + "}", entry.getValue());
        }

        Map<String, String> menInfoReport = MachineInfoStore.MEN_INFO_REPORT;
        Set<Map.Entry<String, String>> memEntrySet = menInfoReport.entrySet();
        for (Map.Entry<String, String> entry : memEntrySet) {
            templatesParams.put("{" + entry.getKey() + "}", entry.getValue());
        }

        Map<String, String> diskInfoReport = MachineInfoStore.DISK_INFO_REPORT;
        Set<Map.Entry<String, String>> diskEntries = diskInfoReport.entrySet();
        for (Map.Entry<String, String> entry : diskEntries) {
            templatesParams.put("{" + entry.getKey() + "}", entry.getValue());
        }


        Set<Map.Entry<String, String>> entries = templatesParams.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            //调用方法用新文本替换原文本内容
            String key = entry.getKey();
            String value = entry.getValue();
            doc.replace(key, value, false, true);
        }

        //保存文档
        String fileNameDate = year + "年" + month + "月";
        String outFilePath = ReportFileUtil.outFilePathHandler(templatePath, fileNameDate);
        doc.saveToFile(outFilePath, FileFormat.Docx_2013);
        doc.dispose();
    }


}
