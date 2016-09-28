package org.leaf.train.utils;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.leaf.train.model.Journey;
import org.leaf.train.model.TrainStation;
import org.leaf.train.model.TrainTicket;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yaoshuhong
 * @date 2016-09-28 15:31
 */
public class CacheUtils {

    private static CacheManager manager = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("stations", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, List.class, ResourcePoolsBuilder.heap(10000))
                    .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.HOURS))).build())
            .withCache("tickets", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, List.class, ResourcePoolsBuilder.heap(10000))
                    .withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.MINUTES))).build())
            .build(true);

    /**
     * 获取缓存的站点信息
     *
     * @param trainNo 列车编号
     * @param journey 旅程信息
     * @return
     */
    public static List<TrainStation> getStations(String trainNo, Journey journey) {
        String key = buildStationsKey(trainNo, journey);
        Cache<String, List> cache = manager.getCache("stations", String.class, List.class);
        List<TrainStation> list = cache.get(key);
        return list;
    }

    /**
     * 缓存站点信息
     *
     * @param trainNo 列车编号
     * @param journey 旅程信息
     * @param list    站点信息
     */
    public static void putStations(String trainNo, Journey journey, List<TrainStation> list) {
        String key = buildStationsKey(trainNo, journey);
        Cache<String, List> cache = manager.getCache("stations", String.class, List.class);
        cache.put(key, list);
    }

    /**
     * 获取旅行的票务信息
     *
     * @param journey 旅程信息
     */
    public static List<TrainTicket> getTickets(Journey journey) {
        String key = builTicketsKey(journey);
        Cache<String, List> cache = manager.getCache("tickets", String.class, List.class);
        List<TrainTicket> trainTickets = cache.get(key);
        return trainTickets;
    }

    /**
     * 缓存票务信息
     *
     * @param journey      旅程信息
     * @param trainTickets 票务信息
     */
    public static void putTickets(Journey journey, List<TrainTicket> trainTickets) {
        String key = builTicketsKey(journey);
        Cache<String, List> cache = manager.getCache("tickets", String.class, List.class);
        cache.put(key, trainTickets);
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

}
