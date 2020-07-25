package com.huang.singleton;

// 枚举示例
public enum Cities {
    TOKYO(1,"東京"),FUKUOKA(2,"福岡"),OSAKA(3,"大阪");

    private int index;

    private String msg;

    Cities(int index, String msg) {
        this.index = index;
        this.msg = msg;
    }

    public int getIndex() {
        return index;
    }

    public String getMsg() {
        return msg;
    }

    public static void main(String[] args) {
        Cities[] values = Cities.values();

        for (Cities city : values) {
            System.out.println(city.getIndex() + "->" + city.getMsg());
        }
    }
}
