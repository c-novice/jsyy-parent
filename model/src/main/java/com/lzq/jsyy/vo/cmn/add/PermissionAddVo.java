package com.lzq.jsyy.vo.cmn.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "添加权限vo类")
@ToString
public class PermissionAddVo {
    @ApiModelProperty(value = "用户类型")
    private String type;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "父权限名称")
    private String father;
}
