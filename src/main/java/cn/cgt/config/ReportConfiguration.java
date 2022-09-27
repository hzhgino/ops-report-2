package cn.cgt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author HuangZh
 * @date 2022/09/26
 */
@Data
@Component
@ConfigurationProperties(prefix = "cgt.report")
public class ReportConfiguration {


    /**
     * 模板路径
     */
    private String templatePath;
    /**
     * 输出路径
     */
    private String outPath;





}
