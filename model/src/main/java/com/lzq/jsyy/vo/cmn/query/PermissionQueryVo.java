package com.lzq.jsyy.vo.cmn.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "权限查询条件类")
@ToString
public class PermissionQueryVo {
    @ApiModelProperty(value = "用户类型")
    private String type;

    @ApiModelProperty(value = "权限名称")
    private String name;
}
