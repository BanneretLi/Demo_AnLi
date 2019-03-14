package com.example.a51044.myap;



import com.example.a51044.myap.mvvm.MyCallBack;
import com.example.a51044.myap.mvvm.MyInterface;
import com.example.a51044.myap.mvvm.RetrofitUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import rx.Subscriber;


/**
 * author: ${车文飞}
 * date: 2019/3/14 11:26
 * description:
 */
public class ViewModel2 implements MyInterface.Model {

    private MyCallBack callBack;

    public ViewModel2(MyCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void getData(String url, HashMap<String, Object> head, HashMap<String, Object> map, Class kind, MyInterface.MyCallBack myCallBack) {

    }

    @Override
    public void postData(String url, HashMap<String, Object> head, HashMap<String, Object> map, final Class kind) {
        final Gson gson = new Gson();
        RetrofitUtils.getInstance().post(url, head, map, new RetrofitUtils.HttpListtener() {
            @Override
            public void OnSuccess(String jsonStr) {
                Object o = gson.fromJson(jsonStr, kind);
                callBack.sucess(o);
            }

            @Override
            public void OnError(String error) {
               callBack.error(error);
            }
        });
    }

    @Override
    public void postHeader(String url, HashMap<String, Object> header, String path, Class kind, MyInterface.MyCallBack myCallBack) {

    }

}
