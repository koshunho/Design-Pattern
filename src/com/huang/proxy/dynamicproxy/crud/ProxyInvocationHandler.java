package com.huang.proxy.dynamicproxy.crud;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 通用的动态代理实现的类，所有的代理对象类型设置为Object即可
public class ProxyInvocationHandler implements InvocationHandler {

    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    //生成代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                target.getClass().getInterfaces(), //注意！
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log(method.getName());

        Object result = method.invoke(target, args);

        return result;
    }

    public void log(String methodName){
        System.out.println("执行了 " + methodName + " 方法");
    }
}
