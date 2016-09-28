package org.leaf.train.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 车票查询结果
 *
 * @author yaoshuhong
 * @date 2016-09-28 11:13
 */
public class SearchResult {

    /**
     * 车次
     */
    private String trainCode;

    /**
     * 日期
     */
    private String date;

    /**
     * 票务结果
     */
    private List<TicketResult> list = new ArrayList<>(0);

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<TicketResult> getList() {
        return list;
    }

    public void setList(List<TicketResult> list) {
        this.list = list;
    }
}
