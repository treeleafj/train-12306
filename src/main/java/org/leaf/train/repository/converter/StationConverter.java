package org.leaf.train.repository.converter;

import org.leaf.train.entity.Station;
import org.leaf.train.repository.model.TrainStation;

/**
 * @author leaf
 * @date 2016-09-29 10:55
 */
public class StationConverter {

    public static Station toStation(TrainStation trainStation) {
        Station station = new Station();
        station.setCode(trainStation.getStation_train_code())
                .setName(trainStation.getStation_name())
                .setIndex(Integer.parseInt(trainStation.getStation_no()))
                .setStopoverTime(trainStation.getStopover_time())
                .setArriveTime(trainStation.getArrive_time())
                .setStartTime(trainStation.getStart_time());
        return station;
    }

}
