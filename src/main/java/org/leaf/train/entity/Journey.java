package org.leaf.train.entity;

/**
 * 旅程信息,用户要求的出发地和目的地,以及日期
 *
 * @author leaf
 * @date 2016-09-27 17:30
 */
public class Journey {

    /**
     * 车次代码
     */
    private String code;

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

    public Journey() {
    }

    public Journey(String from, String to, String date) {
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public Journey(String code, String from, String to, String date) {
        this(from, to, date);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public Journey setCode(String code) {
        this.code = code;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Journey setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public Journey setTo(String to) {
        this.to = to;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Journey setDate(String date) {
        this.date = date;
        return this;
    }
}
