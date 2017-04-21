package com.zgf.tars.client.test;

import com.qq.tars.client.Communicator;
import com.qq.tars.client.CommunicatorConfig;
import com.qq.tars.client.CommunicatorFactory;
import com.zgf.tars.client.testapp.HelloPrx;
import com.zgf.tars.client.testapp.HelloPrxCallback;
import org.junit.Test;

/**
 * Created by zgf on 17/4/21.
 */
public class TestClient {

    /**
     * 同步调用测试
     */
    @Test
    public void testSync() {
        CommunicatorConfig cfg = new CommunicatorConfig();
        //构建通信器
        Communicator communicator = CommunicatorFactory.getInstance().getCommunicator(cfg);
        //通过通信器，生成代理对象
        HelloPrx proxy = communicator.stringToProxy(HelloPrx.class, "testapp.HelloServer.HelloObj");
        String ret = proxy.hello(1000, "HelloWorld");
        System.out.println(ret);
    }

    @Test
    public void testAsync() {
        CommunicatorConfig cfg = new CommunicatorConfig();
        //构建通信器
        Communicator communicator = CommunicatorFactory.getInstance().getCommunicator(cfg);
        //通过通信器，生成代理对象
        HelloPrx proxy = communicator.stringToProxy(HelloPrx.class, "TestApp.HelloServer.HelloObj");
        proxy.async_hello(new HelloPrxCallback() {

            @Override
            public void callback_expired() {
            }

            @Override
            public void callback_exception(Throwable ex) {
            }

            @Override
            public void callback_hello(String ret) {
                System.out.println(ret);
            }
        }, 1000, "HelloWorld");
    }

    public static void main(String[] args) {
        CommunicatorConfig cfg = new CommunicatorConfig();
        Communicator communicator = CommunicatorFactory.getInstance().getCommunicator(cfg);
        HelloPrx proxy = communicator.stringToProxy(HelloPrx.class, "testapp.HelloServer.HelloObj@tcp -h 127.0.0.1 -p 18600 -t 60000");
        //同步调用
        String ret = proxy.hello(1000, "Hello World");
        System.out.println(ret);

//        //单向调用
//        proxy.async_hello(null, 1000, "Hello World");
//
//        //异步调用
//        proxy.async_hello(new HelloPrxCallback() {
//
//            @Override
//            public void callback_expired() {
//            }
//
//            @Override
//            public void callback_exception(Throwable ex) {
//            }
//
//            @Override
//            public void callback_hello(String ret) {
//                System.out.println(ret);
//            }
//        }, 1000, "Hello World");
    }
}
