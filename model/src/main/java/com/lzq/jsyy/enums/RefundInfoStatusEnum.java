package com.lzq.jsyy.enums;

/**
 * 退单枚举类
 *
 * @author lzq
 */
public enum RefundInfoStatusEnum {
    // 退款状态
    REFUNDING(1, "退款中"),
    REFUNDED(2, "已退款");

    private Integer status;
    private String name;

    RefundInfoStatusEnum(Integer status, String name) {
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
