package cn.cgt.util;

import cn.cgt.config.ReportConfiguration;
import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 报告文件工具类
 *
 * @author HuangZh
 * @date 2022/09/26
 */
@Component
public class ReportFileUtil {


    private static ReportConfiguration reportConfiguration;

    @Autowired
    public void setReportConfiguration(ReportConfiguration reportConfiguration) {
        ReportFileUtil.reportConfiguration = reportConfiguration;
    }

    /**
     * 传入模板文件路径， 返回报告文件生成路径
     * 输出文件路径处理
     *
     * @param templatePath 模板文件路径
     * @param date         日期
     *
     * @return {@link String}
     */
    public static String outFilePathHandler(String templatePath, String date) {
        String outFilePath = templatePath.replace(reportConfiguration.getTemplatePath(), reportConfiguration.getOutPath());
        //检查输出目录是否存在
        String outPath = outFilePath.replace(FileUtil.getName(outFilePath), "");
        if (!FileUtil.newFile(outPath).exists()) {
            FileUtil.mkdir(outPath);
        }
        //修改输出名称
        String mainName = FileUtil.mainName(outFilePath);
        String outFileName = mainName + "(" + date + ")";
        return outFilePath.replace(mainName, outFileName);
    }


}
