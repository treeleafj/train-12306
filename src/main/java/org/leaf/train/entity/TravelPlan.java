package org.leaf.train.entity;

import org.springframework.beans.BeanUtils;

/**
 * 出行计划
 *
 * @author leaf
 * @date 2016-09-28 11:17
 */
public class TravelPlan extends TrainInfo {

    /**
     * 购票方式
     */
    private BuyTicketMode mode;

    /**
     * 相差多少站
     */
    private int differ;

    public TravelPlan() {
    }

    public TravelPlan(TrainInfo trainInfo) {
        BeanUtils.copyProperties(trainInfo, this);
    }

    public TravelPlan(TrainInfo trainInf, BuyTicketMode mode, int differ) {
        this(trainInf);
        this.mode = mode;
        this.differ = differ;
    }

    public BuyTicketMode getMode() {
        return mode;
    }

    public TravelPlan setMode(BuyTicketMode mode) {
        this.mode = mode;
        return this;
    }

    public int getDiffer() {
        return differ;
    }

    public TravelPlan setDiffer(int differ) {
        this.differ = differ;
        return this;
    }
}
