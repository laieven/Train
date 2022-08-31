package com.lbj.train;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BaseInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.lbj.train.model.TimeModel;
import com.lbj.train.provider.TimeProvider;

public class TimeShowActivity extends Activity {

    private ImageView mHourShow;
    private ImageView mMinuteShow;
    private static final int HOUR = 1;
    private static final int MINUTE = 2;

    private TimeModel mTimeModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_show);
        super.onCreate(savedInstanceState);
        initView();
        getTimeModel();

        //计算时分分别需要走的度数
        //先计算分针的，每走一步相当于走了6°
        float minuteCrossAngle = mTimeModel.getMinute() * (360 / 60);
        startAnimation(MINUTE, minuteCrossAngle);

        //时针，每走一步相当于0.5°
        float hourCrossAngle = mTimeModel.getHour() * (360 / 12 / 60);
        hourCrossAngle += minuteCrossAngle;
        //开启动画
        startAnimation(HOUR, hourCrossAngle);
    }

    private void startAnimation(int type, float angle) {
        RotateAnimation animation = new RotateAnimation(0, angle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5F);

        //设置差值器
        animation.setInterpolator(baseInterpolator);

        //动画持续时间500ms
        animation.setDuration(500);

        //选择相应的指针进行转动
        switch (type){
            case HOUR:
                mHourShow.setAnimation(animation);
                break;
            case MINUTE:
                mMinuteShow.setAnimation(animation);
                break;
        }

        //开启动画
        animation.start();
    }

    private void initView() {
        mHourShow = this.findViewById(R.id.hour_image_show);
        mMinuteShow = this.findViewById(R.id.minute_image_show);
    }

    //获得相关数据
    private void getTimeModel() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        mTimeModel = TimeProvider.getInstance().getLineData(position);
        TimeModel timeModel = null;
    }


    //相关动画
    private final BaseInterpolator baseInterpolator = new BaseInterpolator() {
        @Override
        public float getInterpolation(float input) {
            return 1 - (float)Math.pow(1 - input, 4);
        }
    };



}
