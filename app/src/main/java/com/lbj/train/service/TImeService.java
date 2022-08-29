package com.lbj.train.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.Nullable;

import com.lbj.train.constants.MyConstants;
import com.lbj.train.model.TimeModel;
import com.lbj.train.utils.CacheUtil;

import java.util.List;

public class TImeService extends Service {


    private CacheUtil mCache;

    @Override
    public void onCreate() {
        super.onCreate();
        //此时需要先获取一下缓存的
        mCache = CacheUtil.getCache(this);
        handleData();
    }

    //分别从缓存和网络中获取数据
    private void handleData() {

        //从缓存中获取
        new Thread(new Runnable() {
            @Override
            public void run() {
//                读取处缓存的内容
                List<TimeModel> timeModels = mCache.read();
                //判断缓存是否有内容
                if (timeModels.size() == 0){
                    return;
                }
                sendTimeChange();
            }
        }).start();

        //从网络中获取


    }


    //看是否改变了消息，不管是缓存还是网络，只要有数据通知过来都要发送消息
    //因为主线程没有办法完成
    private void sendTimeChange(){
        Message message = Message.obtain();
        message.arg1();
        message.what = MyConstants.MSG_TIME_CHANGE;//
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
