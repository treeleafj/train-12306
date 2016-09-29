package org.leaf.train.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leaf
 * @date 2016-09-27 18:06
 */
public class StationUtils {

    static final Logger log = LoggerFactory.getLogger(StationUtils.class);

    static Map<String, String> map = new HashMap<>();

    static {

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("station.txt");

        if (in == null) {
            log.error("读取不到station.properties");
        }
        try {
            String station_names = IOUtils.toString(in, "utf-8");

            String[] split = station_names.split("@");
            for (String s : split) {
                String[] array = s.split("\\|");
                if (array.length < 3) {
                    continue;
                }
                try {
                    map.put(array[1], array[2]);
                } catch (Exception e) {
                    log.error("解析{}错误!!!", Arrays.toString(array), e);
                }
            }
        } catch (IOException e) {
            log.error("读取station.properties失败", e);
        }

    }

    /**
     * 根据站点中文名获取对应的电话码
     *
     * @param name 站点中文名
     * @return
     */
    public static String getTelecode(String name) {
        return map.get(name);
    }
}
