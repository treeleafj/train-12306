package org.leaf.train.utils;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.leaf.train.entity.Journey;
import org.leaf.train.entity.Price;
import org.leaf.train.entity.Station;
import org.leaf.train.entity.TrainInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author leaf
 * @date 2016-09-28 15:31
 */
public class CacheUtils {


    private static CacheManager manager = CacheManagerBuilder.newCacheManagerBuilder()
//            .with(CacheManagerBuilder.persistence("E://12306/data"))
            .withCache("stations", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ArrayList.class, ResourcePoolsBuilder.heap(100000))
                    .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.HOURS))).build())

            .withCache("tickets", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ArrayList.class, ResourcePoolsBuilder.heap(100000))
                    .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.MINUTES))).build())

            .withCache("prices", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, Price.class, ResourcePoolsBuilder.heap(100000))
                    .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.DAYS))).build())
            .build(true);

    /**
     * 获取缓存的站点信息
     *
     * @param trainNo 列车编号
     * @param journey 旅程信息
     * @return
     */
    public static List<Station> getStations(String trainNo, Journey journey) {
        String key = buildStationsKey(trainNo, journey);
        Cache<String, ArrayList> cache = manager.getCache("stations", String.class, ArrayList.class);
        List<Station> list = cache.get(key);
        return list;
    }

    /**
     * 缓存站点信息
     *
     * @param trainNo 列车编号
     * @param journey 旅程信息
     * @param list    站点信息
     */
    public static void putStations(String trainNo, Journey journey, List<Station> list) {
        String key = buildStationsKey(trainNo, journey);
        Cache<String, ArrayList> cache = manager.getCache("stations", String.class, ArrayList.class);
        cache.put(key, (ArrayList) list);
    }

    /**
     * 获取旅行的票务信息
     *
     * @param journey 旅程信息
     */
    public static List<TrainInfo> getTickets(Journey journey) {
        String key = builTicketsKey(journey);
        Cache<String, ArrayList> cache = manager.getCache("tickets", String.class, ArrayList.class);
        List<TrainInfo> trainInfos = cache.get(key);
        return trainInfos;
    }

    /**
     * 缓存票务信息
     *
     * @param journey 旅程信息
     * @param infos   票务信息
     */
    public static void putTickets(Journey journey, List<TrainInfo> infos) {
        String key = builTicketsKey(journey);
        Cache<String, ArrayList> cache = manager.getCache("tickets", String.class, ArrayList.class);
        cache.put(key, (ArrayList) infos);
    }

    /**
     * 从缓存里获取车票价格
     *
     * @param trainNo
     * @param fromNo
     * @param toNo
     * @param date
     * @return
     */
    public static Price getPrice(String trainNo, String fromNo, String toNo, String date) {
        String key = buildPriceKey(trainNo, fromNo, toNo, date);
        Cache<String, Price> cache = manager.getCache("prices", String.class, Price.class);
        return cache.get(key);
    }

    /**
     * 车票价格存到缓存
     *
     * @param trainNo
     * @param fromNo
     * @param toNo
     * @param date
     * @param price
     */
    public static void putPrice(String trainNo, String fromNo, String toNo, String date, Price price) {
        String key = buildPriceKey(trainNo, fromNo, toNo, date);
        Cache<String, Price> cache = manager.getCache("prices", String.class, Price.class);
        cache.put(key, price);
    }

    /**
     * 构建站点缓存信息的key
     *
     * @param trainNo 列车编号
     * @param journey 旅程信息
     * @return
     */
    private static String buildStationsKey(String trainNo, Journey journey) {
        StringBuilder sb = new StringBuilder();
        sb.append(trainNo).append('_')
                .append(journey.getFrom()).append('_')
                .append(journey.getTo()).append('_')
                .append(journey.getDate());
        return sb.toString();
    }

    /**
     * 构建票务缓存信息的key
     *
     * @param journey 旅程信息
     * @return
     */
    private static String builTicketsKey(Journey journey) {
        StringBuilder sb = new StringBuilder();
        sb.append(journey.getFrom()).append('_')
                .append(journey.getTo()).append('_')
                .append(journey.getDate());
        return sb.toString();
    }

    private static String buildPriceKey(String trainNo, String fromNo, String toNo, String date) {
        return trainNo + fromNo + toNo + date;
    }
}
