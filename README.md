# Design-Pattern
Some examples to help understanding design pattern

## 代理模式

面试被问到最多的设计模式除了单例就是代理了。为什么要学代理模式 --> AOP底层就是动态代理。

#### 静态代理
![静态代理](http://qcorkht4q.bkt.clouddn.com/blog静态代理.png)

 - 抽象角色：一段使用**接口或抽象类**来实现
 - 真实角色：被代理的角色
 - 代理角色：代理真实角色，代理真实角色后，一般会做一些附属操作
 - 客户：使用代理角色来进行一些操作

例子：package com.huang.proxy.staticproxy;

--> 在这个过程中，你直接接触的就是中介，如同显示生活中，你看不到房东，但是你通过代理依旧租到了房东的房子，这就是代理模式。

代理模式的好处：

 1. 可以使我们的真实角色更加纯粹，不再关注一些**公共的事情**
 2. 公共的业务由代理完成，实现了业务分工
 3. 公共业务发生扩展时，变得更加集中和方便

缺点：

 1. 类多了，多了代理类，工作量变大。
    
	一个代理类只能对一个业务接口的实现类进行包装，如果有多个业务接口的话就要定义很多的实现类和代理类才行。而且如果代理类对业务方法的预处理、调用后操作都是一样的，则多个代理类就有很多重复代码。
	
	
再深入例子：package com.huang.proxy.staticproxy.crud

![横向编程](http://qcorkht4q.bkt.clouddn.com/blog横向编程.png)

#### 动态代理
动态代理的代理类是动态生成的，静态代理的代理类是我们提前写好的。

 1.基于接口的动态代理 --> JDK动态代理
 2.基于类的动态代理 --> cglib

JDK的动态代理需要了解2个类：InvocationHandler  和 Proxy

##### InvocationHandler
 当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用。
```java
// 当在代理实例上调用方法时，方法调用将被编码并分派到其调用处理程序的invoke()方法
public interface InvocationHandler {
    //参数
	//proxy: 代理的真实对象。
	//method: 所要调用真实对象的某个方法的 Method 对象
	//args: 所要调用真实对象某个方法时接受的参数
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable;
}
```

调用invoke试试
```java
        //注意了！ 因为Host的rent()方法是实例方法，在用invoke调用的时候必须传入一个实例进去，不然提示空指针
        //如果是一个静态方法的话，传入一个null参数就可以了
        Method[] methods3 = Host.class.getMethods();
        for(Method m : methods3){
            System.out.println(m);
        }
        Host.class.getMethod("rent").invoke(new Host());
```

##### Proxy
Proxy 这个类的作用就是用来动态创建一个代理对象的类。使用newProxyInstance方法得到一个动态的代理对象。
```java
     //loader - 一个 ClassLoader 对象，定义了由哪个 ClassLoader 对象来对生成的代理对象进行加载。
	 //interfaces - 一个 Interface 对象的数组，表示的是我将要给我需要代理的对象提供一组什么接口，如果我提供了一组接口给它，那么这个代理对象就宣称实现了该接口(多态)，这样我就能调用这组接口中的方法了
	 //h - 一个 InvocationHandler 对象，表示的是当我这个动态代理对象在调用方法的时候，会关联到哪一个 InvocationHandler 对象上
    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler h)
        throws IllegalArgumentException
```

例子：package com.huang.proxy.dynamicproxy.rent

**一个动态代理，一般代理某一类业务，一个动态代理可以代理多个类，代理的是接口！**

```java
    //生成代理类。重点是第二个参数，获取要代理的抽象角色！之前都是一个角色，现在可以代理一类角色。
    //通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
    //第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象。也就是this.getClass().getClassLoader()
    //第二个参数 rent.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
    //第三个参数 handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上，就是this
	//感觉就是通过这一步绑定了代理类 和 proxyInvocationHandler, 所以使用代理类的方法的时候 就会转发到pih的invoke上
    public Object getProxy(){
        return Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                rent.getClass().getInterfaces(),  //所有实现Rent接口的类都可以被代理，而不仅仅只是一个了。
                this);
    }
```

再深入例子：package com.huang.proxy.dynamicproxy.crud

**接口引用 *真 正* 指向的是一个绑定了业务类的代理对象，所以通过接口方法名调用的是被代理的方法！**

##### 动态代理总结
静态代理有的它都有，静态代理没有的，它也有

 - ...
 - 一个动态代理类，一般代理某一类业务
 - 一个动态代理可以代理多个类，**代理的是接口**！