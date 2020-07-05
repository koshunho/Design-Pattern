package com.huang.factory.simple;

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
