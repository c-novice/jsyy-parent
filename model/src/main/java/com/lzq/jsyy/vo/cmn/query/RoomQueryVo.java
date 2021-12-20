package com.lzq.jsyy.vo.cmn.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "房间条件查询类")
@ToString
public class RoomQueryVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "设施编号")
    private String facilityId;

    @ApiModelProperty(value = "房间编号")
    private String roomId;

    @ApiModelProperty(value = "房间类型")
    private String type;

    @ApiModelProperty(value = "座位数下限")
    private Integer seatingLow;

    @ApiModelProperty(value = "座位数上限")
    private Integer seatingHigh;
}
