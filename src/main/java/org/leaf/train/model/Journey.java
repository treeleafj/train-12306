package org.leaf.train.model;

/**
 * 旅程信息
 *
 * @author yaoshuhong
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

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDate() {
        return date;
    }
}
