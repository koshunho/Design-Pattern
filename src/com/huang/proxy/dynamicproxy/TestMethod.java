package com.huang.proxy.dynamicproxy;

import com.huang.proxy.dynamicproxy.rent.Host;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestMethod {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 返回所有方法
        Method[] methods1 = System.class.getDeclaredMethods();
        System.out.println("System getDeclaredMethods 清单（数量 = " + methods1.length + "）：");
        for (Method m : methods1) {
            System.out.println(m);
        }

        System.out.println();

        // 返回所有 public 方法
        Method[] methods2 = System.class.getMethods();
        System.out.println("System getMethods 清单（数量 = " + methods2.length + "）：");
        for (Method m : methods2) {
            System.out.println(m);
        }

        System.out.println();

        // 利用 Method 的 invoke 方法调用 System.currentTimeMillis()
        Method method = System.class.getMethod("currentTimeMillis");
        System.out.println(method);
        System.out.println(method.invoke(null));

        System.out.println();


        //！！！！注意了！！！ 因为Host的rent()方法是实例方法，在用invoke调用的时候必须传入一个实例进去，不然提示空指针
        //如果是一个静态方法的话，传入一个null参数就可以了
        Method[] methods3 = Host.class.getMethods();
        for(Method m : methods3){
            System.out.println(m);
        }
        Host.class.getMethod("rent").invoke(new Host());
    }
}
