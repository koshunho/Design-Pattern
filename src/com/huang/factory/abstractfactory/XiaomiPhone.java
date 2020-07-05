package com.huang.factory.abstractfactory;

public class XiaomiPhone implements PhoneProduct{
    @Override
    public void start() {
        System.out.println("开启小米手机");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭小米手机");
    }

    @Override
    public void call() {
        System.out.println("小米打电话");
    }

    @Override
    public void SMS() {
        System.out.println("小米发短信");
    }
}
