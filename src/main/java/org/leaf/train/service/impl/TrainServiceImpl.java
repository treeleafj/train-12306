package org.leaf.train.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.leaf.train.model.BuyTicketType;
import org.leaf.train.model.Journey;
import org.leaf.train.model.SearchResult;
import org.leaf.train.model.TicketResult;
import org.leaf.train.model.TrainResponse;
import org.leaf.train.model.TrainStation;
import org.leaf.train.model.TrainTicket;
import org.leaf.train.service.TrainService;
import org.leaf.train.utils.CacheUtils;
import org.leaf.train.utils.GetEx;
import org.leaf.train.utils.StationUtils;
import org.leaf.train.utils.TrainTicketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.treeleaf.common.json.Jsoner;
import org.treeleaf.common.safe.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yaoshuhong
 * @date 2016-09-27 15:10
 */
public class TrainServiceImpl implements TrainService {

    private static Logger log = LoggerFactory.getLogger(TrainServiceImpl.class);

    private AtomicLong count = new AtomicLong();

    @Override
    public List<TrainTicket> tickets(Journey journey) {

        List<TrainTicket> trainTickets = CacheUtils.getTickets(journey);
        if (trainTickets == null) {
            String s = "https://kyfw.12306.cn/otn/lcxxcx/query?purpose_codes=ADULT&queryDate={}&from_station={}&to_station={}";
            FormattingTuple ft = MessageFormatter.arrayFormat(s, new Object[]{journey.getDate(), StationUtils.getTelecode(journey.getFrom()), StationUtils.getTelecode(journey.getTo())});
            String url = ft.getMessage();

            String r = new GetEx(url).send();
            log.info("第{}次查询", count.addAndGet(1));

            if ("-1".equals(r)) {//查询错误
                log.error("查询错误:{}", url);
                return Collections.emptyList();
            }

            try {
                TrainResponse trainResponse = Jsoner.toObj(r, TrainResponse.class);
                if (!trainResponse.getStatus()) {
                    trainTickets = Collections.emptyList();
                } else {
                    trainTickets = Jsoner.toArray(Jsoner.toJson(trainResponse.getData().get("datas")), TrainTicket.class);
                }

                if (trainTickets == null) {//可能查询失败
                    return Collections.emptyList();
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
        for (TrainTicket ticket : trainTickets) {
            if (journey.getCode().equals(ticket.getStation_train_code())) {
                List<TrainTicket> list = new ArrayList<>(1);
                list.add(ticket);
                return list;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public TrainTicket ticket(Journey journey) {
        List<TrainTicket> tickets = this.tickets(journey);
        Assert.isTrue(tickets.size() <= 1, "查询结果大于1:" + Jsoner.toJson(journey));
        return tickets.size() == 1 ? tickets.get(0) : null;
    }

    @Override
    public List<TrainStation> stations(String trainNo, Journey journey) {

        List<TrainStation> result = CacheUtils.getStations(trainNo, journey);
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
            return Collections.emptyList();
        }

        TrainResponse trainResponse = Jsoner.toObj(r, TrainResponse.class);

        if (!trainResponse.getStatus()) {
            result = Collections.emptyList();
        } else {
            result = Jsoner.toArray(Jsoner.toJson(trainResponse.getData().get("data")), TrainStation.class);
        }

        CacheUtils.putStations(trainNo, journey, result);//存到缓存里

        return result;
    }

    @Override
    public List<SearchResult> search(Journey journey) {
        List<TrainTicket> list = this.tickets(journey);

        List<SearchResult> result = new ArrayList<>();

        for (TrainTicket trainTicket : list) {
            int num = TrainTicketUtils.ticketNum(trainTicket);

            if (num > 0) {
                addSearchResult(result, trainTicket, BuyTicketType.NONE);
            } else {
                //当前票务的站点旅程信息
                Journey nowJourney = new Journey(trainTicket.getStation_train_code(), trainTicket.getFrom_station_name(),
                        trainTicket.getTo_station_name(), journey.getDate());

                TrainTicket t;

                t = searchBack(nowJourney);//只往后查询多买几站

                if (t != null) {
                    addSearchResult(result, t, BuyTicketType.WANGHOU);
                }

                t = FrontCenter(nowJourney);//只中途上车补票
                if (t != null) {
                    addSearchResult(result, t, BuyTicketType.BUPIAO);
                }

                t = searchFront(nowJourney);//只往前查询
                if (t != null) {
                    addSearchResult(result, t, BuyTicketType.WANGQIAN);
                }
            }
        }

        return result;
    }

    @Override
    public List<SearchResult> searchNone(Journey journey) {
        List<TrainTicket> list = this.tickets(journey);

        List<SearchResult> result = new ArrayList<>();

        for (TrainTicket trainTicket : list) {
            int num = TrainTicketUtils.ticketNum(trainTicket);

            if (num > 0) {
                addSearchResult(result, trainTicket, BuyTicketType.NONE);
            }
        }

        return result;
    }

    private void addSearchResult(List<SearchResult> searchResults, TrainTicket trainTicket, BuyTicketType type) {

        SearchResult searchResult = null;
        for (SearchResult result : searchResults) {
            if (result.getTrainCode().equals(trainTicket.getStation_train_code())) {
                searchResult = result;
                break;
            }
        }

        if (searchResult == null) {
            searchResult = new SearchResult();
            searchResult.setDate(trainTicket.getStart_train_date());
            searchResult.setTrainCode(trainTicket.getStation_train_code());
            searchResults.add(searchResult);
        }

        TicketResult ticketResult = new TicketResult();
        ticketResult.setFromStationName(trainTicket.getFrom_station_name());
        ticketResult.setToStationName(trainTicket.getTo_station_name());
        ticketResult.setArriveTime(trainTicket.getArrive_time());
        ticketResult.setStartTime(trainTicket.getStart_time());
        ticketResult.setSwzNum(trainTicket.getSwz_num());
        ticketResult.setYdzNum(trainTicket.getZy_num());
        ticketResult.setEdzNum(trainTicket.getZe_num());
        ticketResult.setWzNum(trainTicket.getWz_num());
        ticketResult.setType(type);
        searchResult.getList().add(ticketResult);
    }

    @Override
    public TrainTicket searchFront(Journey journey) {

        TrainTicket ticket = this.ticket(journey);
        if (ticket == null) {
            log.error("站点票务信息查询不到:{}", Jsoner.toJson(journey));
            return null;
        }

        List<TrainStation> stations = this.stations(ticket.getTrain_no(), journey);
        if (stations.size() < 2) {
            return null;
        }

        //往前查,起点是上车站往前变,终点是下车站不变
        int start = 0;
        for (int i = 1; i < stations.size(); i++) {
            if (stations.get(i).getStation_name().equals(journey.getFrom())) {
                start = i;
                break;
            }
        }

        for (int i = start - 1; i >= 0; i--) {//倒着查询是否可以往前买
            TrainStation station = stations.get(i);
            Journey newJourney = new Journey(journey.getCode(), station.getStation_name(), journey.getTo(), journey.getDate());
            TrainTicket newTicket = ticket(newJourney);
            if (newTicket == null) {//可能双车次号
                newJourney = new Journey(stations.get(0).getStation_train_code(), station.getStation_name(), journey.getTo(), journey.getDate());
                newTicket = ticket(newJourney);
            }

            if (newTicket == null) {//这个时候就真的没有了
                return null;
            }

            int num = TrainTicketUtils.ticketNum(newTicket);
            if (num > 0) {//第一个有票的站就返回
                return newTicket;
            }
        }

        return null;
    }

    @Override
    public TrainTicket searchBack(Journey journey) {

        TrainTicket ticket = this.ticket(journey);
        if (ticket == null) {
            log.error("站点票务信息查询不到:{}", Jsoner.toJson(journey));
            return null;
        }

        List<TrainStation> stations = this.stations(ticket.getTrain_no(), journey);
        if (stations.size() < 2) {
            return null;
        }

        //只往后,不中途补票, 起点是上车站不变, 终点是下车站往后一直变
        int start = 1;
        for (int i = 1; i < stations.size(); i++) {
            if (stations.get(i).getStation_name().equals(journey.getTo())) {
                start = i;
                break;
            }
        }

        for (int i = start + 1; i < stations.size(); i++) {
            TrainStation station = stations.get(i);

            Journey newJourney = new Journey(journey.getCode(), journey.getFrom(), station.getStation_name(), journey.getDate());
            TrainTicket newTicket = this.ticket(newJourney);

            if (newTicket == null) {//可能双车次号,那么用另外一个次车号去查询
                newJourney = new Journey(stations.get(0).getStation_train_code(), journey.getFrom(), station.getStation_name(), journey.getDate());
                newTicket = ticket(newJourney);
            }

            if (newTicket == null) {
                continue;//如果还是查不到,说明这个站到达不了,有些站就是这样只能经过,不能上车
            }

            int num = TrainTicketUtils.ticketNum(newTicket);
            if (num > 0) {//第一个有票的站就返回
                return newTicket;
            }
        }

        return null;
    }

    @Override
    public TrainTicket FrontCenter(Journey journey) {

        TrainTicket ticket = this.ticket(journey);
        if (ticket == null) {
            log.error("站点票务信息查询不到:{}", Jsoner.toJson(journey));
            return null;
        }

        List<TrainStation> stations = this.stations(ticket.getTrain_no(), journey);
        if (stations.size() < 2) {
            return null;
        }

        //只中途补票, 起点是上车站不变, 终点是下车站往前变
        int end = 1;//下车站位置
        int start = 0;//上车站位置
        for (int i = 1; i < stations.size(); i++) {
            if (stations.get(i).getStation_name().equals(journey.getTo())) {
                end = i;
            }

            if (stations.get(i).getStation_name().equals(journey.getFrom())) {
                start = i;
            }
        }

        if (end <= 1) {//如果第二个站级是目的,那么就没必要中途补票了...
            return null;
        }

        for (int i = end - 1; i > start; i--) {//倒着查询是否可以中途补票
            TrainStation station = stations.get(i);
            Journey newJourney = new Journey(journey.getCode(), journey.getFrom(), station.getStation_name(), journey.getDate());
            TrainTicket newTicket = ticket(newJourney);

            if (newTicket == null) {//可能双车次号
                newJourney = new Journey(stations.get(0).getStation_train_code(), journey.getFrom(), station.getStation_name(), journey.getDate());
                newTicket = ticket(newJourney);
            }

            if (newTicket == null) {//这个时候就真的没有了
                return null;
            }

            int num = TrainTicketUtils.ticketNum(newTicket);
            if (num > 0) {//第一个有票的站就返回
                return newTicket;
            }
        }

        return null;
    }
}
