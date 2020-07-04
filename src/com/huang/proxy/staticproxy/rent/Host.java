package com.huang.proxy.staticproxy.rent;
// 真实角色
public class Host implements Rent{

    @Override
    public void rent() {
        System.out.println("房屋出租");
    }
}
