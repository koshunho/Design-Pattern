package com.huang.factory.abstractfactory;

public class Consumer {
    public static void main(String[] args) {
        System.out.println("==============小米系列产品==============");
        //小米工厂
        XiaomiFactory xiaomiFactory = new XiaomiFactory();

        PhoneProduct xiaomiPhone = xiaomiFactory.phoneProduct();
        xiaomiPhone.call();
        xiaomiPhone.shutdown();

        RouterProduct xiaomiRouter = xiaomiFactory.routerProduct();
        xiaomiRouter.setting();
        xiaomiRouter.WiFi();

        System.out.println("==============华为系列产品==============");
        //华为工厂
        HuaweiFactory huaweiFactory = new HuaweiFactory();

        PhoneProduct huaweiProduct = huaweiFactory.phoneProduct();
        huaweiProduct.call();
        huaweiProduct.SMS();

        RouterProduct huaweiRouter = huaweiFactory.routerProduct();
        huaweiRouter.WiFi();
        huaweiRouter.setting();
    }
}
