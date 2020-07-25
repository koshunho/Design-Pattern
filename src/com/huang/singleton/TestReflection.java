package com.huang.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
