package com.lbj.train;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.lbj.train.adapters.MyAdapter;
import com.lbj.train.interfaces.OnRecyclerViewItemClickListener;
import com.lbj.train.model.TimeModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private OnClickListener mOnTimeItemClickListener;
    private MyAdapter mMyAdapter;
    private TimeServiceConnection mTimeServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //service绑定
        mTimeServiceConnection = new TimeServiceConnection();
        Intent intent = new Intent(MainActivity.this, TimeShowActivity.class);
        bindService(intent, mTimeServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyAdapter.removeOnRecyclerViewItemClickListener();
        unbindService(mTimeServiceConnection);
        mTimeServiceConnection = null;
    }

    //1. 找控件，recyclerView
    //2. 设置adapter
    //3. 设置相关动画
    private void initView() {
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
    }

    //因为需要进行在后台运行，因此需要一个serviceConnection进行连接
    class TimeServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }


    class OnClickListener implements OnRecyclerViewItemClickListener{

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(MainActivity.this, TimeShowActivity.class);
            intent.putExtra("key", position);
            startActivity(intent);
        }
    }
}
