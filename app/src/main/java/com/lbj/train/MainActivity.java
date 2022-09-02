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
    //3. 设置相关动画
    private void initView() {
        Log.i(TAG, "initView: ");
        //找控件
        mRecyclerView = this.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        //设置adapter
        List<TimeModel> timeModels = null;
        mMyAdapter = new MyAdapter(timeModels);
        mOnTimeItemClickListener = new OnClickListener();
        mMyAdapter.setOnRecyclerViewItemClickListener(mOnTimeItemClickListener);
        mRecyclerView.setAdapter(mMyAdapter);




        //设置动画
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.alpha_anim);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        layoutAnimationController.setOrder(layoutAnimationController.ORDER_NORMAL);
        layoutAnimationController.setDelay(0.3f);
        mRecyclerView.setLayoutAnimation(layoutAnimationController);

        //设置Handler
        mUiHandler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.i(TAG, "handleMessage: ");
                super.handleMessage(msg);
                //判断消息的来源
                if (msg.what == MyConstants.TIME_FROM_CACHE){
                    //来自缓存的消息
                    Toast.makeText(MainActivity.this, "收到来自缓存的消息", Toast.LENGTH_SHORT).show();
                } else if (msg.what == MyConstants.TIME_FROM_NET){
                    //来自网络的消息
                    Toast.makeText(MainActivity.this, "收到来自网络消息的消息", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "数据已经更新", Toast.LENGTH_SHORT).show();
                }
                mMyAdapter.notifyDataSetChanged();
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
            intent.putExtra("key", position);
            startActivity(intent);
        }
    }
}
