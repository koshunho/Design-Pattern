package com.huang.proxy.staticproxy.rent;

// 代理角色
public class Proxy implements Rent{

    //代理类相当于增加了真实角色。所以把真实角色作为成员变量
    private Host host;

    public Proxy(Host host){
        this.host = host;
    }

    @Override
    public void rent() {
        seeHouse();
        host.rent();
        fare();
    }

    // 租房的附加动作
    public void seeHouse(){
        System.out.println("带房客看房");
    }

    // 租房的附加动作
    public void fare(){
        System.out.println("收钱");
    }
}
