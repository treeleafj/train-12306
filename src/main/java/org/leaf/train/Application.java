package org.leaf.train;

import org.leaf.train.model.Journey;
import org.leaf.train.model.TrainSite;
import org.leaf.train.model.TrainTicket;
import org.leaf.train.service.TrainService;
import org.leaf.train.service.impl.TrainServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.treeleaf.common.json.Jsoner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yaoshuhong
 * @date 2016-09-27 14:47
 */
public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String from = "IOQ";
        String to = "CBQ";
        Date date = dateFormat.parse("2016-10-28");
        Journey journey = new Journey(from, to, date);
        TrainService trainService = new TrainServiceImpl();

        List<TrainTicket> list =  trainService.findTicket(journey);

        log.info(Jsoner.toJson(list));
    }

}
