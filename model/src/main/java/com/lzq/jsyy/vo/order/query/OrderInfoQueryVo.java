package com.lzq.jsyy.vo.order.query;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@ApiModel(description = "订单条件查询类")
@Data
@ToString
public class OrderInfoQueryVo {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "设施编号")
    private String facilityId;

    @ApiModelProperty(value = "房间编号")
    private String roomId;

    @ApiModelProperty(value = "预约排班编号")
    @TableField("schedule_id")
    private String scheduleId;

    @ApiModelProperty(value = "预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String workDate;

    @ApiModelProperty(value = "订单状态")
    @TableField("order_status")
    private Integer orderStatus;

    @ApiModelProperty(value = "最后一个审批人的用户名")
    @TableField("last_pending_username")
    private String lastPendingUsername;

    @ApiModelProperty(value = "该预约订单的最高级审批权限")
    @TableField("last_pending_permission")
    private String lastPendingPermission;
}
