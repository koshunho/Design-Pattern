package com.huang.factory.abstractfactory;

// 抽象产品工厂
public interface ProductFactory {
    //生产手机
    PhoneProduct phoneProduct();

    //生产路由器
    RouterProduct routerProduct();
}
