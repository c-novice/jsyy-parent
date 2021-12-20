package com.lzq.jsyy.vo.msm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 短信实体类
 *
 * @author lzq
 */
@Data
@ApiModel(description = "短信实体类")
@ToString
public class MsmVo implements Serializable {

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "短信模板id")
    private String templateId;

    @ApiModelProperty(value = "短信模板参数")
    private String[] params;
}
