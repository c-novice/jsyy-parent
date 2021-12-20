package com.lzq.jsyy.common.exception;

import com.lzq.jsyy.common.result.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 自定义全局异常类
 *
 * @author lzq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class JsyyException extends RuntimeException {
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param message
     * @param code
     */
    public JsyyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public JsyyException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
