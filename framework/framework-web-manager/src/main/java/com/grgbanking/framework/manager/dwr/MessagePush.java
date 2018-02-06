package com.grgbanking.framework.manager.dwr;

import com.grgbanking.framework.manager.dispatcher.RequestIdentifierLocalHolder;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by zhangweihua on 2017/12/29.
 * 本节Demo实现的基本功能：
 * 在之前的的代码基础上修改，
 * 实现将index.jsp输入框中的推送文本显示在其它打开的jsp页面上
 * 目前流行的服务器推送技术
 */
public class MessagePush {
    private Logger logger = LoggerFactory.getLogger(MessagePush.class);
    public void send(final String content){
        logger.info("【" + RequestIdentifierLocalHolder.getRequestIdentifier() + "】 : 开始消息推送!");
        Runnable run = new Runnable(){
            private ScriptBuffer script = new ScriptBuffer();
            public void run() {
                //设置要调用的 js及参数
                script.appendCall("show" , content);
                //得到所有ScriptSession
                Collection<ScriptSession> sessions = Browser.getTargetSessions();
                //遍历每一个ScriptSession
                for (ScriptSession scriptSession : sessions){
                    scriptSession.addScript( script);
                }
            }
        };
        //执行推送
        Browser. withAllSessions(run);
    }
}
