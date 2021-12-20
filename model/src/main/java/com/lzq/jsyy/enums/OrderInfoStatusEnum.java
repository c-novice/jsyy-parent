package com.lzq.jsyy.enums;

/**
 * 预约记录枚举类
 *
 * @author lzq
 */
public enum OrderInfoStatusEnum {
    // 预约记录状态
    LOSE_EFFICACY(1, "已失效"),
    PAYING(2, "支付中"),
    PENDING(3, "审批中"),
    ORDERED(4, "已预约"),
    REFUSED(5, "被拒绝");

    private Integer status;
    private String name;

    OrderInfoStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
