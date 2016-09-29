package org.leaf.train.entity;

/**
 * 车次信息
 *
 * @author leaf
 * @date 2016-09-29 11:03
 */
public class TrainInfo {

    /**
     * 列次编号
     */
    private String trainNo;

    /**
     * 车次代码
     */
    private String code;

    /**
     * 首发站电码
     */
    private String startStationTeleCode;

    /**
     * 首发站名称(中文名)
     */
    private String startStationName;

    /**
     * 终点站代码
     */
    private String endStationTeleCode;

    /**
     * 终点车名称(中文名)
     */
    private String endStationName;

    /**
     * 上车站代码
     */
    private String fromStationTeleCode;

    /**
     * 上车站名称(中文名)
     */
    private String fromStationName;

    /**
     * 上车站顺序编号
     */
    private String fromStationNo;

    /**
     * 下车站代码
     */
    private String toStationTeleCode;

    /**
     * 下车站名称(中文名)
     */
    private String toStationName;

    /**
     * 下车站顺序编号
     */
    private String toStationNo;

    /**
     * 上车站出发时间
     */
    private String startTime;

    /**
     * 下车站到达时间
     */
    private String arriveTime;

    /**
     * 列车类型(中文)
     */
    private String className;

    /**
     * 历时(01:53)
     */
    private String lishi;

    /**
     * 是否可以网页购买: N(有票), Y(没票)
     */
    private boolean canWebBuy;

    /**
     * 开售时间
     */
    private String saleTime;

    /**
     * 备注比如:9月29日&lt;br/&gt;9点起售
     */
    private String note;

    /**
     * 列车是否被控制, 0不控制, 1被控制(属于不可购票)
     */
    private boolean isControlled;

    /**
     * 列车控制信息
     */
    private String controlledMessage;

    /**
     * 高级软卧
     */
    private int gjrwNum;

    /**
     * 软卧剩余数, 取值为: "--", "无", "*(表示还未开售)", 数字
     */
    private int rwNum;

    /**
     * 软座剩余数, 取值为: "--", "无", "*(表示还未开售)", 数字
     */
    private int rzNum;

    /**
     * 无座剩余数, 取值为: "--", "无", "*(表示还未开售)", 数字
     */
    private int wzNum;

    /**
     * 硬卧剩余数, 取值为: "--", "无", "*(表示还未开售)", 数字
     */
    private int ywNum;

    /**
     * 硬座剩余数, 取值为: "--", "无", "*(表示还未开售)", 数字
     */
    private int yzNum;

    /**
     * 二等座剩余数, 取值为: "--", "无", "*(表示还未开售)", 数字
     */
    private int edzNum;

    /**
     * 一等座剩余数, 取值为: "--", "无", "*(表示还未开售)", 数字
     */
    private int ydzNum;

    /**
     * 商务座剩余数, 取值为: "--", "无", "*(表示还未开售)", 数字
     */
    private int swzNum;

    public String getTrainNo() {
        return trainNo;
    }

    public TrainInfo setTrainNo(String trainNo) {
        this.trainNo = trainNo;
        return this;
    }

    public String getCode() {
        return code;
    }

    public TrainInfo setCode(String code) {
        this.code = code;
        return this;
    }

    public String getStartStationTeleCode() {
        return startStationTeleCode;
    }

    public TrainInfo setStartStationTeleCode(String startStationTeleCode) {
        this.startStationTeleCode = startStationTeleCode;
        return this;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public TrainInfo setStartStationName(String startStationName) {
        this.startStationName = startStationName;
        return this;
    }

    public String getEndStationTeleCode() {
        return endStationTeleCode;
    }

    public TrainInfo setEndStationTeleCode(String endStationTeleCode) {
        this.endStationTeleCode = endStationTeleCode;
        return this;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public TrainInfo setEndStationName(String endStationName) {
        this.endStationName = endStationName;
        return this;
    }

    public String getFromStationTeleCode() {
        return fromStationTeleCode;
    }

    public TrainInfo setFromStationTeleCode(String fromStationTeleCode) {
        this.fromStationTeleCode = fromStationTeleCode;
        return this;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public TrainInfo setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
        return this;
    }

    public String getToStationTeleCode() {
        return toStationTeleCode;
    }

    public TrainInfo setToStationTeleCode(String toStationTeleCode) {
        this.toStationTeleCode = toStationTeleCode;
        return this;
    }

    public String getToStationName() {
        return toStationName;
    }

    public TrainInfo setToStationName(String toStationName) {
        this.toStationName = toStationName;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public TrainInfo setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public TrainInfo setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public TrainInfo setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getLishi() {
        return lishi;
    }

    public TrainInfo setLishi(String lishi) {
        this.lishi = lishi;
        return this;
    }

    public boolean isCanWebBuy() {
        return canWebBuy;
    }

    public TrainInfo setCanWebBuy(boolean canWebBuy) {
        this.canWebBuy = canWebBuy;
        return this;
    }

    public String getSaleTime() {
        return saleTime;
    }

    public TrainInfo setSaleTime(String saleTime) {
        this.saleTime = saleTime;
        return this;
    }

    public String getNote() {
        return note;
    }

    public TrainInfo setNote(String note) {
        this.note = note;
        return this;
    }

    public boolean isControlled() {
        return isControlled;
    }

    public TrainInfo setControlled(boolean controlled) {
        isControlled = controlled;
        return this;
    }

    public String getControlledMessage() {
        return controlledMessage;
    }

    public TrainInfo setControlledMessage(String controlledMessage) {
        this.controlledMessage = controlledMessage;
        return this;
    }

    public int getGjrwNum() {
        return gjrwNum;
    }

    public TrainInfo setGjrwNum(int gjrwNum) {
        this.gjrwNum = gjrwNum;
        return this;
    }

    public int getRwNum() {
        return rwNum;
    }

    public TrainInfo setRwNum(int rwNum) {
        this.rwNum = rwNum;
        return this;
    }

    public int getRzNum() {
        return rzNum;
    }

    public TrainInfo setRzNum(int rzNum) {
        this.rzNum = rzNum;
        return this;
    }

    public int getWzNum() {
        return wzNum;
    }

    public TrainInfo setWzNum(int wzNum) {
        this.wzNum = wzNum;
        return this;
    }

    public int getYwNum() {
        return ywNum;
    }

    public TrainInfo setYwNum(int ywNum) {
        this.ywNum = ywNum;
        return this;
    }

    public int getYzNum() {
        return yzNum;
    }

    public TrainInfo setYzNum(int yzNum) {
        this.yzNum = yzNum;
        return this;
    }

    public int getEdzNum() {
        return edzNum;
    }

    public TrainInfo setEdzNum(int edzNum) {
        this.edzNum = edzNum;
        return this;
    }

    public int getYdzNum() {
        return ydzNum;
    }

    public TrainInfo setYdzNum(int ydzNum) {
        this.ydzNum = ydzNum;
        return this;
    }

    public int getSwzNum() {
        return swzNum;
    }

    public TrainInfo setSwzNum(int swzNum) {
        this.swzNum = swzNum;
        return this;
    }

    public String getFromStationNo() {
        return fromStationNo;
    }

    public TrainInfo setFromStationNo(String fromStationNo) {
        this.fromStationNo = fromStationNo;
        return this;
    }

    public String getToStationNo() {
        return toStationNo;
    }

    public TrainInfo setToStationNo(String toStationNo) {
        this.toStationNo = toStationNo;
        return this;
    }
}
