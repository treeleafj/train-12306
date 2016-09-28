package org.leaf.train.model;

/**
 * Created by yaoshuhong on 2016/9/28 028.
 */
public enum BuyTicketType {

    NONE("1", "正常购票"),
    WANGHOU("2", "往后买"),
    WANGQIAN("3", "往前买"),
    BUPIAO("4", "上车补票");

    private String value;
    private String desc;

    BuyTicketType(String value, String desc) {
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
