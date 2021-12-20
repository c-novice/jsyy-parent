package com.lzq.jsyy.vo.cmn.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "修改设施vo类")
@ToString
public class FacilityUpdateVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "设施名称")
    private String name;

    @ApiModelProperty(value = "设施描述")
    private String description;
}
