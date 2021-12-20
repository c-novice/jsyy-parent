package com.lzq.jsyy.order.service;

import com.lzq.jsyy.model.order.PaymentInfo;

import java.util.Map;

/**
 * @author lzq
 */
public interface WechatService {
    /**
     * 生成微信二维码
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    Map<String, Object> createNative(String orderId) throws Exception;

    /**
     * 查询订单状态
     *
     * @param outTradeNo
     * @return
     * @throws Exception
     */
    Map<String, String> queryPayStatus(String outTradeNo) throws Exception;

    /**
     * 微信退款
     *
     * @param paymentInfo
     * @return
     * @throws Exception
     */
    Map<String, String> refund(PaymentInfo paymentInfo) throws Exception;
}
