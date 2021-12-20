package com.lzq.jsyy.vo.cmn.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@Data
@ToString
@ApiModel(description = "修改权限vo类")
public class PermissionUpdateVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "用户类型")
    private String type;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "父权限名称")
    private String father;
}
