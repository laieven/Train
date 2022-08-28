package com.lbj.train.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {

    private final OkHttpClient okHttpClient;
    private static OkHttpUtil okHttpUtil;

    public OkHttpUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        //通过build获取实例
        okHttpClient = builder.build();
    }

    //获取实例
    public static synchronized OkHttpUtil getInstance(){
        if (okHttpUtil == null){
            okHttpUtil = new OkHttpUtil();
        }
        return okHttpUtil;
    }


    //获取数据
    public Response getData(String url){
        Request request = new Request.Builder()
                .get().url(url).build();

        Call call = okHttpClient.newCall(request);

        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
