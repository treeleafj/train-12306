package org.leaf.train.service.impl;

import org.leaf.train.model.Journey;
import org.leaf.train.model.TrainResponse;
import org.leaf.train.model.TrainSite;
import org.leaf.train.model.TrainTicket;
import org.leaf.train.service.TrainService;
import org.leaf.train.utils.GetEx;
import org.leaf.train.utils.StationUtils;
import org.leaf.train.utils.TrainTicketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.treeleaf.common.json.Jsoner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author yaoshuhong
 * @date 2016-09-27 15:10
 */
public class TrainServiceImpl implements TrainService {

    private static Logger log = LoggerFactory.getLogger(TrainServiceImpl.class);

    public List<TrainTicket> tickets(Journey journey) {
        String s = "https://kyfw.12306.cn/otn/lcxxcx/query?purpose_codes=ADULT&queryDate={}&from_station={}&to_station={}";
        FormattingTuple ft = MessageFormatter.arrayFormat(s, new Object[]{journey.getDate(), journey.getFrom(), journey.getTo()});
        String url = ft.getMessage();

        String r = new GetEx(url).send();

        TrainResponse trainResponse = Jsoner.toObj(r, TrainResponse.class);

        if (!trainResponse.getStatus()) {
            return Collections.emptyList();
        }

        return Jsoner.toArray(Jsoner.toJson(trainResponse.getData().get("datas")), TrainTicket.class);
    }

    public TrainTicket ticket(String trainCode, Journey journey) {
        List<TrainTicket> tickets = this.tickets(journey);
        for (TrainTicket ticket : tickets) {
            if (trainCode.equals(ticket.getStation_train_code())) {
                return ticket;
            }
        }
        return null;
    }

    public List<TrainSite> sites(String trainNo, Journey journey) {
        String s = "https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no={}&from_station_telecode={}&to_station_telecode={}&depart_date={}";
        FormattingTuple ft = MessageFormatter.arrayFormat(s, new Object[]{trainNo, journey.getFrom(), journey.getTo(), journey.getDate()});
        String url = ft.getMessage();

        String r = new GetEx(url).send();

        TrainResponse trainResponse = Jsoner.toObj(r, TrainResponse.class);

        if (!trainResponse.getStatus()) {
            return Collections.emptyList();
        }

        return Jsoner.toArray(Jsoner.toJson(trainResponse.getData().get("data")), TrainSite.class);
    }

    public List<TrainTicket> findTicket(Journey journey) {
        List<TrainTicket> list = this.tickets(journey);

        List<TrainTicket> result = new ArrayList<TrainTicket>();
        for (TrainTicket trainTicket : list) {
            int num = TrainTicketUtils.ticketNum(trainTicket);
            if (num > 0) {
                print(trainTicket);
                result.add(trainTicket);
            } else {
                f(result, trainTicket, journey);//不然就进入递归查询中途能否上车
            }
        }

        return result;
    }

    public void f(List<TrainTicket> result, TrainTicket trainTicket, Journey journey) {
        List<TrainSite> sites = this.sites(trainTicket.getTrain_no(), journey);
        if (sites.size() < 2) {
            return;
        }

        for (int i = 1; i < sites.size(); i++) {
            TrainSite site = sites.get(i);
            String start_teleCode = StationUtils.getTelecode(sites.get(0).getStart_station_name());
            String to_teleCode = StationUtils.getTelecode(site.getStation_name());
            Journey newJourney = new Journey(start_teleCode, to_teleCode, journey.getDate());
            TrainTicket newTicket = ticket(sites.get(0).getStation_train_code(), newJourney);

            int num = TrainTicketUtils.ticketNum(newTicket);
            if (num > 0) {
                print(newTicket);
                result.add(newTicket);
            }
        }
    }

    void print(TrainTicket trainTicket) {
        int num = TrainTicketUtils.ticketNum(trainTicket);
        log.info("车次:{}, {}->{}, 剩余总票数:{}, 商务座:{},  一等座:{}, 二等座:{}, 无座:{}",
                trainTicket.getStation_train_code(), trainTicket.getFrom_station_name(), trainTicket.getTo_station_name(), num,
                trainTicket.getSwz_num(), trainTicket.getZy_num(), trainTicket.getZe_num(), trainTicket.getWz_num());
    }
}
