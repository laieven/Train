package com.lbj.train.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lbj.train.constants.MyConstants;
import com.lbj.train.model.TimeModel;
import com.lbj.train.provider.TimeProvider;
import com.lbj.train.utils.CacheUtil;
import com.lbj.train.utils.NetWorkUtil;

import java.util.List;

public class TimeService extends Service {

    private static final String TAG = "TimeService";
    private Handler mUiHandler;
    private CacheUtil mCache;

    @Override
    public void onCreate() {
        Log.i(TAG, "service onCreate: ");
        super.onCreate();
        mCache = CacheUtil.getCache(this);
        //此时需要先获取一下缓存的
        getData();
    }

    //分别从缓存和网络中获取数据
    private void getData() {


        //从缓存中获取
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                读取处缓存的内容
                List<TimeModel> timeModels = mCache.read();
                //判断缓存是否有内容
                if (timeModels.size() == 0){
                    return;
                }
                TimeProvider.getInstance().setTimeModelList(timeModels);
                //查到了缓存有消息，那么进行发送
                sendTimeChange(MyConstants.TIME_FROM_CACHE);
            }
        }, "Cache").start();

        //从网络中获取
        new Thread(new Runnable() {
            @Override
            public void run() {
                //sleep的原因是防止回传数据之后还未bind
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<TimeModel> allTime = NetWorkUtil.getNet().getAllTime();
                if (allTime == null || allTime.size() == 0){
                    return;
                }
                TimeProvider.getInstance().setTimeModelList(allTime);
                sendTimeChange(MyConstants.TIME_FROM_NET);
                //同时将数据进行缓存
                mCache.write(allTime);
            }
        }, "Net").start();

    }


    //看是否改变了消息，不管是缓存还是网络，只要有数据通知过来都要发送消息
    //因为主线程没有办法完成耗时任务，只能交给子线程去完成
    private void sendTimeChange(int type){
        Message message = mUiHandler.obtainMessage();
        message.arg1 = type;
        message.what = MyConstants.MSG_TIME_CHANGE;//识别子线程发来的是什么消息
        mUiHandler.sendMessage(message);//发送消息
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

    //Binder的主要作用就是去设置UI
    public class InnerBinder extends Binder{
        public void setUiHandler(Handler uiHandler){
            mUiHandler = uiHandler;
        }
    }
}
