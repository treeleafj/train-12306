package org.leaf.train.service;

import org.leaf.train.model.Journey;
import org.leaf.train.model.TrainSite;
import org.leaf.train.model.TrainTicket;

import java.util.List;

/**
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
     * @param trainCode 车次代码
     * @param journey   旅程信息
     * @return
     */
    TrainTicket ticket(String trainCode, Journey journey);

    /**
     * 查询指定车号的站点信息
     *
     * @param trainNo 车次编号
     * @param journey 旅程信息
     * @return
     */
    List<TrainSite> sites(String trainNo, Journey journey);

    /**
     * 查询有票的车次,可中途上车补票
     *
     * @param journey
     * @return
     */
    List<TrainTicket> findTicket(Journey journey);
}
