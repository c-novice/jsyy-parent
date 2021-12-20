package com.lzq.jsyy.model.cmn;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lzq.jsyy.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 预约排班
 *
 * @author lzq
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "预约排班实体类")
@Data
@ToString
public class Schedule extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设施编号")
    @TableField("facility_id")
    private String facilityId;

    @ApiModelProperty(value = "房间编号")
    @TableField("room_id")
    private String roomId;

    @ApiModelProperty(value = "开放预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("open_date")
    private String openDate;

    @ApiModelProperty(value = "截止预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("close_date")
    private String closeDate;

    @ApiModelProperty(value = "预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("work_date")
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

    @ApiModelProperty(value = "预约费用")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "该预约订单的最高级审批权限")
    @TableField("last_pending_permission")
    private String lastPendingPermission;

    @ApiModelProperty(value = "是否已被预约")
    @TableField("is_ordered")
    private Integer isOrdered;
}
