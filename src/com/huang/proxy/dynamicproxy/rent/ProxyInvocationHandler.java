package com.huang.proxy.dynamicproxy.rent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {

    // 跟静态代理不同的地方：这里的是要代理的接口！接口！
    private Rent rent;

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    //生成代理类。重点是第二个参数，获取要代理的抽象角色！之前都是一个角色，现在可以代理一类角色。
    //通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
    //第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象。也就是this.getClass().getClassLoader()
    //第二个参数 rent.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
    //第三个参数 handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上，就是this
    public Object getProxy(){
        return Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                rent.getClass().getInterfaces(),  //所有实现Rent接口的类都可以被代理，而不仅仅只是一个了。
                this);
    }

    //参数
    //proxy: 代理的真实对象。
    //method: 所要调用真实对象的某个方法的 Method 对象
    //args: 所要调用真实对象某个方法时接受的参数
    //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用。


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        seeHouse();

        Object result = method.invoke(rent, args);

        fare();

        return result;
    }

    public void seeHouse(){
        System.out.println("带房客看房");
    }

    public void fare(){
        System.out.println("收钱");
    }
}
