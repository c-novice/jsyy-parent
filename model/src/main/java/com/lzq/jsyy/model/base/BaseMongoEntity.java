package com.lzq.jsyy.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * mongodb对象基类
 *
 * @author lzq
 */
@Data
@ToString
@ApiModel("mongodb对象基类")
public class BaseMongoEntity implements Serializable {

    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除(1:已删除，0:未删除)")
    private Integer isDeleted;
}
