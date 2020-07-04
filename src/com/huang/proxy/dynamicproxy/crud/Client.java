package com.huang.proxy.dynamicproxy.crud;
//先创建一个业务实现类(Impl)对象 和 一个代理类对象
public class Client {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        ProxyInvocationHandler pih = new ProxyInvocationHandler();

        pih.setTarget(userService);

        // 接口引用 真 正 指向的是一个绑定了业务类的代理对象，所以通过接口方法名调用的是被代理的方法！
        UserService proxy = (UserService) pih.getProxy();

        proxy.add();
    }
}
