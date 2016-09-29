package org.leaf.train.entity;

/**
 * 站点信息
 *
 * @author leaf
 * @date 2016-09-29 10:50
 */
public class Station {

    /**
     * 达到时间
     */
    private String arriveTime;

    /**
     * 开车时间
     */
    private String startTime;

    /**
     * 站点名称
     */
    private String name;

    /**
     * 车次号
     */
    private String code;

    /**
     * 停留多少分钟
     */
    private String stopoverTime;

    /**
     * 第几个站,从1开始
     */
    private Integer index;

    public String getArriveTime() {
        return arriveTime;
    }

    public Station setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public Station setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getName() {
        return name;
    }

    public Station setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Station setCode(String code) {
        this.code = code;
        return this;
    }

    public String getStopoverTime() {
        return stopoverTime;
    }

    public Station setStopoverTime(String stopoverTime) {
        this.stopoverTime = stopoverTime;
        return this;
    }

    public Integer getIndex() {
        return index;
    }

    public Station setIndex(Integer index) {
        this.index = index;
        return this;
    }
}
