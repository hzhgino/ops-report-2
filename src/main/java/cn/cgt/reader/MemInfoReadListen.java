package cn.cgt.reader;

import cn.cgt.dto.MEMInfoDTO;
import cn.cgt.store.MachineInfoStore;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存情况监听
 *
 * @author HuangZh
 * @date 2023/09/11
 */
@Slf4j
@Service
public class MemInfoReadListen implements ReadListener<MEMInfoDTO> {

    private final List<MEMInfoDTO> cachedDataList = new ArrayList<>();


    @Override
    public void invoke(MEMInfoDTO data, AnalysisContext context) {
        String used = data.getUsed().replace("Gi", "");
        String total = data.getTotal().replace("Gi", "");
        //计算使用率, 返回百分比格式的值
        double v = NumberUtil.parseDouble(used,0d) / NumberUtil.parseDouble(total,0d);
        data.setUseRate(v);
//        data.setUseRate(NumberUtil.formatPercent(v, 2));
        cachedDataList.add(data);
    }


    /**
     * 数据解析玩之后会调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        cachedDataList.forEach(v -> MachineInfoStore.ADD_MEN_INFO(v.getHostName(), v.getUseRate()));
        cachedDataList.clear();
    }
}
