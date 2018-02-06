package com.grgbanking.framework.manager.dwr;

import org.directwebremoting.*;
import org.directwebremoting.proxy.dwr.Util;

import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * Created by zhangweihua on 2017/12/30.
 */
public class SendMessage {
    /**
     * 消息发送方法
     * @param msg //要发送的消息
     */

    public void sendMsg(String msg){
        WebContext contex = WebContextFactory.get();
//        Collection<ScriptSession> scriptSessions = contex.getScriptSessionsByPage(contex.getCurrentPage());
        ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
        //像当前session推送
        Util util = new Util(scriptSession);
        ScriptBuffer sb = new ScriptBuffer();
        sb.appendScript("show(");
        sb.appendData(msg);
        sb.appendScript(")");
        util.addScript(sb);
       /* WebContext contex= WebContextFactory.get();
        //获取client.jsp页面的ScriptSession集合，注意："/dwrpush/client.jsp"

        String currentPage = "framework-web-manager/resources/template/employee/feature/ui/employee.html"; // 要推信息的页面地址
        Collection<ScriptSession> sessions=contex.getScriptSessionsByPage(currentPage);
        // 获得所有已经打开此页面的会话
        Util util=new Util(sessions);
        ScriptBuffer sb=new ScriptBuffer();
        sb.appendScript("show(");
        sb.appendData(msg);
        sb.appendScript(")");
        util.addScript(sb);*/
    }
}
