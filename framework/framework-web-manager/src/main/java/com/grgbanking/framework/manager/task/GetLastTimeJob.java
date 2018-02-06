package com.grgbanking.framework.manager.task;

import com.grgbanking.framework.util.http.OkHttp3Utils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created by zhangweihua on 2018/1/31.
 */
public class GetLastTimeJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = "http://192.168.1.22:8088/face/getLastTime";
        String param = "";
        String respStr = OkHttp3Utils.postJson(url, param, null);
        System.out.print(respStr);
        System.out.print("现在的时间是："+new Date());
    }
}
