package com.huang.proxy.staticproxy.crud;

public class Client {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        UserServiceProxy proxy = new UserServiceProxy();

        proxy.setUserService(userService);

        proxy.add();
    }
}
