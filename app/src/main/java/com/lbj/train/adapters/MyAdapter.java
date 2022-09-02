package com.lbj.train.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lbj.train.R;
import com.lbj.train.interfaces.OnRecyclerViewItemClickListener;
import com.lbj.train.model.TimeModel;

import java.util.List;

/**
 * 数据与list做适配
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<TimeModel> mModelList;
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public MyAdapter(List<TimeModel> list){
        mModelList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //取出当前这个model
        TimeModel timeModel = mModelList.get(position);

        holder.timeCurrent.setText(timeModel.getName());
        holder.timeHour.setText(timeModel.getHour());
        holder.timeMinutes.setText(timeModel.getMinute());
    }


    //设置点击事件监听器
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){
        mOnRecyclerViewItemClickListener = listener;
    }

    public void removeOnRecyclerViewItemClickListener(){
        mOnRecyclerViewItemClickListener = null;
    }



    @Override
    public int getItemCount() {
        if (mModelList == null || mModelList.size() == 0){
            return 0;
        }
        return mModelList.size();
    }

    // 与date_item进行对应
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView timeCurrent;
        TextView timeHour;
        TextView timeMinutes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timeCurrent = itemView.findViewById(R.id.current_time);
            timeHour = itemView.findViewById(R.id.hour);
            timeMinutes = itemView.findViewById(R.id.min);
        }
    }
}


