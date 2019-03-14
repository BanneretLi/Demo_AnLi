package com.example.a51044.myap.mvvm;

/**
 * author: ${张渊卓}
 * date: 2019/3/14 9:03
 * description:
 */
public interface MyCallBack<T> {
    void sucess(T data);
    void error(String error);
}
