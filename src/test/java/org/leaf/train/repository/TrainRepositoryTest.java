package org.leaf.train.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.leaf.train.entity.Journey;
import org.leaf.train.entity.TrainInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Administrator on 2017/1/3 003.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TrainRepositoryTest {

    @Autowired
    private TrainRepository trainRepository;

    @Test
    public void tickets() throws Exception {
        Journey journey = new Journey();
        journey.setFrom("深圳北");
        journey.setTo("潮阳");
        journey.setDate("2017-01-23");
        List<TrainInfo> tickets = trainRepository.tickets(journey);
        System.out.println(tickets);
    }

}