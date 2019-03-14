package com.example.a51044.myap.mvvm;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/1810:29<p>
 * <p>更改时间：2019/1/1810:29<p>
 * <p>版本号：1<p>
 */
public class RetrofitUtils {
    private MyApiService myApiService;

    private RetrofitUtils() {
        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //设置日志拦截器的等级
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                //当网络请求失败的时候，等到网络正常自动加载
                .retryOnConnectionFailure(true).build();


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Contact.BASE_URL)
                .client(okHttpClient)
                .build();

        myApiService = retrofit.create(MyApiService.class);

    }

    public static RetrofitUtils getInstance() {
        return RetroHolder.retro;
    }

    private static class RetroHolder {
        private static final RetrofitUtils retro = new RetrofitUtils();
    }


    //get请求
    public void get(String url, HashMap<String, Object> head, HashMap<String, Object> map, final HttpListtener httpListtener) {
        Observer observer = new Observer<ResponseBody>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (httpListtener != null) {
                    Log.e("zzz", "onError: " + e.getMessage());
                    httpListtener.OnError(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (httpListtener != null) {
                    try {
                        httpListtener.OnSuccess(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        //请求网络放在子线程
        myApiService.get(url, head, map).subscribeOn(Schedulers.io())
                //成功后回调到主线程（observeOn）是观察者
                .observeOn(AndroidSchedulers.mainThread())
                //订阅
                .subscribe(observer);
    }

    //post请求
    public void post(String url, HashMap<String, Object> head, HashMap<String, Object> map, final HttpListtener httpListtener) {
        Observer observer = new Observer<ResponseBody>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (httpListtener != null) {
                    httpListtener.OnError(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (httpListtener != null) {
                    try {
                        httpListtener.OnSuccess(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        if (map == null) {
            map = new HashMap<>();
        }
        myApiService.post(url, head, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    //图片请求
    public void postHeader(String url, HashMap<String, Object> header, String path, final HttpListtener okHttpFace) {
        if (header == null) {
            header = new HashMap<>();
        }
        Observer<ResponseBody> observer = new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (okHttpFace != null) {
                    okHttpFace.OnError(e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (okHttpFace != null) {
                    try {
                        okHttpFace.OnSuccess(responseBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/octet-stream"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("image", file.getName(), requestBody);
        myApiService.postHeader(url, header, builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }



    //自定义接口
    public interface HttpListtener {
        void OnSuccess(String jsonStr);

        void OnError(String error);
    }

}
