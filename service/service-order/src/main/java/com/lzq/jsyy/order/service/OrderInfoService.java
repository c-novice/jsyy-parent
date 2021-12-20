package com.lzq.jsyy.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.vo.order.add.OrderInfoAddVo;
import com.lzq.jsyy.vo.order.query.OrderInfoQueryVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 条件分页查询
     *
     * @param pageParam
     * @param orderInfoQueryVo
     * @return
     */
    Page<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);

    /**
     * 添加预约订单
     *
     * @param orderInfoAddVo
     * @return
     */
    Map<String, Object> add(OrderInfoAddVo orderInfoAddVo);


    /**
     * 删除预约订单记录
     *
     * @param outTradeNo
     * @return
     */
    boolean delete(String outTradeNo);

    /**
     * 根据对外业务编号查询订单
     *
     * @param outTradeNo
     * @return
     */
    OrderInfo getByOutTradeNo(String outTradeNo);

    /**
     * 查询待处理的预约记录
     *
     * @param pageParam
     * @param permissionName
     * @return
     */
    Page<OrderInfo> selectPendingOrder(Page<OrderInfo> pageParam, String permissionName);

    /**
     * 审批订单
     *
     * @param username
     * @param outTradeNo
     * @param status
     * @return
     */
    Map<String, Object> pending(String username, String outTradeNo, Integer status);

    /**
     * 预约提醒
     */
    void orderTips();
}
