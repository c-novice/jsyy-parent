package com.lzq.jsyy.vo.user.update;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@ApiModel(description = "修改用户vo类")
@Data
@ToString
public class UserUpdateVo {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "权限")
    private String permission;

    @ApiModelProperty(value = "学号")
    private String studentNumber;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "用户类型")
    private String type;
}
