package com.huang.proxy.dynamicproxy.rent;

// 一个动态代理，一般代理某一类业务，一个动态代理可以代理多个类，代理的是接口！
public class Client {
    public static void main(String[] args) {
        Host host = new Host();

        ProxyInvocationHandler pih = new ProxyInvocationHandler();

        //把真实角色放进去！！！！真实角色!!!！！!真实角色！！！！！！真实角色
        pih.setRent(host);

        //动态生成代理类
        Rent proxy = (Rent)pih.getProxy();

        proxy.rent();
    }
}
