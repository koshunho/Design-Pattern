package com.huang.factory.abstractfactory;

public class HuaweiRouter implements RouterProduct{
    @Override
    public void start() {
        System.out.println("启动华为路由器");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭华为路由器");
    }

    @Override
    public void WiFi() {
        System.out.println("打开华为WiFi");
    }

    @Override
    public void setting() {
        System.out.println("华为设置");
    }
}
