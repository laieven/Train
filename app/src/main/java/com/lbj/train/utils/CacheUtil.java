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
    private SharedPreferencesUtil mSharedPreferencesUtil;
    public CacheUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(MyConstants.CACHE_FILE_NAME, Context.MODE_PRIVATE);
        mSharedPreferencesUtil = new SharedPreferencesUtil(context);
    }

    //向外暴露接口，能够获得一个实例对象
    public static synchronized CacheUtil getCache(Context context){
        if (mCache == null){
            mCache = new CacheUtil(context);
        }
        return mCache;
    }

    //写入
    public void save(List<TimeModel> timeModels){
        mSharedPreferencesUtil.save(timeModels, mCache.sharedPreferences);
    }

    //缓存读取
    public List<TimeModel> read(){
        return mSharedPreferencesUtil.read(mCache.sharedPreferences);
    }

}
