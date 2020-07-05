package com.huang.factory.simple;

public class CarFactory {
    public static Car getCar(String car){
        if(car.equals("Wuling")){
            return new Wuling();
        }else if(car.equals("Tesla")){
            return new Tesla();
        }else{
            return null;
        }
    }
}
