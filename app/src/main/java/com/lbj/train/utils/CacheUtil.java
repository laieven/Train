package com.lbj.train.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lbj.train.constants.MyConstants;
import com.lbj.train.model.TimeModel;


import java.util.ArrayList;
import java.util.List;

//缓存
public class CacheUtil {
    private static CacheUtil mCache;
    private SharedPreferences sharedPreferences;
    public CacheUtil(Context context) {
        context.getSharedPreferences(MyConstants.CACHE_FILE_NAME, Context.MODE_PRIVATE);
    }

    //向外暴露接口，能够获得一个实例对象
    public static synchronized CacheUtil getCache(Context context){
        if (mCache == null){
            mCache = new CacheUtil(context);
        }
        return mCache;
    }

    //写入
    public void write(List<TimeModel> timeModels){
        if (timeModels == null){
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(timeModels);
        editor.putString(MyConstants.CACHE_FILE_NAME, json);
        editor.apply();
    }

    //缓存读取
    public List<TimeModel> read(){
        String string = sharedPreferences.getString(MyConstants.CACHE_FILE_NAME, null);
        if (TextUtils.isEmpty(string)){
            return new ArrayList<>();
        }
        Gson gson = new Gson();

//        https://blog.csdn.net/jiangyu1013/article/details/56489412
        return gson.fromJson(string, new TypeToken<List<TimeModel>>(){}.getType());
    }

}
