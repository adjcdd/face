package com.grgbanking.framework.manager.mapper;

import com.grgbanking.framework.domains.employeeDailyRule.param.EmployeeDailyRuleAddParam;
import com.grgbanking.framework.domains.employeeDailyRule.param.EmployeeDailyRuleListParam;
import com.grgbanking.framework.domains.employeeDailyRule.param.EmployeeDailyRuleUpdateParam;
import com.grgbanking.framework.domains.employeeDailyRule.pojo.EmployeeDailyRulePojo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangweihua on 2018/1/18.
 */
@Service("employeeDailyRule")
public interface EmployeeDailyRuleMapper extends BaseMapper{
    List<EmployeeDailyRulePojo> getEmployeeDailyRuleList(EmployeeDailyRuleListParam employeeDailyRuleListParam);
    List<EmployeeDailyRulePojo> exportList(EmployeeDailyRuleListParam employeeDailyRuleListParam);
    Long getEmployeeDailyRuleCount(EmployeeDailyRuleListParam employeeDailyRuleListParam);
    EmployeeDailyRulePojo getEmployeeDailyRuleInfo(EmployeeDailyRulePojo employeeDailyRulePojo);
    EmployeeDailyRulePojo getEmployeeDailyRuleByDept(EmployeeDailyRulePojo employeeDailyRulePojo);
    void addEmployeeDailyRulePojo(EmployeeDailyRulePojo EmployeeDailyRulePojo);
    void deleteEmployeeDailyRulePojo(EmployeeDailyRulePojo employeeDailyRulePojo);
    void updateEmployeeDailyRulePojo(EmployeeDailyRulePojo employeeDailyRulePojo);
}
