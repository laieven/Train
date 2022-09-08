package com.lbj.train.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.lbj.train.constants.MyConstants;
import com.lbj.train.model.TimeModel;
import com.lbj.train.utils.SharedPreferencesUtil;


import java.util.List;

//缓存
public class Cache {
    private static Cache mCache;
    private SharedPreferences sharedPreferences;
    private SharedPreferencesUtil mSharedPreferencesUtil;
    public Cache(Context context) {
        sharedPreferences = context.getSharedPreferences(MyConstants.CACHE_FILE_NAME, Context.MODE_PRIVATE);
        mSharedPreferencesUtil = new SharedPreferencesUtil(context);
    }

    //向外暴露接口，能够获得一个实例对象
    public static synchronized Cache getCache(Context context){
        if (mCache == null){
            mCache = new Cache(context);
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
