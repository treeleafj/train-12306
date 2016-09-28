package org.leaf.train.model;

/**
 * Created by yaoshuhong on 2016/9/28 028.
 */
public class BuyTicketType {

    public static final BuyTicketType NONE = new BuyTicketType("1", "正常购票");
    public static final BuyTicketType WANGHOU = new BuyTicketType("2", "往后买");
    public static final BuyTicketType WANGQIAN = new BuyTicketType("3", "往前买");
    public static final BuyTicketType BUPIAO = new BuyTicketType("4", "上车补票");

    private String value;
    private String desc;

    public BuyTicketType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
