package cn.cgt;

import cn.cgt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * 命令初始化服务
 *
 * @author HuangZh
 * @date 2022/09/27
 */
@Service
public class CommandInitService implements CommandLineRunner {

    @Autowired
    private F_33_DailyOpsService f_33_dailyOpsService;
    @Autowired
    private F_211_OperationService f_211_operationService;
    @Autowired
    private F_212_CoreCheckService f_212_coreCheckService;
    @Autowired
    private F_213_FaultReportService f_213_faultReportService;
    @Autowired
    private F_241_FwService f_241_fwService;
    @Autowired
    private F_242_MiddleService f_242_middleService;
    @Autowired
    private F_244_ScanService f_244_scanService;

    @Override
    public void run(String... args) {
        String date = "2022-07-01";
        String version = "26598";
        String scanVersion = "0.100.0";

        //日常巡检报告
        f_33_dailyOpsService.report(date);
        //平台运行情况汇总
        f_211_operationService.report(date);
        //核心平台升级明细情况表
        f_212_coreCheckService.report(date);
        //平台故障及解决方式汇总
        f_213_faultReportService.report(date);
        //服务器防火墙情况巡查表
        f_241_fwService.report(date);
        //服务器中间件况巡查表
        f_242_middleService.report(date);
        //病毒漏洞检查情况
        f_244_scanService.report(date, version, scanVersion);

        //ArrayList<String> dateList = CollUtil.newArrayList("2022-07-01", "2022-08-01", "2022-09-01");
        //for (String date : dateList) {
        //
        //}


    }
}
