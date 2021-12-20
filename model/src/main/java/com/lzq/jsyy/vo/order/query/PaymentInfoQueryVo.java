package com.lzq.jsyy.vo.order.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@ApiModel(description = "支付记录条件查询类")
@Data
@ToString
public class PaymentInfoQueryVo {
    @ApiModelProperty(value = "订单编号")
    private String orderId;

    @ApiModelProperty(value = "支付状态")
    private Integer paymentStatus;

    @ApiModelProperty(value = "回调时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String callbackTime;
}
