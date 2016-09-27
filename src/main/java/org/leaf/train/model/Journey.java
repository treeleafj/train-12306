package org.leaf.train.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 旅程信息
 *
 * @author yaoshuhong
 * @date 2016-09-27 17:30
 */
public class Journey {

    /**
     * 上车站代码
     */
    private String from;

    /**
     * 下车站代码
     */
    private String to;

    /**
     * 日期
     */
    private String date;

    public Journey(String from, String to, String date) {
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public Journey(String from, String to, Date date) {
        this.from = from;
        this.to = to;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.date = dateFormat.format(date);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
