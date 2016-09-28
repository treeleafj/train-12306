package org.leaf.train;

import org.leaf.train.model.Journey;
import org.leaf.train.model.SearchResult;
import org.leaf.train.service.TrainService;
import org.leaf.train.service.impl.TrainServiceImpl;
import org.leaf.train.utils.PrintUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.treeleaf.common.safe.Assert;
import org.treeleaf.common.safe.AssertException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author yaoshuhong
 * @date 2016-09-27 14:47
 */
@Controller
//@EnableWebSecurity
@SpringBootApplication
public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Value("${security.user.name}")
    private String username;

    @Value("${security.user.password}")
    private String password;

    @ResponseBody
    @RequestMapping("search")
    public Object search(String from, String to, String date) throws ParseException {

        Assert.notNull(from, "出发地不能为空");
        Assert.notNull(to, "目的地不能为空");
        Assert.notNull(date, "出发日期不能为空");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            log.warn("日期格式化失败:{}", date);
            throw new AssertException("出发日期格式错误!!!");
        }

        Journey journey = new Journey(from, to, date);
        TrainService trainService = new TrainServiceImpl();

        List<SearchResult> list = trainService.search(journey);
        list.forEach(e -> PrintUtils.print(e));

        return list;
    }

    public static void main(String[] args) throws ParseException {
        SpringApplication.run(Application.class, args);
    }

}
