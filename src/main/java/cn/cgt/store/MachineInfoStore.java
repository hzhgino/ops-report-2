package cn.cgt.store;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务器信息,内存存储
 *
 * @author HuangZh
 * @date 2023/09/11
 */
public class MachineInfoStore {

    /**
     * CPU 过程计算数据
     */
    public static Map<String, List<Double>> CPU_INFO_COMPUTE = new HashMap<>();
    public static Map<String, String> CPU_INFO_REPORT = new HashMap<>();
    /**
     * 内存 过程计算数据
     */
    public static Map<String, List<Double>> MEN_INFO_COMPUTE = new HashMap<>();
    public static Map<String, String> MEN_INFO_REPORT = new HashMap<>();
    /**
     * 磁盘报告数据
     */
    public static Map<String, String> DISK_INFO_REPORT = new HashMap<>();

    /**
     * CPU信息添加,添加前判断是否存在相同的key,如果存则value的list添加,如果不存在则new list 作为value
     */
    public static void ADD_CPU_INFO(String key, Double value) {
        List<Double> cpuInfoList = CPU_INFO_COMPUTE
                .computeIfAbsent(key, k -> new ArrayList<>());
        cpuInfoList.add(value);
    }

    /**
     * 内存信息添加,添加前判断是否存在相同的key,如果存则value的list添加,如果不存在则new list 作为value
     *
     * @param key
     * @param value 使用率
     */
    public static void ADD_MEN_INFO(String key, Double value) {
        List<Double> memInfoList = MEN_INFO_COMPUTE.computeIfAbsent(key, k -> new ArrayList<>());
        memInfoList.add(value);
    }

    /**
     * @param key   主机名称
     * @param value 磁盘使用(多路径合并处理)
     */
//    public static void ADD_DISK_INFO(String key, String value) {
//        List<String> diskInfoList = DISK_INFO_REPORT.computeIfAbsent(key, k -> new ArrayList<>());
//        diskInfoList.add(value);
//    }

}
