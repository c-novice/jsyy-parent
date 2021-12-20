package com.lzq.jsyy.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author lzq
 */
@ApiModel(description = "注册请求类")
@Data
@ToString
public class RegisterVo {
    @ApiModelProperty(value = "手机号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
