package com.lzq.jsyy.vo.order.add;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@Data
@ToString
@ApiModel(description = "预约订单添加vo类")
public class OrderInfoAddVo {
    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "设施编号")
    @TableField("facility_id")
    private String facilityId;

    @ApiModelProperty(value = "房间编号")
    @TableField("room_id")
    private String roomId;

    @ApiModelProperty(value = "预约排班编号")
    @TableField("schedule_id")
    private String scheduleId;

    @ApiModelProperty(value = "该预约订单的最高级审批权限")
    @TableField("last_pending_permission")
    private String lastPendingPermission;
}
