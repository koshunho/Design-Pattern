package com.huang.factory.abstractfactory;

public class XiaomiRouter implements RouterProduct{
    @Override
    public void start() {
        System.out.println("启动小米路由器");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭小米路由器");
    }

    @Override
    public void WiFi() {
        System.out.println("打开小米WiFi");
    }

    @Override
    public void setting() {
        System.out.println("小米设置");
    }
}
