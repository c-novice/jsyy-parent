package com.lzq.jsyy.vo.user.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@Data
@ApiModel(description = "用户条件查询类")
@ToString
public class UserQueryVo {
    @ApiModelProperty(value = "学号")
    private String studentNumber;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户类型")
    private String type;
}
