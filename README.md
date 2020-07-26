# Design-Pattern
Some examples to help understanding design pattern

* [Design-Pattern](#design-pattern)
	* [代理模式](#代理模式)
	* [工厂模式](#工厂模式)
	* [单例模式](#单例模式)

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

## 工厂模式

**作用：实现了创建者和调用者的分离**

![产品族和产品等级](http://qcorkht4q.bkt.clouddn.com/blog抽象工厂_(1).png)

分类：

 - 简单工厂模式
    用来生产同一等级结构中的任意产品（对于增加新的产品，需要扩展已有代码）
 - 工厂方法模式
   用来生产同一登记结构中的固定产品（支持增加任意产品）
 - 抽象工厂模式
  围绕一个超级工厂创建其他工厂。该超级工厂又称为其他工厂的工厂。
  
OOP原则：
 - 开闭原则：一个软件的实体应对扩展开放，对修改关闭
 - 依赖倒转原则：只针对接口编程，不要针对实现编程

核心本质：
- **实例化对象不适用new，用工厂方法代替**
- 将选择实现类，创建对象统一管理和控制。从而将调用者跟我们的实现类解耦

#### 简单工厂
![静态工厂](http://qcorkht4q.bkt.clouddn.com/blog静态工厂1.png)
```java
public interface Car {
    void name();
}
```

```java
public class Wuling implements Car{
    @Override
    public void name() {
        System.out.println("柳州五菱");
    }
}
```
```java
public class Tesla implements Car{
    @Override
    public void name() {
        System.out.println("特斯拉");
    }
}
```
```java
public class CarFactory {
    public static Car getCar(String car){
        if(car.equals("Wuling")){
            return new Wuling();
        }else if(car.equals("Tesla")){
            return new Tesla();
        }else{
            return null;
        }
    }
}

```
```java
public class Consumer {
    public static void main(String[] args) {
        // Car car1 = new Wuling();
        // Car car2 = new Tesla();

        //使用工厂创建：假设我们创建一个 Wuling，需要很多很多参数，直接new的时候要写大量参数是非常累的。
        //写一个工厂类，里面就写好参数了，我们需要就直接调用这个工厂就好了(不需要考虑是怎么生产出来的)
        Car car1 = CarFactory.getCar("Wuling");
        Car car2 = CarFactory.getCar("Tesla");

        car1.name();
        car2.name();
    }
}
```
这种简单工厂的写法是通过switch-case来判断对象创建过程的。

假设我们现在增加另一个款式的车，也实现了Car接口。但是这样我们就需要在CarFactory中修改代码了，违反了开闭原则。

#### 工厂方法
![工厂方法](http://qcorkht4q.bkt.clouddn.com/blog工厂方法1.png)
 
 为每种Car都实现了对应的CarFactory。
 
 工厂方法在简单工厂的基础上再包了一层工厂，所有的工厂都是此工厂的子类。
 #### 抽象工厂
 定义：抽象工厂模式提供了一个创建一系列相关或者相互依赖对象的接口，无需指定他们具体的类
 

 适用场景：
 - 客户端（应用层）不依赖产品类实例如何被创建、实现等细节
 - 强调一系列相关的产品对象（属于同一产品族--> **简单理解，是同一个厂商的生产的不同种类产品**）一起使用创建对象需要大量的重复代码
 - 提供一个产品类的库，所有的产品以同样的接口出现，从而使得客户端不依赖具体的实现

优点：
- 具体产品在应用层的代码隔离，无需关心创建的细节
- 将一个系列的产品统一到一起创建

缺点：
- 规定了所有可能被创建的产品集合，**产品族中扩展新的产品困难**
- 增加了系统的抽象性和理解难度

![抽象工厂](http://qcorkht4q.bkt.clouddn.com/blog抽象工厂2.png)


简单理解 --> 如果需要的产品是固定的，稳定的，就很强大，有效率。但是如果我在后期需要增加产品，比如要生产笔记本了，如果在ProductFactory中加一个笔记本，所有厂商都要实现这个接口方法。

抽象工厂就是工厂的工厂。

#### 总结

 - 简单工厂模式
   虽然某种程度上不符合设计原则，但实际使用最多！
 - 工厂方法模式
   不修改已有类的前提下，通过增加新的工厂类实现扩展。
 - 抽象工厂模式
   不可以增加产品，可以增加产品族！
   
应用场景：
 - JDBC中的Connection对象的获取
 - Sping中IOC容器创建管理bean对象
 - 反射中Class对象的newInstance方法

 ## 适配者模式
 **作用：由于接口不兼容而不能一起工作的那些类可以在一起工作**

小黄在日本买了一个电器，但是日本电压是110V，中国电压是220V，拿到中国用不了。

所以需要一个Adapter将220V转换成110V

1. 创建Target接口（期待得到的插头）：能输出110V（将220V转换成110V）
```java
// Target接口：期望得到的插头 能把220V转换成110V
public interface Target {
    void convert_110v();
}
```

2.  创建源类（原有的插头）
```java
//源类（电源 只能输出220V的电压）
public class PowerSupply220V {
    public void output_220v(){}
}

```
3. 创建适配器类（Adapter）
```java
public class Adapter extends PowerSupply220V implements Target{
    //期待的插头要调用convert_110v方法，但原插头没有
    //所以使用Adapter来实现这个方法
    //实际上convert_110v()只是调用原有插头的output_220v()方法而已
    //Adapter只是将output_220v()做了一层封装，封装成Target可以调用的convert_110v()而已
    @Override
    public void convert_110v() {
        output_220v();
    }
}
```
4. 具体使用目标类，并通过Adapter类调用所需要的方法从而实现目标（不需要通过原有插头）
```java
//パソコン
public class Pasokon {

    private Target power_110v;

    Pasokon(Target power_110v){
        this.power_110v = power_110v;
        System.out.println("このパソコンは正常に動作しています。");
    }

    public static void main(String[] args) {
        Target adapter = new Adapter();

        Pasokon pasokon = new Pasokon(adapter);
    }
}
```

优点：系统需要使用现有的类，而此类的接口不符合系统的需要。那么通过适配器模式就可以让这些功能得到更好的复用。


缺点：过多的使用适配器，会让系统非常零乱，不易整体进行把握

## 单例模式
设计模式王中王。直接说最常见的Double-Checked Locking

核心：
- 单例模式唯一实例必须volatile static;
- getInstance()检验两次对象是否存在。

1. 为什么单例模式唯一实例为什么必须为静态?

常见的单例模式都是通过一个静态方法（如getInstance)返回其单例。 `DCL.getInstance()`

程序调用类中方法只有两种方式，
①创建类的一个对象，用该对象去调用类中方法；
②使用类名直接调用类中方法，格式“类名.方法名()”

在单例模式中，构造器私有化了，不能直接new 一个对象，第一种方法不能用。只能使用第二种方法。

使用类名直接调用类中方法，类中方法必须是静态的，而静态方法不能访问非静态成员变量，因此类自定义的实例变量也必须是静态的。

2. 为什么要加volatile?
   
防止指令重排
```java
public class DCL {

    private volatile static DCL dcl;

    private DCL(){
        System.out.println(Thread.currentThread().getName() + "OK");
    }

    // 常见的单例模式都是通过一个静态方法（如getInstance)返回其单例。 DCL.getInstance()
    // 为什么不直接锁方法（直接在这个方法前加synchronized）？
    // 直接锁方法，每次都需要拿锁，效率太低。
    // 这样可以控制颗粒度，如果已有对象，就无须再进入同步代码块！
    /*插一句：
    synchronized加在一个类的普通方法上，那么相当于synchronized(this)
    如果synchronized加载一个类的静态方法上，那么相当于synchronized(Class对象)。*/
    public static DCL getInstance(){
        if(dcl == null){
            synchronized (DCL.class){
                if(dcl == null){
                    dcl = new DCL();
                    /*dcl = new DCL() 不是原子性操作，至少会经过三个步骤：
                    1. 分配对象内存空间
                    2. 执行构造方法初始化对象
                    3. 设置instance指向刚分配的内存地址，此时instance ！=null；
                    可能执行顺序为 1->2->3，或者1->3->2
                    由于指令重排，导致A线程执行 dcl = new DCL();的时候，可能先执行了第三步（还没执行第二步），
                    此时线程B又进来了，发现dcl已经不为空了，直接返回了dcl，并且后面使用了返回的dcl
                    由于线程A还没有执行第二步，导致此时dcl还不完整，可能会有一些意想不到的错误。

                    -->简而言之，就是会在第一次判断为!null时，就直接返回一个未初始化完成的对象。
                    所以要加volatile防止指令重排
                    */
                }
            }
        }
        return dcl;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            new Thread(()->{
                DCL.getInstance();
            }).start();
        }
    }
}
```

但是有弊端，反射可以破坏单例模式
```java
public class TestReflection {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        DCL instance1 = DCL.getInstance();

        Constructor<DCL> constructor = DCL.class.getDeclaredConstructor(null);

        constructor.setAccessible(true);

        DCL instance2 = constructor.newInstance();

        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
        System.out.println(instance1 == instance2);
    }
}

```

完美实现单例模式的只有枚举`enum`。枚举的单例无法被破坏
```java
public enum EnumSingleton {
    INSTANCE;

    // 这个方法可以不写
    public EnumSingleton getInstance(){
        return INSTANCE;
    }

    public static void main(String[] args) {
        EnumSingleton instance1 = EnumSingleton.INSTANCE;
        EnumSingleton instance2 = EnumSingleton.INSTANCE;

        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
        System.out.println(instance1 == instance2);
    }
}
```