package com.lzq.jsyy.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lzq.jsyy.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 预约订单
 *
 * @author lzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "预约订单实体类")
@TableName("order_info")
@ToString
public class OrderInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "设施编号")
    @TableField("facility_id")
    private String facilityId;

    @ApiModelProperty(value = "订单交易号")
    @TableField("out_trade_no")
    private String outTradeNo;

    @ApiModelProperty(value = "房间编号")
    @TableField("room_id")
    private String roomId;

    @ApiModelProperty(value = "预约排班编号")
    @TableField("schedule_id")
    private String scheduleId;

    @ApiModelProperty(value = "预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String workDate;

    @ApiModelProperty(value = "预约开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @TableField("begin_time")
    private String beginTime;

    @ApiModelProperty(value = "预约结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @TableField("end_time")
    private String endTime;

    @ApiModelProperty(value = "退预约截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("quit_date")
    private String quitDate;

    @ApiModelProperty(value = "订单状态")
    @TableField("order_status")
    private Integer orderStatus;

    @ApiModelProperty(value = "预约费用")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "最后一个审批人的用户名")
    @TableField("last_pending_username")
    private String lastPendingUsername;

    @ApiModelProperty(value = "下一个审批人应具有的权限")
    @TableField("next_need_permission")
    private String nextNeedPermission;

    @ApiModelProperty(value = "该预约订单的最高级审批权限")
    @TableField("last_pending_permission")
    private String lastPendingPermission;
}
