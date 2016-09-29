package org.leaf.train.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.leaf.train.entity.Journey;
import org.leaf.train.entity.Price;
import org.leaf.train.entity.Station;
import org.leaf.train.entity.TrainInfo;
import org.leaf.train.repository.TrainRepository;
import org.leaf.train.repository.converter.StationConverter;
import org.leaf.train.repository.converter.TrainInfoConverter;
import org.leaf.train.repository.model.TrainResponse;
import org.leaf.train.repository.model.TrainStation;
import org.leaf.train.repository.model.TrainTicket;
import org.leaf.train.utils.CacheUtils;
import org.leaf.train.utils.GetEx;
import org.leaf.train.utils.StationUtils;
import org.leaf.train.utils.TrainTicketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Repository;
import org.treeleaf.common.json.Jsoner;
import org.treeleaf.common.safe.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author leaf
 * @date 2016-09-29 10:40
 */
@Repository
public class TrainRepositoryImpl implements TrainRepository {

    private static Logger log = LoggerFactory.getLogger(TrainRepositoryImpl.class);

    private AtomicLong count = new AtomicLong();

    @Override
    public List<TrainInfo> tickets(Journey journey) {
        List<TrainInfo> trainTickets = CacheUtils.getTickets(journey);

        if (trainTickets == null) {
            String s = "https://kyfw.12306.cn/otn/lcxxcx/query?purpose_codes=ADULT&queryDate={}&from_station={}&to_station={}";
            FormattingTuple ft = MessageFormatter.arrayFormat(s, new Object[]{journey.getDate(), StationUtils.getTelecode(journey.getFrom()), StationUtils.getTelecode(journey.getTo())});
            String url = ft.getMessage();

            String r = new GetEx(url).send();
            log.info("第{}次查询", count.addAndGet(1));
            if ("-1".equals(r)) {//查询错误
                log.error("查询错误:{}", url);
                return new ArrayList<>(0);
            }

            try {
                TrainResponse trainResponse = Jsoner.toObj(r, TrainResponse.class);
                if (!trainResponse.getStatus()) {
                    trainTickets = new ArrayList<>(0);
                } else {
                    List<TrainTicket> list = Jsoner.toArray(Jsoner.toJson(trainResponse.getData().get("datas")), TrainTicket.class);

                    if (list == null) {//可能查询失败
                        return new ArrayList<>(0);
                    }

                    trainTickets = new ArrayList<>(list.size());
                    for (TrainTicket t : list) {
                        trainTickets.add(TrainInfoConverter.toTrainInfo(t));
                    }
                }

                CacheUtils.putTickets(journey, trainTickets);
            } catch (Exception e) {
                log.error("转义失败:{}, {}, {}", url, Jsoner.toJson(journey), r);
                throw new RuntimeException(e);
            }
        }

        if (StringUtils.isBlank(journey.getCode())) {
            return trainTickets;
        }

        for (TrainInfo trainInfo : trainTickets) {
            if (journey.getCode().equals(trainInfo.getCode())) {
                List<TrainInfo> list = new ArrayList<>(1);
                list.add(trainInfo);
                return list;
            }
        }

        return new ArrayList<>(0);
    }

    @Override
    public TrainInfo ticket(Journey journey) {
        List<TrainInfo> infos = this.tickets(journey);
        Assert.isTrue(infos.size() <= 1, "查询结果大于1:" + Jsoner.toJson(journey));
        return infos.size() == 1 ? infos.get(0) : null;
    }

    @Override
    public List<Station> stations(String trainNo, Journey journey) {
        List<Station> result = CacheUtils.getStations(trainNo, journey);
        if (result != null) {
            return result;
        }

        String s = "https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no={}&from_station_telecode={}&to_station_telecode={}&depart_date={}";
        FormattingTuple ft = MessageFormatter.arrayFormat(s, new Object[]{trainNo,
                StationUtils.getTelecode(journey.getFrom()), StationUtils.getTelecode(journey.getTo()), journey.getDate()});
        String url = ft.getMessage();

        String r = new GetEx(url).send();
        if ("-1".equals(r)) {//查询错误
            log.error("查询错误:{}", url);
            return new ArrayList<>(0);
        }

        TrainResponse trainResponse = Jsoner.toObj(r, TrainResponse.class);

        if (!trainResponse.getStatus()) {
            result = new ArrayList<>(0);
        } else {
            List<TrainStation> list = Jsoner.toArray(Jsoner.toJson(trainResponse.getData().get("data")), TrainStation.class);

            if (list == null) {//可能查询失败
                return new ArrayList<>(0);
            }

            result = new ArrayList<>(list.size());
            for (TrainStation t : list) {
                result.add(StationConverter.toStation(t));
            }
        }

        CacheUtils.putStations(trainNo, journey, result);//存到缓存里
        return result;
    }

    @Override
    public Price price(String trainNo, String fromTrainIndex, String toTrainIndex, String date) {

        Price price = CacheUtils.getPrice(trainNo, fromTrainIndex, toTrainIndex, date);
        if (price != null) {
            return price;
        }

        String s = "https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no={}&from_station_no={}&to_station_no={}&seat_types=OMO&train_date={}";
        FormattingTuple ft = MessageFormatter.arrayFormat(s, new Object[]{trainNo, fromTrainIndex, toTrainIndex, date});
        String url = ft.getMessage();
        log.info("chaxun ");
        String r = new GetEx(url).send();
        if ("-1".equals(r)) {//查询错误
            return null;
        }

        TrainResponse trainResponse = Jsoner.toObj(r, TrainResponse.class);
        if (!trainResponse.getStatus()) {
            return null;
        }

        Map data = trainResponse.getData();

        //格式统一为"¥98.5"
        price = new Price();
        price.setTranNo((String) data.get("train_no"));
        price.setEdzNum(TrainTicketUtils.toPrice((String) data.get("O")));
        price.setYdzNum(TrainTicketUtils.toPrice((String) data.get("M")));
        price.setWzNum(TrainTicketUtils.toPrice((String) data.get("WZ")));
        price.setSwzNum(TrainTicketUtils.toPrice((String) data.get("A9")));
        price.setGjrwNum(TrainTicketUtils.toPrice((String) data.get("A6")));
        price.setRwNum(TrainTicketUtils.toPrice((String) data.get("A4")));
        price.setYwNum(TrainTicketUtils.toPrice((String) data.get("A3")));
        price.setYzNum(TrainTicketUtils.toPrice((String) data.get("A1")));
        price.setTdzNum(TrainTicketUtils.toPrice((String) data.get("P")));

        CacheUtils.putPrice(trainNo, fromTrainIndex, toTrainIndex, date, price);

        return price;
    }
}
