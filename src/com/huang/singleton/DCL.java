package com.huang.singleton;

// 核心：
// 单例模式唯一实例必须volatile static;
// getInstance()检验两次对象是否存在。
public class DCL {
    // 1.为什么单例模式唯一实例为什么必须为静态?
    // 程序调用类中方法只有两种方式，①创建类的一个对象，用该对象去调用类中方法；②使用类名直接调用类中方法，格式“类名.方法名()”
    // 在单例模式中，构造器私有化了，不能直接new 一个对象，第一种方法不能用。只能使用第二种方法
    // 使用类名直接调用类中方法，类中方法必须是静态的，而静态方法不能访问非静态成员变量，因此类自定义的实例变量也必须是静态的。
    // 2.为什么要加volatile?
    //防止指令重排
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
