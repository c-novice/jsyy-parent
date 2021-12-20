package com.lzq.jsyy.model.cmn;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lzq.jsyy.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 房间
 *
 * @author lzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "房间实体类")
@ToString
public class Room extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设施编号")
    @TableField("facility_id")
    private String facilityId;

    @ApiModelProperty(value = "房间编号")
    @TableField("room_id")
    private String roomId;

    @ApiModelProperty(value = "房间类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "座位数")
    @TableField("seating")
    private Integer seating;

    @ApiModelProperty(value = "房间描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "预约排班")
    @TableField(exist = false)
    private List<Schedule> schedules;
}
