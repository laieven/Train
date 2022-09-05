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

public class SharedPreferencesUtil {
    private Context mContext;

    public SharedPreferencesUtil() {
    }

    public SharedPreferencesUtil(Context mContext) {
        this.mContext = mContext;
    }


    //写入
    public void save(List<TimeModel> timeModels, SharedPreferences sharedPreferences){
        if (timeModels == null){
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(timeModels);
        editor.putString(MyConstants.CACHE_FILE_NAME, json);
        editor.apply();
    }

    //缓存读取
    public List<TimeModel> read(SharedPreferences sharedPreferences){
        String string = sharedPreferences.getString(MyConstants.CACHE_FILE_NAME, null);
        if (TextUtils.isEmpty(string)){
            return new ArrayList<>();
        }
        Gson gson = new Gson();

//        https://blog.csdn.net/jiangyu1013/article/details/56489412
        return gson.fromJson(string, new TypeToken<List<TimeModel>>(){}.getType());
    }
}
