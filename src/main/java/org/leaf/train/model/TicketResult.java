package org.leaf.train.model;

/**
 * @author yaoshuhong
 * @date 2016-09-28 11:17
 */
public class TicketResult {

    private BuyTicketType type;

    private String fromStationName;

    private String toStationName;

    /**
     * 上车发车时间
     */
    private String startTime;

    /**
     * 下车站到达时间
     */
    private String arriveTime;

    private String swzNum;

    private String ydzNum;

    private String edzNum;

    private String wzNum;

    public BuyTicketType getType() {
        return type;
    }

    public void setType(BuyTicketType type) {
        this.type = type;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public void setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
    }

    public String getToStationName() {
        return toStationName;
    }

    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getSwzNum() {
        return swzNum;
    }

    public void setSwzNum(String swzNum) {
        this.swzNum = swzNum;
    }

    public String getYdzNum() {
        return ydzNum;
    }

    public void setYdzNum(String ydzNum) {
        this.ydzNum = ydzNum;
    }

    public String getEdzNum() {
        return edzNum;
    }

    public void setEdzNum(String edzNum) {
        this.edzNum = edzNum;
    }

    public String getWzNum() {
        return wzNum;
    }

    public void setWzNum(String wzNum) {
        this.wzNum = wzNum;
    }
}
