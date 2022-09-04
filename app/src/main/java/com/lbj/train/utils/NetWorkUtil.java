package com.lbj.train.utils;

import com.google.gson.Gson;
import com.lbj.train.constants.MyConstants;
import com.lbj.train.model.TimeModel;
import com.lbj.train.result.TimeRes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

//网络
public class NetWorkUtil {
    private static NetWorkUtil netWorkUtil;

    private String ip;
    private int port;
    private String url;
    private String responseStr;

    public NetWorkUtil() {
        //初始化网络地址、端口号以及url
        ip = MyConstants.IP;
        port = MyConstants.PORT;
        url = MyConstants.RANDOM_TIME_URL;
    }

    public synchronized static NetWorkUtil getNet(){
        if (netWorkUtil == null){
            netWorkUtil = new NetWorkUtil();
        }
        return netWorkUtil;
    }


    //获取所有的数据
    public List<TimeModel> getAllTime(){
        List<TimeModel> timeModelsRes = new ArrayList<>();
        String connectUrl = "http://" + ip + ":" + port + "/" + url;
        //使用okhttp获得数据
        Response response = OkHttpUtil.getInstance().getData(connectUrl);
        if (response == null || response.body() == null){
            return timeModelsRes;
        }
        try {
            responseStr = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //得到响应体
        TimeRes resList = new Gson().fromJson(responseStr, (Type) TimeRes.class);



        if (resList.getCode() == 200){
            //此时需要将获得的所有数据添加到结果中
            timeModelsRes.addAll(resList.getData());

        }else{
            //没有获取到数据
        }
        return timeModelsRes;

    }
}
