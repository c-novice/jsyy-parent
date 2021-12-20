package com.lzq.jsyy.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 账号绑定查询类
 *
 * @author lzq
 */
@Data
@EqualsAndHashCode
@ApiModel(description = "账号绑定查询类")
@ToString
public class BindingVo {
    @ApiModelProperty(value = "学号")
    private String studentNumber;

    @ApiModelProperty(value = "校园通密码")
    private String password;
}
