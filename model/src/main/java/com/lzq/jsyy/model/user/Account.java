package com.lzq.jsyy.model.user;

import com.lzq.jsyy.model.base.BaseMongoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author lzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "校园账号信息实体类")
@Document("account")
@ToString
public class Account extends BaseMongoEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学号")
    @Indexed
    private String studentNumber;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户类型")
    private String type;

    @ApiModelProperty(value = "学生姓名")
    private String name;
}
