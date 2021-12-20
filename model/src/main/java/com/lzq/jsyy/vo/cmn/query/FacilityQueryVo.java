package com.lzq.jsyy.vo.cmn.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "设施条件查询类")
@ToString
public class FacilityQueryVo {
    @ApiModelProperty(value = "设施编号")
    private String id;

    @ApiModelProperty(value = "设施名称")
    private String name;
}
