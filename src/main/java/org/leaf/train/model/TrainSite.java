package org.leaf.train.model;

/**
 * 站点信息
 *
 * @author yaoshuhong
 * @date 2016-09-27 17:11
 */
public class TrainSite {

    /**首发站名(中文)*/
    private String start_station_name;

    /**到站时间(07:23)*/
    private String arrive_time;

    /**站点代码*/
    private String station_train_code;

    /**站点名称*/
    private String station_name;

    /**列车类型(中文)*/
    private String train_class_name;

    /**服务类型*/
    private String service_type;

    /**开车时间*/
    private String start_time;

    /**中途停车多久*/
    private String stopover_time;

    /**终点站*/
    private String end_station_name;

    /**第几个站*/
    private String station_no;

    /**是否正常*/
    private Boolean isEnabled;

    public String getStart_station_name() {
        return start_station_name;
    }

    public void setStart_station_name(String start_station_name) {
        this.start_station_name = start_station_name;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public String getStation_train_code() {
        return station_train_code;
    }

    public void setStation_train_code(String station_train_code) {
        this.station_train_code = station_train_code;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getTrain_class_name() {
        return train_class_name;
    }

    public void setTrain_class_name(String train_class_name) {
        this.train_class_name = train_class_name;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStopover_time() {
        return stopover_time;
    }

    public void setStopover_time(String stopover_time) {
        this.stopover_time = stopover_time;
    }

    public String getEnd_station_name() {
        return end_station_name;
    }

    public void setEnd_station_name(String end_station_name) {
        this.end_station_name = end_station_name;
    }

    public String getStation_no() {
        return station_no;
    }

    public void setStation_no(String station_no) {
        this.station_no = station_no;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }
}
