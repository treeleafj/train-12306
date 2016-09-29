package org.leaf.train.repository.converter;

import org.leaf.train.entity.TrainInfo;
import org.leaf.train.repository.model.TrainTicket;
import org.leaf.train.utils.TrainTicketUtils;

/**
 * @author yaoshuhong
 * @date 2016-09-29 11:13
 */
public class TrainInfoConverter {

    public static TrainInfo toTrainInfo(TrainTicket trainTicket) {
        TrainInfo info = new TrainInfo();
        info.setTrainNo(trainTicket.getTrain_no())
                .setCode(trainTicket.getStation_train_code())
                .setStartStationTeleCode(trainTicket.getStart_station_telecode())
                .setStartStationName(trainTicket.getStart_station_name())
                .setEndStationTeleCode(trainTicket.getEnd_station_telecode())
                .setEndStationName(trainTicket.getEnd_station_name())
                .setFromStationTeleCode(trainTicket.getFrom_station_telecode())
                .setFromStationName(trainTicket.getFrom_station_name())
                .setToStationTeleCode(trainTicket.getTo_station_telecode())
                .setToStationName(trainTicket.getTo_station_name())
                .setStartTime(trainTicket.getStart_time())
                .setArriveTime(trainTicket.getArrive_time())
                .setClassName(trainTicket.getTrain_class_name())
                .setLishi(trainTicket.getLishi())
                .setCanWebBuy("Y".equals(trainTicket.getCanWebBuy()))
                .setSaleTime(trainTicket.getSale_time())
                .setNote(trainTicket.getNote())
                .setControlled("1".equals(trainTicket.getControlled_train_flag()))
                .setControlledMessage(trainTicket.getControlled_train_message())
                .setGjrwNum(TrainTicketUtils.toNum(trainTicket.getGr_num()))
                .setRwNum(TrainTicketUtils.toNum(trainTicket.getRw_num()))
                .setRzNum(TrainTicketUtils.toNum(trainTicket.getRz_num()))
                .setWzNum(TrainTicketUtils.toNum(trainTicket.getWz_num()))
                .setYwNum(TrainTicketUtils.toNum(trainTicket.getYw_num()))
                .setYzNum(TrainTicketUtils.toNum(trainTicket.getYz_num()))
                .setEdzNum(TrainTicketUtils.toNum(trainTicket.getZe_num()))
                .setYdzNum(TrainTicketUtils.toNum(trainTicket.getZy_num()))
                .setSwzNum(TrainTicketUtils.toNum(trainTicket.getSwz_num()));
        return info;
    }

}
