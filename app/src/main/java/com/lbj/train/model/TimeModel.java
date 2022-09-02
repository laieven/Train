package com.lbj.train.model;

import java.util.Objects;

/**
 * 根据返回示例设置当前模型的名称
 * 因为需要去进行从大到小的排序，所以要实现comparable接口
 *
 */
public class TimeModel implements Comparable<TimeModel>{
    private String name;
    private Integer hour;
    private Integer min;

    public TimeModel(String name, Integer hour, Integer minute) {
        this.name = name;
        this.hour = hour;
        this.min = minute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return min;
    }

    public void setMinute(Integer minute) {
        this.min = minute;
    }

    @Override
    public String toString() {
        return "TimeModel{" +
                "name='" + name + '\'' +
                ", hour=" + hour +
                ", minute=" + min +
                '}';
    }

    @Override
    public int compareTo(TimeModel timeModel) {
        //如果当前时间模型为空或者二者是同一个
        if (timeModel == null || timeModel == this){
            return 0;
        }
        //小时小的显示在前面，如果小时相同，那么就对分钟进行排序
        //看当前小时是否相等，如果相等，那么就按照小的在前
        if (Objects.equals(this.hour, timeModel.hour)){
            //当前小时是相等的
            return Integer.compare(this.min, timeModel.min);
        } else {
            //当前小时不相等
            return Integer.compare(this.hour, timeModel.hour);
        }
    }
}
