package com.lbj.train;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.lbj.train.adapters.MyAdapter;
import com.lbj.train.constants.MyConstants;
import com.lbj.train.interfaces.OnRecyclerViewItemClickListener;
import com.lbj.train.model.TimeModel;
import com.lbj.train.provider.TimeProvider;
import com.lbj.train.service.TimeService;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private OnClickListener mOnTimeItemClickListener;
    private MyAdapter mMyAdapter;
    private TimeServiceConnection mTimeServiceConnection;
    private Handler mUiHandler;
    private TimeService.InnerBinder myBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        // 对服务进行绑定
        handleBind();
    }

    private void handleBind() {
        mTimeServiceConnection = new TimeServiceConnection();
        Intent intent = new Intent(this, TimeService.class);
        bindService(intent, mTimeServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
        mMyAdapter.removeOnRecyclerViewItemClickListener();
        unbindService(mTimeServiceConnection);
        mTimeServiceConnection = null;
    }

    //1. 找控件，recyclerView
    //2. 设置adapter
    private void initView() {
        Log.i(TAG, "initView: ");
        //找控件
        mRecyclerView = this.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        //设置adapter
        List<TimeModel> timeModels = TimeProvider.getInstance().getTimeAll();
        mMyAdapter = new MyAdapter(timeModels);
        mRecyclerView.setAdapter(mMyAdapter);
        mOnTimeItemClickListener = new OnClickListener();
        mMyAdapter.setOnRecyclerViewItemClickListener(mOnTimeItemClickListener);


        //设置Handler
        mUiHandler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage: ");
                super.handleMessage(msg);
                switch (msg.what){
                    case MyConstants.MSG_TIME_CHANGE:
                        switch (msg.arg1){
                            case MyConstants.TIME_FROM_CACHE:
                                Toast.makeText(MainActivity.this,"收到缓存数据",Toast.LENGTH_SHORT).show();
                                break;
                            case MyConstants.TIME_FROM_NET:
                                Toast.makeText(MainActivity.this,"收到网络数据",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        mMyAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    private void setUiHandler(){
        myBinder.setUiHandler(mUiHandler);
    }

    //因为需要进行在后台运行，因此需要一个serviceConnection进行连接
    class TimeServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: ");
            myBinder = (TimeService.InnerBinder) service;
            //子线程需要sendMessage
            setUiHandler();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myBinder = null;
        }
    }


    public class OnClickListener implements OnRecyclerViewItemClickListener{

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(MainActivity.this, TimeShowActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }
}
