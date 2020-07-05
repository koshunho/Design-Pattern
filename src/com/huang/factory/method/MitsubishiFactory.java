package com.huang.factory.method;

public class MitsubishiFactory implements CarFactory{
    @Override
    public Car getCar() {
        return new Mitsubishi();
    }
}
