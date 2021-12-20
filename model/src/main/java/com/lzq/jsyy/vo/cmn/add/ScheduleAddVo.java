package com.lzq.jsyy.vo.cmn.add;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "添加预约排班vo类")
@ToString
public class ScheduleAddVo {
    @ApiModelProperty(value = "设施编号")
    private String facilityId;

    @ApiModelProperty(value = "房间编号")
    private String roomId;

    @ApiModelProperty(value = "开放预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String openDate;

    @ApiModelProperty(value = "截止预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String closeDate;

    @ApiModelProperty(value = "预约日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String workDate;

    @ApiModelProperty(value = "预约开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String beginTime;

    @ApiModelProperty(value = "预约结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String endTime;

    @ApiModelProperty(value = "退预约截止日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String quitDate;

    @ApiModelProperty(value = "预约费用")
    private BigDecimal amount;

    @ApiModelProperty(value = "该预约订单的最高级审批权限")
    private String lastPendingPermission;

    @ApiModelProperty(value = "是否已被预约")
    private Integer isOrdered;
}
