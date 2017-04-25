package com.zgf.tars.server.testapp.impl;

import com.qq.tars.support.log.Logger;
import com.qq.tars.support.notify.NotifyHelper;
import com.zgf.tars.server.testapp.HelloServant;

/**
 * Created by zgf on 17/4/21.
 */
public class HelloServantImpl implements HelloServant {
    private final static Logger FLOW_LOGGER = Logger.getLogger("flow", Logger.LogType.ALL);

    public String hello(int no, String name) {
        String format = String.format("hello no=%s, name=%s, time=%s", no, name, System.currentTimeMillis());
        FLOW_LOGGER.info(format);
        NotifyHelper.getInstance().notifyNormal("notify normal "+format);
        NotifyHelper.getInstance().notifyWarn("notify warn "+format);
        NotifyHelper.getInstance().notifyError("notify error "+format);
        return format;
    }
}
