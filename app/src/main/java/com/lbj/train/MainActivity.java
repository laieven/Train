package com.lbj.train;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.lbj.train.beans.Time;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter ;

    List<Time> mTimeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        // 网络中获取数据
        for (int i = 0; i < 50; i++) {
            Time time = new Time();
            time.setMinute("小时" + i);
            time.setHour("分钟" + i);
            mTimeList.add(time);
        }

        //设置适配器
        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);

        //使用线性管理器进行管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

        //加上水平线
        DividerItemDecoration mDivider = new
                DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mDivider);
        initListener();
    }

    //设置监听器
    private void initListener() {
    }


    /**
     * 适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        private AdapterView.OnItemClickListener mOnItemClickListener;

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(MainActivity.this, R.layout.item_list, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            Time times = mTimeList.get(position);
//            News news = mNewsList.get(position);
            holder.mTitleTv.setText(times.getHour());
            holder.mTitleContent.setText(times.getMinute());
        }

        @Override
        public int getItemCount() {
            return mTimeList.size();
        }

        public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
            //设置一个监听,其实,就是要设置一个接口,一个回调的接口
            this.mOnItemClickListener = listener;
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mTitleTv;
        TextView mTitleContent;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.textView);
            mTitleContent = itemView.findViewById(R.id.textView2);
        }
    }

}
