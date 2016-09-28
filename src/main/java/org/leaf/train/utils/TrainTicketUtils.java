package org.leaf.train.utils;

import org.leaf.train.model.TrainTicket;

/**
 * @author yaoshuhong
 * @date 2016-09-27 17:37
 */
public class TrainTicketUtils {

    /**
     * 获取有座位的票数
     *
     * @param trainTicket
     * @return
     */
    public static int zwNum(TrainTicket trainTicket) {
        int num = toNum(trainTicket.getGg_num()) +
                toNum(trainTicket.getGr_num()) +
                toNum(trainTicket.getQt_num()) +
                toNum(trainTicket.getRw_num()) +
                toNum(trainTicket.getRz_num()) +
                toNum(trainTicket.getTz_num()) +
                toNum(trainTicket.getYb_num()) +
                toNum(trainTicket.getYw_num()) +
                toNum(trainTicket.getYz_num()) +
                toNum(trainTicket.getZe_num()) +
                toNum(trainTicket.getZy_num()) +
                toNum(trainTicket.getSwz_num());
        return num;
    }

    /**
     * 获取总票数
     *
     * @param trainTicket
     * @return
     */
    public static int ticketNum(TrainTicket trainTicket) {
        if (trainTicket == null) {
            System.out.println(trainTicket);
        }
        int num = toNum(trainTicket.getGg_num()) +
                toNum(trainTicket.getGr_num()) +
                toNum(trainTicket.getQt_num()) +
                toNum(trainTicket.getRw_num()) +
                toNum(trainTicket.getRz_num()) +
                toNum(trainTicket.getTz_num()) +
                toNum(trainTicket.getWz_num()) +
                toNum(trainTicket.getYb_num()) +
                toNum(trainTicket.getYw_num()) +
                toNum(trainTicket.getYz_num()) +
                toNum(trainTicket.getZe_num()) +
                toNum(trainTicket.getZy_num()) +
                toNum(trainTicket.getSwz_num());
        return num;
    }

    private static int toNum(String num) {
        // "--", "无", "*(表示还未开售)", 数字
        if ("--".equals(num)) {
            return 0;
        } else if ("无".equals(num)) {
            return 0;
        } else if ("*".equals(num)) {
            return 0;
        } else {
            return Integer.parseInt(num);
        }
    }
}
