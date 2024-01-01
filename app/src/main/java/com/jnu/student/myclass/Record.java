package com.jnu.student.myclass;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {

    // 序列化需要的UID值
    private static final long serialVersionUID = 1227L;
    Date date;
    String describe;
    double change_value;
    double now_value;

    public Record(Date date, String describe, double change_value, double now_value) {
        this.date = date;
        this.describe = describe;
        this.change_value = change_value;
        this.now_value = now_value;
    }

    public Date getDate() {
        return date;
    }

    public String getDescribe()  {
        return describe;
    }

    public double getChange_value() {
        return change_value;
    }

    public double getNow_value() {
        return now_value;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setChange_value(double change_value) {
        this.change_value = change_value;
    }

    public void setNow_value(double now_value) {
        this.now_value = now_value;
    }
}
