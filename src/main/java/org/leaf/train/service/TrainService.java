package org.leaf.train.service;

import org.leaf.train.model.Journey;
import org.leaf.train.model.SearchResult;
import org.leaf.train.model.TrainStation;
import org.leaf.train.model.TrainTicket;

import java.util.List;

/**
 * 列车查询服务
 *
 * Created by yaoshuhong on 2016/9/27 027.
 */
public interface TrainService {

    /**
     * 查询车票列表
     *
     * @param journey 旅程信息
     * @return
     */
    List<TrainTicket> tickets(Journey journey);

    /**
     * 查询指定车次的车票信息
     *
     * @param journey 旅程信息
     * @return
     */
    TrainTicket ticket(Journey journey);

    /**
     * 查询指定车号的站点信息
     *
     * @param trainNo 车次编号
     * @param journey 旅程信息
     * @return
     */
    List<TrainStation> stations(String trainNo, Journey journey);

    /**
     * 查询有票的车次,可查询:中途上车补票,往后买多几个站,往前买多几个站
     *
     * @param journey
     * @return 查到的所有有票的结果(包含:中途上车补票, 往后买多几个站, 往前买多几个站)
     */
    List<SearchResult> search(Journey journey);

    /**
     * 查询正常有票的车次
     *
     * @param journey 旅程信息
     * @return 查到的所有有票的结果
     */
    List<SearchResult> searchNone(Journey journey);

    /**
     * 往前查询有余票的票务信息
     *
     * @param journey 旅程信息
     * @return 当前有票最先查到的票务信息
     */
    TrainTicket searchFront(Journey journey);

    /**
     * 往后查询有余票的票务信息
     *
     * @param journey 旅程信息
     * @return 当前有票最先查到的票务信息
     */
    TrainTicket searchBack(Journey journey);

    /**
     * 中途补票的方式查询有余票的票务信息
     *
     * @param journey 旅程信息
     * @return 当前有票最先查到的票务信息
     */
    TrainTicket FrontCenter(Journey journey);
}
