package org.leaf.train.utils;

import org.leaf.train.model.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yaoshuhong
 * @date 2016-09-28 9:51
 */
public class PrintUtils {

    private static Logger log = LoggerFactory.getLogger(PrintUtils.class);

    public static void print(SearchResult searchResult) {
        log.info("车次:{}, 日期:{}", searchResult.getTrainCode(), searchResult.getDate());
        searchResult.getList().forEach(c -> {
            log.info("----------({}){}->{} {}, 商务座:{}, 一等座:{}, 二等座:{}, 无座:{}", c.getType().getDesc(), c.getFromStationName(), c.getToStationName(),
                    c.getStartTime(), c.getSwzNum(), c.getYdzNum(), c.getEdzNum(), c.getWzNum());
        });
    }
}
