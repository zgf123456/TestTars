package com.zgf.tars.server.tars.impl;

import com.zgf.tars.server.tars.HelloServant;

/**
 * Created by zgf on 17/4/21.
 */
public class HelloServantImpl implements HelloServant {
    public String hello(int no, String name) {
        return String.format("hello no=%s, name=%s, time=%s", no, name, System.currentTimeMillis());
    }
}
