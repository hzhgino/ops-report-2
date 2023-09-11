package cn.cgt.service;

import cn.cgt.dto.CpuInfoDTO;
import cn.cgt.dto.DiskInfoDTO;
import cn.cgt.dto.MEMInfoDTO;
import cn.cgt.reader.CpuInfoReadListen;
import cn.cgt.reader.DiskInfoReadListen;
import cn.cgt.reader.MemInfoReadListen;
import cn.cgt.store.MachineInfoStore;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.excel.EasyExcel;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务器月报,excel细心获取
 *
 * @author HuangZh
 * @date 2023/09/11
 */
@Service
public class F_33_DailyExcelReadService {

    private List<File> xlsxList;
    private CpuInfoReadListen cpuInfoReadListen = new CpuInfoReadListen();
    private MemInfoReadListen menInfoReadListen = new MemInfoReadListen();

    /**
     * 执行
     */
    public void execution(String excelDir) {
        //读取excel日报记录

        File[] files = FileUtil.ls(excelDir);
        xlsxList = Arrays.stream(files)
                .filter(file -> file.getName().endsWith(".xlsx"))
                .sorted(Comparator.comparing(File::getName))
                .collect(Collectors.toList());

        for (File file : xlsxList) {
            //读取CPU
            EasyExcel.read(file, CpuInfoDTO.class, cpuInfoReadListen)
                    .headRowNumber(2)
                    .sheet(1)
                    .doRead();

            //读取内存
            EasyExcel.read(file, MEMInfoDTO.class, menInfoReadListen)
                    .headRowNumber(2)
                    .sheet(2)
                    .doRead();
        }
        //获取cpu区间
        this.getCpuInfos();
        // 获取内存信息
        this.getMenInfos();
        //获取磁盘使用率
        this.getDIskInfos();
    }


//    public static void main(String[] args) {
//        F_33_DailyExcelReadService f_33_dailyExcelReadService = new F_33_DailyExcelReadService();
//        f_33_dailyExcelReadService.execution();
//        System.out.println("---cpu---");
//        MachineInfoStore.CPU_INFO_REPORT.forEach((k, v) -> System.out.println(k + " : " + v));
//        System.out.println("---men---");
//        MachineInfoStore.MEN_INFO_REPORT.forEach((k, v) -> System.out.println(k + " : " + v));
//        System.out.println("---disk---");
//        MachineInfoStore.DISK_INFO_REPORT.forEach((k, v) -> System.out.println(k + " : " + v));
//    }


    private void getDIskInfos() {
        DiskInfoReadListen diskInfoReadListen = new DiskInfoReadListen();
        File last = CollUtil.getLast(xlsxList);
        EasyExcel.read(last, DiskInfoDTO.class, diskInfoReadListen)
                .headRowNumber(2)
                .sheet(3)
                .doRead();
    }

    /**
     * 获取内存信息
     */
    private void getMenInfos() {
        Set<Map.Entry<String, List<Double>>> entries = MachineInfoStore.MEN_INFO_COMPUTE.entrySet();
        for (Map.Entry<String, List<Double>> entry : entries) {
            String key = entry.getKey();
            List<Double> value = entry.getValue();
            DoubleSummaryStatistics doubleSummaryStatistics = value.stream().mapToDouble(Double::doubleValue).summaryStatistics();
            double max = doubleSummaryStatistics.getMax();
            double min = doubleSummaryStatistics.getMin();
            MachineInfoStore.MEN_INFO_REPORT.put(key + "-mem", String.format("%s - %s", NumberUtil.formatPercent(min, 2), NumberUtil.formatPercent(max, 2)));
        }

    }

    /**
     * 获取cpu 的最大最小值
     */
    private void getCpuInfos() {
        Set<Map.Entry<String, List<Double>>> entries = MachineInfoStore.CPU_INFO_COMPUTE.entrySet();
        for (Map.Entry<String, List<Double>> entry : entries) {
            List<Double> value = entry.getValue();
            DoubleSummaryStatistics doubleSummaryStatistics = value.stream().mapToDouble(Double::doubleValue).summaryStatistics();
            String cpuReport = String.format("%s - %s", NumberUtil.formatPercent(doubleSummaryStatistics.getMin(), 2), NumberUtil.formatPercent(doubleSummaryStatistics.getMax(), 2));
            MachineInfoStore.CPU_INFO_REPORT.put(entry.getKey() + "-cpu", cpuReport);
        }
    }
}
