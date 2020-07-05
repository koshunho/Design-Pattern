package com.huang.factory.method;

public class Consumer {
    public static void main(String[] args) {
        Car wuling = new WulingFactory().getCar();

        Car tesla = new TeslaFactory().getCar();

        wuling.name();
        tesla.name();

        Car mitsubishi = new MitsubishiFactory().getCar();
        mitsubishi.name();
    }
}
