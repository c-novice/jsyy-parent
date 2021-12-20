package com.lzq.jsyy.common.result;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 *
 * @author lzq
 */
@Getter
public enum ResultCodeEnum {
    // 返回结果状态枚举
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    PARAM_ERROR(202, "参数不正确"),
    SERVICE_ERROR(203, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    USER_REPEAT(205, "该用户已存在"),
    DELETE_FAIL(206, "删除失败"),
    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
    CODE_ERROR(210, "验证码错误"),
    LOGIN_USER_ERROR(211, "账号或密码错误"),
    LOGIN_PHONE_ERROR(212, "该手机号未注册"),
    REGISTER_PHONE_ERROR(213, "手机号已被使用"),
    LOGIN_ERROR_MULTI(214, "最近登录失败次数过多，请稍后再试"),
    LOGIN_ACL(215, "没有权限"),
    PERMISSION_ADD_ERROR(216, "添加权限失败"),
    PERMISSION_EXIST(217, "该权限已存在"),
    FACILITY_ADD_ERROR(218, "添加设施失败"),
    FACILITY_CHANGE_ERROR(219, "修改设施失败"),
    ROOM_ADD_ERROR(220, "添加房间失败"),
    ROOM_CHANGE_ERROR(221, "修改房间失败"),
    SCHEDULE_ADD_ERROR(222, "添加排班失败"),
    SCHEDULE_CHANGE_ERROR(223, "修改排班失败"),
    REGISTER_USERNAME_ERROR(224, "注册用户已存在"),
    REGISTER_ERROR(225, "修改排班失败"),
    BINDING_ERROR(226, "绑定失败"),
    FACILITY_GET_ERROR(227, "设施信息获取失败"),
    ROOM_GET_ERROR(228, "教室信息获取失败"),
    SCHEDULE_GET_ERROR(229, "预约信息获取失败"),
    PERMISSION_GET_ERROR(230, "权限获取失败"),
    ORDER_ADD_ERROR(231, "预约失败"),
    ORDER_CHANGE_ERROR(232, "修改预约失败"),
    PAYMENT_ADD_ERROR(233, "账单添加失败"),
    PAY_SUCCESS(234, "支付成功"),
    PAYING(235, "支付中"),
    PAY_ERROR(236, "支付失败"),
    PENDING_ERROR(237, "审批失败");

    private final Integer code;
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
