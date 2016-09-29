package org.leaf.train.service;

import org.leaf.train.entity.Journey;
import org.leaf.train.entity.TravelPlan;
import org.leaf.train.entity.TravelPlanCollection;

import java.util.List;

/**
 * 列车查询服务
 * <p>
 * Created by leaf on 2016/9/27 027.
 */
public interface TrainService {

    /**
     * 查询有票的车次,可查询:中途上车补票,往后买多几个站,往前买多几个站
     *
     * @param journey
     * @return 查到的所有有票的结果(包含:中途上车补票, 往后买多几个站, 往前买多几个站)
     */
    List<TravelPlanCollection> search(Journey journey);

    /**
     * 查询正常有票的车次
     *
     * @param journey 旅程信息
     * @return 查到的所有有票的结果
     */
    List<TravelPlanCollection> searchNone(Journey journey);

    /**
     * 往前查询有余票的票务信息
     *
     * @param journey 旅程信息
     * @return 当前有票最先查到的票务信息
     */
    TravelPlan searchFront(Journey journey);

    /**
     * 往后查询有余票的票务信息
     *
     * @param journey 旅程信息
     * @return 当前有票最先查到的票务信息
     */
    TravelPlan searchBack(Journey journey);

    /**
     * 中途补票的方式查询有余票的票务信息
     *
     * @param journey 旅程信息
     * @return 当前有票最先查到的票务信息
     */
    TravelPlan searchCenter(Journey journey);
}
