package org.leaf.train.utils;

import org.leaf.train.entity.TravelPlanCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author leaf
 * @date 2016-09-28 9:51
 */
public class PrintUtils {

    private static Logger log = LoggerFactory.getLogger(PrintUtils.class);

    public static void print(TravelPlanCollection travelPlanCollection) {
        log.info("车次:{}", travelPlanCollection.getCode());
        travelPlanCollection.getList().forEach(c ->
                log.info("----------({}){}->{} {}, 商务座:{}, 一等座:{}, 二等座:{}, 无座:{}, 软座:{}, 硬座:{}, 软卧:{}, 硬卧:{}, 高级软卧:{}",
                        c.getMode().getDesc(), c.getFromStationName(), c.getToStationName(), c.getStartTime(),
                        c.getSwzNum(), c.getYdzNum(), c.getEdzNum(), c.getWzNum(), c.getRwNum(), c.getYzNum(), c.getRwNum(), c.getYwNum(), c.getGjrwNum()));
    }
}
