package com.lbj.train.result;

import com.lbj.train.model.TimeModel;

import java.util.List;

public class TimeRes {
    private Integer resCode;
    private String message;
    private List<TimeModel> time;

    public TimeRes() {
    }


    public TimeRes(Integer resCode, String message, List<TimeModel> time) {
        this.resCode = resCode;
        this.message = message;
        this.time = time;
    }


    public Integer getResCode() {
        return resCode;
    }

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TimeModel> getTime() {
        return time;
    }

    public void setTime(List<TimeModel> time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TimeRes{" +
                "resCode=" + resCode +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
