package com.lbj.train.model;

//时间数据模型，因为需要去进行从大到小的排序，所以要实现comparable接口
public class TimeModel implements Comparable<TimeModel>{
    private String currentTime;
    private Integer hour;
    private Integer minute;

    public TimeModel(String currentTime, Integer hour, Integer minute) {
        this.currentTime = currentTime;
        this.hour = hour;
        this.minute = minute;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "TimeModel{" +
                "currentTime='" + currentTime + '\'' +
                ", hour=" + hour +
                ", minute=" + minute +
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
        if (this.hour == timeModel.hour){
            //当前小时是相等的
            return Integer.compare(this.minute, timeModel.minute);
        } else {
            //当前小时不相等
            return Integer.compare(this.hour, timeModel.hour);
        }
    }
}
