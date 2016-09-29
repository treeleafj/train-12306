package org.leaf.train.entity;

/**
 * Created by leaf on 2016/9/28 028.
 */
public class BuyTicketMode {

    public static final BuyTicketMode NONE = new BuyTicketMode("1", "正常购票");
    public static final BuyTicketMode WANGHOU = new BuyTicketMode("2", "往后买");
    public static final BuyTicketMode WANGQIAN = new BuyTicketMode("3", "往前买");
    public static final BuyTicketMode BUPIAO = new BuyTicketMode("4", "上车补票");

    /**
     * 类型
     */
    private String value;

    /**
     * 描述
     */
    private String desc;

    public BuyTicketMode(String value, String desc) {
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
