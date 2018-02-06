package com.grgbanking.framework.manager.task;

import com.grgbanking.framework.domains.employeeSign.param.EmployeeSignIdentifyParam;
import com.grgbanking.framework.util.base64.Base64Util;
import com.grgbanking.framework.util.http.OkHttp3Utils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;

/**
 * Created by zhangweihua on 2018/1/31.
 */
public class CheckTimeJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String path="G:\\123\\";
        for(int i=1;i<16;i++){
            String path1=path+i+".jpg";
            File file = new File(path1);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
                byte[] b = new byte[1000];
                int n;
                while ((n = fis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
                byte[] buffer = bos.toByteArray();
                fis.close();
                bos.close();
                EmployeeSignIdentifyParam employeeSignIdentifyParam = new EmployeeSignIdentifyParam();
                employeeSignIdentifyParam.setDevice_no("001");
                employeeSignIdentifyParam.setImage(Base64Util.encodeBase64Byte2Str(buffer));
                String url = "http://192.168.1.22:8088/face/monitoring";
                String param = "{\"image\":\"" + employeeSignIdentifyParam.getImage() + "\",\"device_no\":\"" + employeeSignIdentifyParam.getDevice_no() +"\"}";
                String respStr = OkHttp3Utils.postJson(url, param, null);
                System.out.print(respStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
