package com.lzq.jsyy.enums;

/**
 * 支付订单枚举类
 *
 * @author lzq
 */

public enum PaymentInfoStatusEnum {
    // 订单状态
    OVER_TIME(1, "已过期"),
    PAYING(2, "支付中"),
    PAYED(3, "已支付"),
    CANCELED(4, "已取消"),
    REFUND(5, "已退单"),
    REMAINING(6, "待支付");

    private Integer status;
    private String name;

    PaymentInfoStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public static String getStatusNameByStatus(Integer status) {
        AuthStatusEnum[] arrObj = AuthStatusEnum.values();
        for (AuthStatusEnum obj : arrObj) {
            if (status.intValue() == obj.getStatus().intValue()) {
                return obj.getName();
            }
        }
        return "";
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
