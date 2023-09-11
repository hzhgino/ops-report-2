package cn.cgt.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务器 磁盘
 * 主机名称	总空间	已使用空间	可用空间	使用率	目录路径
 *
 * @author HuangZh
 * @date 2023/09/11
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DiskInfoDTO {

    /**
     * IP地址
     */
    @ExcelProperty("主机名称")
    private String hostName;

    /**
     * 总空间
     */
    @ExcelProperty("总空间")
    private String total;

    /**
     * 已使用空间
     */
    @ExcelProperty("已使用空间")
    private String used;

    /**
     * 可用空间
     */
    @ExcelProperty("可用空间")
    private String free;

    /**
     * 使用率
     */
    @ExcelProperty("使用率")
    private String useRate;

    /**
     * 目录路径
     */
    @ExcelProperty("目录路径")
    private String path;

    /**
     * 使用率,以hostname为key,多个使用率则用路径区分,如:
     * key:sjw-1
     * value:[/:10%,/data:50%]
     */
    @ExcelIgnore
    private List<String> usedRates = new ArrayList<>();


}
