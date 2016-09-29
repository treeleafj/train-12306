package org.leaf.train.service.impl;

import org.leaf.train.entity.BuyTicketMode;
import org.leaf.train.entity.Journey;
import org.leaf.train.entity.Price;
import org.leaf.train.entity.Station;
import org.leaf.train.entity.TrainInfo;
import org.leaf.train.entity.TravelPlan;
import org.leaf.train.entity.TravelPlanCollection;
import org.leaf.train.repository.TrainRepository;
import org.leaf.train.service.TrainService;
import org.leaf.train.utils.TrainTicketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.treeleaf.common.json.Jsoner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leaf
 * @date 2016-09-27 15:10
 */
@Service
public class TrainServiceImpl implements TrainService {

    private static Logger log = LoggerFactory.getLogger(TrainServiceImpl.class);

    @Autowired
    private TrainRepository trainRepository;

    @Override
    public Price price(String trainNo, String fromNo, String toNo, String date) {
        return trainRepository.price(trainNo, fromNo, toNo, date);
    }

    @Override
    public List<TravelPlanCollection> search(Journey journey) {
        List<TrainInfo> list = trainRepository.tickets(journey);

        List<TravelPlanCollection> result = new ArrayList<>();

        for (TrainInfo trainInfo : list) {
            int num = TrainTicketUtils.ticketNum(trainInfo);

            if (num > 0) {
                TravelPlan travelPlan = new TravelPlan(trainInfo, BuyTicketMode.NONE, 0);
                addSearchResult(result, travelPlan);
            } else {
                //当前票务的站点旅程信息
                Journey nowJ = new Journey(trainInfo.getCode(), trainInfo.getFromStationName(),
                        trainInfo.getToStationName(), journey.getDate());

                TravelPlan travelPlan;

                travelPlan = this.searchBack(nowJ);//只往后查询多买几站

                if (travelPlan != null) {
                    this.addSearchResult(result, travelPlan);
                }

                travelPlan = this.searchCenter(nowJ);//只中途上车补票
                if (travelPlan != null) {
                    this.addSearchResult(result, travelPlan);
                }

                travelPlan = this.searchFront(nowJ);//只往前查询
                if (travelPlan != null) {
                    this.addSearchResult(result, travelPlan);
                }
            }
        }

        return result;
    }

    @Override
    public List<TravelPlanCollection> searchNone(Journey journey) {
        List<TrainInfo> list = trainRepository.tickets(journey);

        List<TravelPlanCollection> result = new ArrayList<>();

        for (TrainInfo trainInfo : list) {
            int num = TrainTicketUtils.ticketNum(trainInfo);

            if (num > 0) {
                TravelPlan travelPlan = new TravelPlan(trainInfo, BuyTicketMode.NONE, 0);
                this.addSearchResult(result, travelPlan);
            }
        }

        return result;
    }

    private void addSearchResult(List<TravelPlanCollection> travelPlanCollections, TravelPlan travelPlan) {

        TravelPlanCollection travelPlanCollection = null;
        for (TravelPlanCollection result : travelPlanCollections) {
            if (result.getCode().equals(travelPlan.getCode())) {
                travelPlanCollection = result;
                break;
            }
        }

        if (travelPlanCollection == null) {
            travelPlanCollection = new TravelPlanCollection();
            travelPlanCollection.setCode(travelPlan.getCode());
            travelPlanCollections.add(travelPlanCollection);
        }

        travelPlanCollection.getList().add(travelPlan);
    }

    @Override
    public TravelPlan searchFront(Journey journey) {

        TrainInfo info = trainRepository.ticket(journey);
        if (info == null) {
            log.error("站点票务信息查询不到:{}", Jsoner.toJson(journey));
            return null;
        }

        List<Station> stations = trainRepository.stations(info.getTrainNo(), journey);
        if (stations.size() < 2) {
            return null;
        }

        //往前查,起点是上车站往前变,终点是下车站不变
        int start = 0;
        for (int i = 1; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(journey.getFrom())) {
                start = i;
                break;
            }
        }

        int stationNum = 0;

        for (int i = start - 1; i >= 0; i--) {//倒着查询是否可以往前买
            Station station = stations.get(i);
            Journey newJourney = new Journey(journey.getCode(), station.getName(), journey.getTo(), journey.getDate());
            TrainInfo newInfo = trainRepository.ticket(newJourney);
            if (newInfo == null) {//可能双车次号
                newJourney = new Journey(stations.get(0).getCode(), station.getName(), journey.getTo(), journey.getDate());
                newInfo = trainRepository.ticket(newJourney);
            }

            if (newInfo == null) {//这个时候就真的没有了
                return null;
            }

            stationNum++;

            int num = TrainTicketUtils.ticketNum(newInfo);
            if (num > 0) {//第一个有票的站就返回
                return new TravelPlan(newInfo, BuyTicketMode.WANGQIAN, stationNum);
            }
        }

        return null;
    }

    @Override
    public TravelPlan searchBack(Journey journey) {

        TrainInfo info = trainRepository.ticket(journey);
        if (info == null) {
            log.error("站点票务信息查询不到:{}", Jsoner.toJson(journey));
            return null;
        }

        List<Station> stations = trainRepository.stations(info.getTrainNo(), journey);
        if (stations.size() < 2) {
            return null;
        }

        //只往后,不中途补票, 起点是上车站不变, 终点是下车站往后一直变
        int start = 1;
        for (int i = 1; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(journey.getTo())) {
                start = i;
                break;
            }
        }

        int stationNum = 0;

        for (int i = start + 1; i < stations.size(); i++) {
            Station station = stations.get(i);

            Journey newJourney = new Journey(journey.getCode(), journey.getFrom(), station.getName(), journey.getDate());
            TrainInfo newInfo = trainRepository.ticket(newJourney);

            if (newInfo == null) {//可能双车次号,那么用另外一个次车号去查询
                newJourney = new Journey(stations.get(0).getCode(), journey.getFrom(), station.getName(), journey.getDate());
                newInfo = trainRepository.ticket(newJourney);
            }

            if (newInfo == null) {
                continue;//如果还是查不到,说明这个站到达不了,有些站就是这样只能经过,不能上车
            }

            stationNum++;

            int num = TrainTicketUtils.ticketNum(newInfo);
            if (num > 0) {//第一个有票的站就返回
                return new TravelPlan(newInfo, BuyTicketMode.WANGHOU, stationNum);
            }
        }

        return null;
    }

    @Override
    public TravelPlan searchCenter(Journey journey) {

        TrainInfo info = trainRepository.ticket(journey);
        if (info == null) {
            log.error("站点票务信息查询不到:{}", Jsoner.toJson(journey));
            return null;
        }

        List<Station> stations = trainRepository.stations(info.getTrainNo(), journey);
        if (stations.size() < 2) {
            return null;
        }

        //只中途补票, 起点是上车站不变, 终点是下车站往前变
        int end = 1;//下车站位置
        int start = 0;//上车站位置
        for (int i = 1; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(journey.getTo())) {
                end = i;
            }

            if (stations.get(i).getName().equals(journey.getFrom())) {
                start = i;
            }
        }

        if (end <= 1) {//如果第二个站级是目的,那么就没必要中途补票了...
            return null;
        }

        int stationNum = 0;

        for (int i = end - 1; i > start; i--) {//倒着查询是否可以中途补票
            Station station = stations.get(i);
            Journey newJourney = new Journey(journey.getCode(), journey.getFrom(), station.getName(), journey.getDate());
            TrainInfo newInfo = trainRepository.ticket(newJourney);

            if (newInfo == null) {//可能双车次号
                newJourney = new Journey(stations.get(0).getCode(), journey.getFrom(), station.getName(), journey.getDate());
                newInfo = trainRepository.ticket(newJourney);
            }

            if (newInfo == null) {//这个时候就真的没有了
                return null;
            }

            stationNum++;

            int num = TrainTicketUtils.ticketNum(newInfo);
            if (num > 0) {//第一个有票的站就返回
                return new TravelPlan(newInfo, BuyTicketMode.BUPIAO, stationNum);
            }
        }

        return null;
    }
}
