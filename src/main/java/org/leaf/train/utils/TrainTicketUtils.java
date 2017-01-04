package org.leaf.train.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.leaf.train.entity.TrainInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leaf
 * @date 2016-09-27 17:37
 */
public class TrainTicketUtils {

    private static Logger log = LoggerFactory.getLogger(TrainTicketUtils.class);

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
        } else if ("有".equals(num)) {//有,暂且认为有1张
            return 9999;
        } else {
            return Integer.parseInt(num);
        }
    }

    /**
     * 将高铁票的金额转为double
     *
     * @param s
     * @return
     */
    public static double toPrice(String s) {
        if (StringUtils.isBlank(s)) {
            return 0;
        }
        String p = s.replaceAll("¥", "");
        if (NumberUtils.isNumber(p)) {
            return NumberUtils.toDouble(p, 0);
        } else {
            log.error("未知的金额类型:{}", p);
            return 0;
        }
    }
}
