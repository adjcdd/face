package com.grgbanking.framework.manager.service;

import com.grgbanking.framework.domains.common.ErrorCode;
import com.grgbanking.framework.domains.common.RestResponse;
import com.grgbanking.framework.domains.employeeDailyRule.param.EmployeeDailyRuleAddParam;
import com.grgbanking.framework.domains.employeeDailyRule.param.EmployeeDailyRuleListParam;
import com.grgbanking.framework.domains.employeeDailyRule.param.EmployeeDailyRuleUpdateParam;
import com.grgbanking.framework.domains.employeeDailyRule.pojo.EmployeeDailyRulePojo;
import com.grgbanking.framework.domains.manager.common.json.PaginationJson;
import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import com.grgbanking.framework.manager.mapper.EmployeeDailyRuleMapper;
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
import java.util.List;

/**
 * Created by zhangweihua on 2018/1/18.
 */
@Service("employeeDailyRuleService")
public class EmployeeDailyRuleService {
    private Logger logger = LoggerFactory.getLogger(EmployeeDailyRuleService.class);
    @Autowired
    private EmployeeDailyRuleMapper employeeDailyRuleMapper;

    public RestResponse getEmployeeDailyRuleList(EmployeeDailyRuleListParam employeeDailyRuleListParam) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询规则列表!");
        RestResponse restResponse = new RestResponse();
        try {
            employeeDailyRuleListParam.setCurrentCount((employeeDailyRuleListParam.getPageNo() - 1) * employeeDailyRuleListParam.getPageSize());
            List<EmployeeDailyRulePojo> employeeDailyRulePojoList = new ArrayList<EmployeeDailyRulePojo>();
            long totalCount = 0;
            employeeDailyRulePojoList = this.employeeDailyRuleMapper.getEmployeeDailyRuleList(employeeDailyRuleListParam);
            totalCount = this.employeeDailyRuleMapper.getEmployeeDailyRuleCount(employeeDailyRuleListParam);

            PaginationJson page = new PaginationJson();
            page.setPageNo(employeeDailyRuleListParam.getPageNo());
            page.setPageSize(employeeDailyRuleListParam.getPageSize());
            page.setData(employeeDailyRulePojoList);
            page.setTotalCount(totalCount);
            page.setTotalPage(PageUtil.calTotalPage(totalCount, employeeDailyRuleListParam.getPageSize()));
            restResponse.setResponseBody(page);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询规则列表出现异常", e);
            throw e;
        }
        return restResponse;
    }
    public RestResponse getEmployeeDailyRuleInfo(EmployeeDailyRulePojo employeeDailyRulePojo) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始查询规则信息!");
        RestResponse restResponse = new RestResponse();
        try {
            employeeDailyRulePojo = this.employeeDailyRuleMapper.getEmployeeDailyRuleInfo(employeeDailyRulePojo);
            restResponse.setResponseBody(employeeDailyRulePojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("查询成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "查询规则出现信息", e);
            throw e;
        }
    return restResponse;
    }
    @Transactional(rollbackFor = Exception.class)
    public RestResponse addEmployeeDailyRule(EmployeeDailyRuleAddParam employeeDailyRuleAddParam) throws Exception {
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始新增规则!");
        RestResponse restResponse = new RestResponse();
        try {
                EmployeeDailyRulePojo employeeDailyRulePojo = new EmployeeDailyRulePojo();
                BeanUtils.copyProperties(employeeDailyRuleAddParam,employeeDailyRulePojo);
                this.employeeDailyRuleMapper.addEmployeeDailyRulePojo(employeeDailyRulePojo);
                restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                restResponse.getResponseHeader().setMessage("新增规则成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "新增规则出现异常", e);
            throw e;
        }
        return restResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public RestResponse deleteEmployeeDailyRule(EmployeeDailyRulePojo employeeDailyRulePojo) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始删除规则!");
        RestResponse restResponse = new RestResponse();
        try {
            this.employeeDailyRuleMapper.deleteEmployeeDailyRulePojo(employeeDailyRulePojo);
            restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
            restResponse.getResponseHeader().setMessage("删除规则成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "删除规则出现异常", e);
            throw e;
        }
        return restResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    public RestResponse updateEmployeeDailyRule(EmployeeDailyRuleUpdateParam employeeDailyRuleUpdateParam) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始更新规则!");
        RestResponse restResponse = new RestResponse();
        try {
            EmployeeDailyRulePojo employeeDailyRulePojo = new EmployeeDailyRulePojo();
            BeanUtils.copyProperties(employeeDailyRuleUpdateParam,employeeDailyRulePojo);
                this.employeeDailyRuleMapper.updateEmployeeDailyRulePojo(employeeDailyRulePojo);
                restResponse.getResponseHeader().setErrorCode(ErrorCode.SUCCESS);
                restResponse.getResponseHeader().setMessage("更新规则成功");
        } catch (Exception e) {
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "更新规则出现异常", e);
            throw e;
        }
        return restResponse;
    }

    public RestResponse exportEmployeeDailyRule(EmployeeDailyRuleListParam employeeDailyRuleListParam, HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始导出考勤规则报表!");
        RestResponse restResponse = new RestResponse();
        try{
            List<EmployeeDailyRulePojo> employeeDailyRulePojoList=this.employeeDailyRuleMapper.exportList(employeeDailyRuleListParam);
            List<EmployeeDailyRulePojo> employeeDailyRulePojos=new ArrayList<EmployeeDailyRulePojo>();
            for(int i=0;i<employeeDailyRulePojoList.size();i++){
                EmployeeDailyRulePojo employeeDailyRulePojo=new EmployeeDailyRulePojo();
                employeeDailyRulePojo=employeeDailyRulePojoList.get(i);
                employeeDailyRulePojos.add(employeeDailyRulePojo);
            }
            String title="考勤规则报表";
            String[] headersName={"编号","标题","上班时间","下班时间","部门"};
            ExportExcel exportExcel=new ExportExcel();
            exportExcel.exportExcel(title,headersName,employeeDailyRulePojos,response);
        }catch (Exception e){
            logger.error("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "导出考勤规则报表出现异常", e);
            throw e;
        }

        return restResponse;
    }
}
