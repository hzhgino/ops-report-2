package cn.cgt.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 服务器 内存信息
 * IP地址	总容量	已使用内存	空闲内存	共享内存	缓存、缓冲内存	剩余可用内存
 *
 * @author HuangZh
 * @date 2023/09/11
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class MEMInfoDTO {

    /**
     * IP地址
     */
    @ExcelProperty("IP地址")
    private String hostName;


    /**
     * 总容量
     */
    @ExcelProperty("总容量")
    private String total;

    /**
     * 已使用内存
     */
    @ExcelProperty("已使用内存")
    private String used;

    /**
     * 空闲内存
     */
    @ExcelProperty("空闲内存")
    private String free;

    /**
     * 共享内存
     */
    @ExcelProperty("共享内存")
    private String shared;

    /**
     * 缓存、缓冲内存
     */
    @ExcelProperty("缓存、缓冲内存")
    private String buffCache;

    /**
     * 剩余可用内存
     */
    @ExcelProperty("剩余可用内存")
    private String available;


    /**
     * 使用率
     */
    @ExcelIgnore
    private Double useRate;

}
