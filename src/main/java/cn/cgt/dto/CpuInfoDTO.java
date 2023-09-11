package cn.cgt.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;

/**
 * 服务器CPU信息
 *
 * @author HuangZh
 * @date 2023/09/11
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CpuInfoDTO {

    /**
     * IP地址
     */
    @ExcelProperty("IP地址")
    private String hostName;

    /**
     * 1 分钟内的平均负载
     */
    @ExcelProperty("1 分钟内的平均负载")
    public String status1;
    /**
     * 5 分钟内的平均负载
     */
    @ExcelProperty("5 分钟内的平均负载")
    public String status5;
    /**
     * 15 分钟内的平均负载
     */
    @ExcelProperty("15 分钟内的平均负载")
    public String status15;


    //------以下是统计出来的报告结果

    @ExcelIgnore
    public String min;
    @ExcelIgnore
    public String max;

}
