package com.huang.proxy.dynamicproxy.rent;

public class Host implements Rent{

    @Override
    public void rent() {
        System.out.println("房屋出租");
    }
}
