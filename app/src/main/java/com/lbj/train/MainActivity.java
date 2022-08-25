package com.lbj.train;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lbj.train.adapters.MyAdapter;
import com.lbj.train.beans.Time;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<Time> timeList = new ArrayList<Time>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定控件
        initView();
        //准备数据
        initData();
        //创建适配器，作为连接数据源和控件的桥梁
        MyAdapter myAdapter = new MyAdapter(this,R.layout.date_item, timeList);

        mListView.setAdapter(myAdapter);

        //设置点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Time time = timeList.get(position);
                Intent intent = new Intent(MainActivity.this, DateShowActivity.class);
//                intent.putExtra("hour", hour);
//                intent.putExtra("minutes", minutes);
                startActivity(intent);
            }
        });

    }

    /**
     * 从网络中获取
     */
    private void initData() {
        for(int i = 0; i < 10; i++){
            timeList.add(new Time(i + "时",  i + "分钟"));
        }
    }

    private void initView() {
        mListView = this.findViewById(R.id.list_view);
    }
}
