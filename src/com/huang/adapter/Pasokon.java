package com.huang.adapter;

//パソコン
public class Pasokon {

    private Target power_110v;

    Pasokon(Target power_110v){
        this.power_110v = power_110v;
        System.out.println("このパソコンは正常に動作しています。");
    }

    public static void main(String[] args) {
        Target adapter = new Adapter();

        Pasokon pasokon = new Pasokon(adapter);
    }
}

