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
        //报告日期
        String date = "2024-04-01";
        String version = " 2024-04-24  "; //病毒库
        String scanVersion = "8.0.5.5013";  //扫描程序
        // excel 下载地址：172.16.2.12:/home/thtf/ops/excel

//        System.out.println("数据更新报告： \\192.168.11.5\开发项目文档\\2023年\\市纪委监委党风廉政建设大数据平台运维（2023年）项目\\项目运维资料\\一、数据整理入库\\过程资料\\数据入库");
        System.out.println("数据更新报告: \\\\192.168.11.5\\开发项目文档\\2023年\\市纪委监委党风廉政建设大数据平台运维（2023年）项目\\项目运维资料\\一、数据整理入库\\过程资料\\数据入库");

        //日常巡检报告, 每日巡检记录 2.12服务器
        f_33_dailyOpsService.report(date);
        //平台运行情况汇总
        f_211_operationService.report(date);
        // 核心平台升级明细情况表
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
        //    f_241_fwService.report(date);
        //}


    }








}
