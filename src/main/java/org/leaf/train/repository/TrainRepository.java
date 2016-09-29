package org.leaf.train.repository;

import org.leaf.train.entity.Station;
import org.leaf.train.entity.TrainInfo;
import org.leaf.train.entity.Journey;

import java.util.List;

/**
 * 高铁站仓储
 * Created by leaf on 2016/9/29 029.
 */
public interface TrainRepository {

    /**
     * 查询车票列表
     *
     * @param journey 旅程信息
     * @return
     */
    List<TrainInfo> tickets(Journey journey);

    /**
     * 查询指定车次的车票信息
     *
     * @param journey 旅程信息
     * @return
     */
    TrainInfo ticket(Journey journey);

    /**
     * 查询指定车号的站点信息
     *
     * @param trainNo 车次编号
     * @param journey 旅程信息
     * @return
     */
    List<Station> stations(String trainNo, Journey journey);
}
