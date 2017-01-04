package org.leaf.train.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.leaf.train.entity.Journey;
import org.leaf.train.entity.Price;
import org.leaf.train.entity.Station;
import org.leaf.train.entity.TrainInfo;
import org.leaf.train.repository.converter.TrainInfoConverter;
import org.leaf.train.repository.model.ExTrainResponse;
import org.leaf.train.repository.model.TrainResponse;
import org.leaf.train.repository.model.TrainTicket;
import org.leaf.train.utils.CacheUtils;
import org.leaf.train.utils.GetEx;
import org.leaf.train.utils.StationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Repository;
import org.treeleaf.common.json.Jsoner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author leaf
 * @date 2017-01-03 12:04
 */
@Repository
public class QueryBuyApiTrainRepositoryImpl extends TrainRepositoryImpl {

    private Logger log = LoggerFactory.getLogger(QueryBuyApiTrainRepositoryImpl.class);

    private AtomicLong count = new AtomicLong();

    @Override
    public List<TrainInfo> tickets(Journey journey) {
        List<TrainInfo> trainTickets = CacheUtils.getTickets(journey);

        if (trainTickets == null) {
            String s = "https://kyfw.12306.cn/otn/leftTicket/queryA?leftTicketDTO.train_date={}&leftTicketDTO.from_station={}&leftTicketDTO.to_station={}&purpose_codes=ADULT";
            FormattingTuple ft = MessageFormatter.arrayFormat(s, new Object[]{journey.getDate(), StationUtils.getTelecode(journey.getFrom()), StationUtils.getTelecode(journey.getTo())});
            String url = ft.getMessage();

            String r = null;
            int num = 0;
            while (true) {
                try {
                    long t1 = System.currentTimeMillis();
                    r = new GetEx(url).send();
                    log.info("用时:{}", System.currentTimeMillis() - t1);
                    break;
                } catch (Exception e) {
                    num++;
                    log.info("第{}次失败:{}", num, e.getMessage());
                    if (num == 10) {
                        throw e;
                    }
                }
            }
            log.info("第{}次查询", count.addAndGet(1));
            if ("-1".equals(r)) {//查询错误
                log.error("查询错误:{}", url);
                return new ArrayList<>(0);
            }

            try {
                ExTrainResponse trainResponse = Jsoner.toObj(r, ExTrainResponse.class);
                if (!trainResponse.getStatus()) {
                    trainTickets = new ArrayList<>(0);
                } else {

                    trainTickets = new ArrayList<>(trainResponse.getData().length);

                    for (Map<String, Object> item : trainResponse.getData()) {
                        Object o = item.get("queryLeftNewDTO");
                        String json = Jsoner.toJson(o);
                        TrainTicket t = Jsoner.toObj(json, TrainTicket.class);
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
}
