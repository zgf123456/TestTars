package com.zgf.tars.client.test;

import com.qq.tars.client.Communicator;
import com.qq.tars.client.CommunicatorConfig;
import com.qq.tars.client.CommunicatorFactory;
import com.zgf.tars.client.testapp.HelloPrx;
import com.zgf.tars.client.testapp.HelloPrxCallback;
import org.junit.Test;

import java.util.UUID;

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

    public static void main(String[] args) throws Exception {
        // 依赖 JDK版本 1.8

        // 客户端寻址模式
        // 1. 直接寻址
        //      TestApp.HelloServer.HelloObj@tcp -h 10.211.55.9 -p 20002 -t 60000
        //      多节点模式 TestApp.HelloServer.HelloObj@tcp -h 10.211.55.9 -p 20002 -t 60000:tcp -h 10.211.55.9 -p 20002 -t 60000

//        CommunicatorConfig cfg = new CommunicatorConfig();
//        Communicator communicator = CommunicatorFactory.getInstance().getCommunicator(cfg);
//        final HelloPrx proxy = communicator.stringToProxy(HelloPrx.class, "TestApp.HelloServer.HelloObj@tcp -h 10.211.55.9 -p 20002 -t 60000");

        // 2. 注册中心寻址
        CommunicatorConfig cfg = CommunicatorConfig.load(TestClient.class.getClassLoader().getResource("tarsConfig.conf").getFile());
        Communicator communicator = CommunicatorFactory.getInstance().getCommunicator(cfg);
        final HelloPrx proxy = communicator.stringToProxy(HelloPrx.class, "TestApp.HelloServer.HelloObj");


        // 服务调用模式
        // 1. 单向调用
        // 2. 同步调用
        // 3. 异步调用
        // 4. Set调用 -- 服务编排??

        //同步调用
        Runnable runnable = new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    String ret = proxy.hello((int) Thread.currentThread().getId(), "Hello World(" + Thread.currentThread().getName() + "-" + UUID.randomUUID().toString() + ")");
                    System.out.println(ret);
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }

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
