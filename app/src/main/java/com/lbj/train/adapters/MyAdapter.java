package com.lbj.train.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lbj.train.R;
import com.lbj.train.beans.Time;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Time> {

    private int resourceId;

    public MyAdapter(Context context, int textViewResourceId,
                        List<Time> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获取当前项的time实例
        Time time = getItem(position);

        View view;

        ViewHolder viewHolder;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
            viewHolder = new ViewHolder();
            ViewHolder.hour = view.findViewById(R.id.hour);
            ViewHolder.minutes = view.findViewById(R.id.minutes);
            //将ViewHolder存储在View中
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.hour.setText(time.getHour());
        viewHolder.minutes.setText(time.getMinute());

        return view;
    }

}

class ViewHolder{
    public static TextView hour;
    public static TextView minutes;
}
