package com.huang.proxy.staticproxy.rent;

public class Client {
    public static void main(String[] args) {
        // 房东要租房
        Host host = new Host();

        // 中介帮房东
        Proxy proxy = new Proxy(host);

        // 客户去找中介
        proxy.rent();
    }
}
