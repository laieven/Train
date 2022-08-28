package com.lbj.train.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.lbj.train.utils.CacheUtil;

public class TImeService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        handleData();
    }

    //分别从缓存和网络中获取数据
    private void handleData() {

        //从缓存中获取
        new Thread(new Runnable() {
            @Override
            public void run() {
//                CacheUtil.
            }
        }).start();

        //从网络中获取


    }


    //看是否改变了消息
    private void sendTimeChange(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new InnerBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    class InnerBinder extends Binder{
        public void setUiHandler(Handler uiHandler){

        }
    }
}
