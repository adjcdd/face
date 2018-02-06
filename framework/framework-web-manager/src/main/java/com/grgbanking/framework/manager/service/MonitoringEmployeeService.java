package com.grgbanking.framework.manager.service;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.employee.pojo.EmployeePojo;
import com.grgbanking.framework.domains.employeeDaily.pojo.EmployeeDailyPojo;
import com.grgbanking.framework.domains.manager.common.json.PaginationJson;
import com.grgbanking.framework.domains.report.employee.monitoringEmployee.param.ExportMonitoringEmployeeParam;
import com.grgbanking.framework.domains.report.employee.monitoringEmployee.param.MonitoringEmployeeListParam;
import com.grgbanking.framework.domains.report.employee.monitoringEmployee.pojo.MonitoringEmployeePojo;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.mapper.EmployeeDailyMapper;
import com.grgbanking.framework.manager.mapper.EmployeeMapper;
import com.grgbanking.framework.manager.mapper.MonitoringEmployeeMapper;
import com.grgbanking.framework.manager.excel.ExportExcel;
import com.grgbanking.framework.util.page.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhangweihua on 2018/1/26.
 */
@Service("monitoringEmployeeService")
public class MonitoringEmployeeService {
    private Logger logger = LoggerFactory.getLogger(MonitoringEmployeeService.class);
    @Autowired
    private MonitoringEmployeeMapper monitoringEmployeeMapper;
    @Autowired
    private EmployeeDailyMapper employeeDailyMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    public RestResponse getMonitoringEmployeeList(MonitoringEmployeeListParam monitoringEmployeeListParam) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询个人实时考勤报表!");
        RestResponse restResponse = new RestResponse();
        try {
            monitoringEmployeeListParam.setCurrentCount((monitoringEmployeeListParam.getPageNo() - 1) * monitoringEmployeeListParam.getPageSize());
            List<MonitoringEmployeePojo> monitoringEmployeePojoList=this.monitoringEmployeeMapper.getList(monitoringEmployeeListParam);
            MonitoringEmployeePojo monitoringEmployeePojo=new MonitoringEmployeePojo();
            EmployeePojo employeePojo=new EmployeePojo();
            for(int i=0;i<monitoringEmployeePojoList.size();i++){
                monitoringEmployeePojo=monitoringEmployeePojoList.get(i);
                BeanUtils.copyProperties(monitoringEmployeePojo,employeePojo);
                employeePojo=this.employeeMapper.getEmployeeInfoByName(employeePojo);
                monitoringEmployeePojo.setDept(employeePojo.getDept());
                monitoringEmployeePojo.setCtfNo(employeePojo.getCtfNo());
                monitoringEmployeePojo.setStart_time(monitoringEmployeeListParam.getStart_time());
                monitoringEmployeePojo.setEnd_time(monitoringEmployeeListParam.getEnd_time());
                monitoringEmployeePojo.setDaily_time(new Date());
            }

            long totalCount = 0;
            totalCount = this.monitoringEmployeeMapper.getMonitoringEmployeeCount(monitoringEmployeeListParam);
            //totalCount=monitoringEmployeePojoList.size();
            PaginationJson page = new PaginationJson();
            page.setPageNo(monitoringEmployeeListParam.getPageNo());
            page.setPageSize(monitoringEmployeeListParam.getPageSize());
            page.setData(monitoringEmployeePojoList);
            page.setTotalCount(totalCount);
            page.setTotalPage(PageUtil.calTotalPage(totalCount, monitoringEmployeeListParam.getPageSize()));
            restResponse.setResponseBody(page);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询个人实时考勤报表成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询个人实时考勤报表出现异常", e);
            throw e;
        }
        return restResponse;
    }
    @Transactional(rollbackFor = Exception.class)
    public RestResponse addMonitoringEmployee() throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始新增个人实时考勤报表!");
        RestResponse restResponse = new RestResponse();
        try {
            //把当天所有签到完的用户放入报表
            List<EmployeeDailyPojo> employeeDailyPojoList=this.employeeDailyMapper.getEmployeeDailyCurrentDayList();
            EmployeeDailyPojo emp=new EmployeeDailyPojo();
            MonitoringEmployeePojo monitoringEmployeePojo=new MonitoringEmployeePojo();
            EmployeePojo employeePojo=new EmployeePojo();
            if(null!=employeeDailyPojoList){
                for(int i=0;i<employeeDailyPojoList.size();i++){
                    String id = UUID.randomUUID().toString().replaceAll("-", "");
                    emp=employeeDailyPojoList.get(i);
                    monitoringEmployeePojo.setId(id);
                    monitoringEmployeePojo.setName(emp.getName());
                    BeanUtils.copyProperties(emp,employeePojo);
                    employeePojo=this.employeeMapper.getEmployeeInfoByName(employeePojo);
                    monitoringEmployeePojo.setDept(employeePojo.getDept());
                    monitoringEmployeePojo.setCtfNo(employeePojo.getCtfNo());
                    monitoringEmployeePojo.setDaily_time(new Date());
                    monitoringEmployeePojo.setLater_times(emp.getLater_time()==null?0:1);
                    monitoringEmployeePojo.setEarly_times(emp.getEarly_time()==null?0:1);
                    monitoringEmployeePojo.setNormal_times(emp.getLater_time()==null&&emp.getEarly_time()==null?1:0);
                    monitoringEmployeePojo.setAttendance_days(1);
                    monitoringEmployeePojo.setAttendanced_days(emp.getFlag()!=null?1:0);
                    monitoringEmployeePojo.setTotal_latertime(emp.getLater_time());
                    monitoringEmployeePojo.setTotal_earlytime(emp.getEarly_time());
                    monitoringEmployeePojo.setNo_check_in(emp.getFlag()==5?1:0);
                    monitoringEmployeePojo.setNo_check_out(emp.getFlag()==6?1:0);
                    this.monitoringEmployeeMapper.addMonitoringEmployee(monitoringEmployeePojo);
                    restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                    restResponse.getResponseHeader().setMessage("新增个人实时考勤报表成功");
                }
            }
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "新增个人实时考勤报表出现异常", e);
            throw e;
        }
        return restResponse;
    }
    public RestResponse exportMonitoringEmployee(MonitoringEmployeeListParam monitoringEmployeeListParam,HttpServletRequest request,HttpServletResponse response) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始导出个人实时考勤报表!");
        RestResponse restResponse = new RestResponse();
        try{
            List<MonitoringEmployeePojo> monitoringEmployeePojoList=this.monitoringEmployeeMapper.exportList(monitoringEmployeeListParam);
            MonitoringEmployeePojo monitoringEmployeePojo=new MonitoringEmployeePojo();
            List<ExportMonitoringEmployeeParam> exportMonitoringEmployeeParamList=new ArrayList<ExportMonitoringEmployeeParam>();

            EmployeePojo employeePojo=new EmployeePojo();
            for(int i=0;i<monitoringEmployeePojoList.size();i++){
                monitoringEmployeePojo=monitoringEmployeePojoList.get(i);
                BeanUtils.copyProperties(monitoringEmployeePojo,employeePojo);
                employeePojo=this.employeeMapper.getEmployeeInfoByName(employeePojo);
                monitoringEmployeePojo.setDept(employeePojo.getDept());
                monitoringEmployeePojo.setCtfNo(employeePojo.getCtfNo());
                monitoringEmployeePojo.setStart_time(monitoringEmployeeListParam.getStart_time());
                monitoringEmployeePojo.setEnd_time(monitoringEmployeeListParam.getEnd_time());
                monitoringEmployeePojo.setDaily_time(new Date());
                ExportMonitoringEmployeeParam exportMonitoringEmployeeParam=new ExportMonitoringEmployeeParam();
                BeanUtils.copyProperties(monitoringEmployeePojo,exportMonitoringEmployeeParam);
                exportMonitoringEmployeeParamList.add(exportMonitoringEmployeeParam);
            }
            String title="个人实时考勤报表";
            String[] headersName={"姓名","部门","开始日期","结束日期","考勤日期","考勤结果","迟到次数","早退次数","正常次数","应出勤天数","实出勤天数","迟到时长","早退时长","上班无考勤次数","下班无考勤次数"};
            ExportExcel exportExcel=new ExportExcel();
            exportExcel.exportExcel(title,headersName,exportMonitoringEmployeeParamList,response);
            //exportExcel.exportExcel(title,headersName,monitoringEmployeePojoList,null);
            //exportExcel.exportExcel(title,headersName,exportMonitoringEmployeeParamList,null);
           // ExportExcel.exportMonitoringEmployeeExcel(monitoringEmployeePojoList,request,response);
        }catch (Exception e){
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "导出个人实时考勤报表出现异常", e);
            throw e;
        }

        return restResponse;
    }
}
