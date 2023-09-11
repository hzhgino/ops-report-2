package cn.cgt.reader;

import cn.cgt.dto.DiskInfoDTO;
import cn.cgt.store.MachineInfoStore;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * excel 文件  磁盘数据监听处理
 *
 * @author HuangZh
 * @date 2023/09/11
 */
@Slf4j
@Service
public class DiskInfoReadListen implements ReadListener<DiskInfoDTO> {

    private final List<DiskInfoDTO> cachedDataList = new ArrayList<>();


    @Override
    public void invoke(DiskInfoDTO data, AnalysisContext context) {
        //计算使用率, 返回百分比格式的值
        cachedDataList.add(data);
    }


    /**
     * 数据解析玩之后会调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

        Map<String, DiskInfoDTO> diskInfoDTOMap = new HashMap<>();
        List<List<DiskInfoDTO>> groupList = CollUtil.groupByField(cachedDataList, "hostName");

        for (List<DiskInfoDTO> diskInfoDTOS : groupList) {
            DiskInfoDTO first = CollUtil.getFirst(diskInfoDTOS);
            if (first == null) {
                continue;
            }
            DiskInfoDTO build = first.toBuilder().build();
            for (DiskInfoDTO diskInfoDTO : diskInfoDTOS) {
                String path = diskInfoDTO.getPath();
                String useRate = diskInfoDTO.getUseRate();
                build.getUsedRates().add(path + ":" + useRate);
            }
            diskInfoDTOMap.put(first.getHostName(), build);
        }


        Collection<DiskInfoDTO> values = diskInfoDTOMap.values();
        for (DiskInfoDTO value : values) {
            MachineInfoStore.DISK_INFO_REPORT.put(value.getHostName() + "-disk", CollUtil.join(value.getUsedRates(), "\n"));
        }
        cachedDataList.clear();
    }
}
