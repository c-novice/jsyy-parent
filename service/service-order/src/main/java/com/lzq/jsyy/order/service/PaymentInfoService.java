package com.lzq.jsyy.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.vo.order.query.PaymentInfoQueryVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface PaymentInfoService extends IService<PaymentInfo> {
    /**
     * 分页条件查询订单
     *
     * @param pageParam
     * @param paymentInfoQuery
     * @return
     */
    Page<PaymentInfo> selectPage(Page<PaymentInfo> pageParam, PaymentInfoQueryVo paymentInfoQuery);

    /**
     * 根据预约订单生成支付记录
     *
     * @param orderInfo
     * @return
     */
    boolean add(OrderInfo orderInfo);

    /**
     * 支付订单
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    Map<String, Object> pay(String orderId) throws Exception;

    /**
     * 取消支付
     *
     * @param outTradeNo
     * @return
     */
    boolean cancel(String outTradeNo);

    /**
     * 支付成功，修改订单记录
     *
     * @param outTradeNo
     * @param resultMap
     */
    void success(String outTradeNo, Map<String, String> resultMap);

    /**
     * 根据对外业务编号查询订单
     *
     * @param outTradeNo
     * @return
     */
    PaymentInfo getByOutTradeNo(String outTradeNo);

    /**
     * 设置订单失效
     *
     * @param outTradeNo
     */
    void loss(String outTradeNo);
}
