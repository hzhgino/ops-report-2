package cn.cgt.service;

import cn.cgt.config.ReportConfiguration;
import cn.cgt.util.ReportFileUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 项目运维资料\三、系统维护\过程资料\安全管理\服务器中间件巡查表记录
 * 2.4.2、安全管理-服务器中间件况巡查表.docx
 *
 * @author HuangZh
 * @date 2022/09/26
 */
@Service
public class F_242_MiddleService {

    @Autowired
    private ReportConfiguration reportConfiguration;

    /**
     * 报告
     *
     * @param date        日期
     */
    public void report(String date) {
        //
        String templatePath = reportConfiguration.getTemplatePath() + "项目运维资料/三、系统维护/过程资料/安全管理/服务器中间件巡查记表记录/2.4.2、安全管理-服务器中间件况巡查表.docx";
        //加载文档
        Document doc = new Document();
        doc.loadFromFile(templatePath);

        DateTime dateTime = DateUtil.parseDate(date);
        int year = dateTime.year();
        int month = dateTime.monthBaseOne();
        String codeDate = year + "-" + month;
        Map<String, String> templatesParams = new HashMap<>();
        templatesParams.put("{编号}", "SJW-MD-" + codeDate);
        templatesParams.put("{日期}", date);

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
