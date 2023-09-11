package cn.cgt.reader;

import cn.cgt.dto.CpuInfoDTO;
import cn.cgt.store.MachineInfoStore;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangZh
 * @date 2023/09/11
 */
@Slf4j
@Service
public class CpuInfoReadListen implements ReadListener<CpuInfoDTO> {

    private final List<CpuInfoDTO> cachedDataList = new ArrayList<>();


    @Override
    public void invoke(CpuInfoDTO data, AnalysisContext context) {
        cachedDataList.add(data);
    }


    /**
     * 数据解析玩之后会调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        cachedDataList.forEach(v -> MachineInfoStore.ADD_CPU_INFO(v.getHostName(), Double.valueOf(v.getStatus15())));
        cachedDataList.clear();
    }
}
