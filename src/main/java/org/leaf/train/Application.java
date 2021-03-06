package org.leaf.train;

import org.leaf.train.entity.Journey;
import org.leaf.train.entity.Price;
import org.leaf.train.entity.TravelPlanCollection;
import org.leaf.train.service.TrainService;
import org.leaf.train.utils.PrintUtils;
import org.leaf.train.utils.StationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.treeleaf.common.safe.Assert;
import org.treeleaf.common.safe.AssertException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author leaf
 * @date 2016-09-27 14:47
 */
@RestController
@EnableWebSecurity
@SpringBootApplication
public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private TrainService trainService;

    /**
     * 查询有余票的列车信息
     *
     * @param journey
     * @return
     * @throws ParseException
     */
    @RequestMapping("search")
    public List<TravelPlanCollection> search(Journey journey) throws ParseException {

        Assert.notNull(journey.getFrom(), "出发地不能为空");
        Assert.notNull(journey.getTo(), "目的地不能为空");
        Assert.notNull(journey.getDate(), "出发日期不能为空");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            dateFormat.parse(journey.getDate());
        } catch (ParseException e) {
            log.warn("日期格式化失败:{}", journey.getDate());
            throw new AssertException("出发日期格式错误!!!");
        }

        Assert.notNull(StationUtils.getTelecode(journey.getFrom()), "出发地错误");
        Assert.notNull(StationUtils.getTelecode(journey.getTo()), "目的地错误");

        List<TravelPlanCollection> list = trainService.search(journey);
        list.forEach(e -> PrintUtils.print(e));

        return list;
    }

    /**
     * 查询价格
     *
     * @param trainNo 列车编号
     * @param fromNo  上车站点顺序编号
     * @param toNo    下车站顺序编号
     * @param date    日期(yyyy-mm-dd)
     * @return
     */
    @RequestMapping("price")
    public Price price(String trainNo, String fromNo, String toNo, String date) {
        Assert.notNull(trainNo, "列车编号不能为空");
        Assert.notNull(fromNo, "上车站点顺序编号不能为空");
        Assert.notNull(toNo, "下车站顺序编号不能为空");
        Assert.notNull(date, "日期不能为空");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            log.warn("日期格式化失败:{}", date);
            throw new AssertException("出发日期格式错误!!!");
        }

        Price price = trainService.price(trainNo, fromNo, toNo, date);
        if (price != null) {
            return price;
        } else {
            return new Price();
        }
    }

    public static void main(String[] args) throws ParseException {
        SpringApplication.run(Application.class, args);
        log.info("程序启动完毕!");
    }

}
