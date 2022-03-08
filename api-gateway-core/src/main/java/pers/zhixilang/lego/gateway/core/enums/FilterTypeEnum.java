package pers.zhixilang.lego.gateway.core.enums;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-23 16:36
 */
public enum  FilterTypeEnum {
    PRE("pre"),
    ROUTE("route"),
    POST("post");

    private String type;

    FilterTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }}
