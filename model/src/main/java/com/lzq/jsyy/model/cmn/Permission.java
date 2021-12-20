package com.lzq.jsyy.model.cmn;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzq.jsyy.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 权限
 *
 * @author lzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "权限实体类")
@TableName("permission")
@ToString
public class Permission extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户类型")
    private String type;

    @ApiModelProperty(value = "权限名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "父权限")
    @TableField("father")
    private String father;
}
