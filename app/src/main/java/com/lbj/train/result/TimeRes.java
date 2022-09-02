package com.lbj.train.result;

import com.lbj.train.model.TimeModel;

import java.util.List;

public class TimeRes {
    private Integer code;
    private String message;
    private List<TimeModel> data;

    public TimeRes() {
    }

    public TimeRes(Integer code, String message, List<TimeModel> time) {
        this.code = code;
        this.message = message;
        this.data = time;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TimeModel> getData() {
        return data;
    }

    public void setData(List<TimeModel> data) {
        this.data = data;
    }


}
