package com.lbj.train.provider;

import com.lbj.train.beans.Time;
import com.lbj.train.model.TimeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class TimeProvider {

    private static TimeProvider mInstance;
    private List<TimeModel> mTimeModelList;

    public TimeProvider() {
        mTimeModelList = new ArrayList<>();
        mTimeModelList.add(new TimeModel("暂无数据",0,0));
    }

    public static synchronized TimeProvider getInstance(){
        if (mInstance == null){
            mInstance = new TimeProvider();
        }
        return mInstance;
    }

    public List<TimeModel> getTimeAll(){
        return mTimeModelList;
    }

    //需要获取某一行的数据
    public TimeModel getLineData(int position){
        return mTimeModelList.get(position);
    }

    public void setTimeModelList(List<TimeModel> timeModels){
        mTimeModelList.clear();
        //对当前传入的数据进行排序
        TreeSet<TimeModel> treeSet = new TreeSet<>(timeModels);
        mTimeModelList.addAll(treeSet);
    }




}
