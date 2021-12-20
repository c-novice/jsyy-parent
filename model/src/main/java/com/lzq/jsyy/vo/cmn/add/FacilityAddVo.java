package com.lzq.jsyy.vo.cmn.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "添加设施vo类")
@ToString
public class FacilityAddVo {
    @ApiModelProperty(value = "设施名称")
    private String name;

    @ApiModelProperty(value = "设施描述")
    private String description;
}
