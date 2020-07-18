package com.huang.adapter;

public class Adapter extends PowerSupply220V implements Target{
    //期待的插头要调用convert_110v方法，但原插头没有
    //所以使用Adapter来实现这个方法
    //实际上convert_110v()只是调用原有插头的output_220v()方法而已
    //Adapter只是将output_220v()做了一层封装，封装成Target可以调用的convert_110v()而已
    @Override
    public void convert_110v() {
        output_220v();
    }
}
