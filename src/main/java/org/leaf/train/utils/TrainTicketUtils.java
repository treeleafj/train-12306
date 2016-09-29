package org.leaf.train.utils;

import org.leaf.train.entity.TrainInfo;

/**
 * @author leaf
 * @date 2016-09-27 17:37
 */
public class TrainTicketUtils {

    /**
     * 获取有座位的票数
     *
     * @param trainInfo
     * @return
     */
    public static int zwNum(TrainInfo trainInfo) {
        int num = trainInfo.getGjrwNum() +
                trainInfo.getRwNum() +
                trainInfo.getRzNum() +
                trainInfo.getYwNum() +
                trainInfo.getYzNum() +
                trainInfo.getEdzNum() +
                trainInfo.getYdzNum() +
                trainInfo.getSwzNum();
        return num;
    }

    /**
     * 获取总票数
     *
     * @param trainInfo
     * @return
     */
    public static int ticketNum(TrainInfo trainInfo) {
        int num = trainInfo.getGjrwNum() +
                trainInfo.getRwNum() +
                trainInfo.getRzNum() +
                trainInfo.getWzNum() +
                trainInfo.getYwNum() +
                trainInfo.getYzNum() +
                trainInfo.getEdzNum() +
                trainInfo.getYdzNum() +
                trainInfo.getSwzNum();
        return num;
    }

    public static int toNum(String num) {
        // "--", "无", "*(表示还未开售)", 数字
        if ("--".equals(num)) {
            return 0;
        } else if ("无".equals(num)) {
            return 0;
        } else if ("*".equals(num)) {
            return 0;
        } else {
            return Integer.parseInt(num);
        }
    }
}
