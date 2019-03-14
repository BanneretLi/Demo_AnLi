package com.example.a51044.myap.mvvm;

import java.util.HashMap;
import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2320:05<p>
 * <p>更改时间：2019/1/2320:05<p>
 * <p>版本号：1<p>
 */
public interface MyInterface {

    interface MyCallBack<T> {
        void onSuccess(T data);

        void onError(String error);
    }

    interface Model {
        void getData(String url, HashMap<String, Object> head, HashMap<String, Object> map, Class kind, MyCallBack myCallBack);
        void postData(String url, HashMap<String, Object> head, HashMap<String, Object> map, Class kind);
        void postHeader(String url, HashMap<String, Object> header, String path, Class kind, MyCallBack myCallBack);
    }

    interface MyView<T> {
        void onRequestOk(T data);

        void onRequestNo(String error);
    }

    interface Presenter {
        void startRequest(String url, HashMap<String, Object> head, HashMap<String, Object> map, Class kind);
        void postData(String url, HashMap<String, Object> head, HashMap<String, Object> map, Class kind);
        void postHeader(String url, HashMap<String, Object> header, String path, Class kind);
    }

    interface PermissionListener {
        //        动态获取权限成功
        void granted();
        //        失败
        void denied(List<String> deniedList);
    }
}
