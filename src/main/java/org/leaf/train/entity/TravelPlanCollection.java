package org.leaf.train.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 出行计划集合
 *
 * @author leaf
 * @date 2016-09-28 11:13
 */
public class TravelPlanCollection {

    /**
     * 车次
     */
    private String code;

    /**
     * 票务结果
     */
    private List<TravelPlan> list = new ArrayList<>(0);

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<TravelPlan> getList() {
        return list;
    }

    public void setList(List<TravelPlan> list) {
        this.list = list;
    }
}
