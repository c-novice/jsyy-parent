package com.lzq.jsyy.order.service.impl;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.order.service.WechatService;
import com.lzq.jsyy.order.utils.ConstantPropertiesUtils;
import com.lzq.jsyy.order.utils.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lzq
 */
@Service
@Slf4j
public class WechatServiceImpl implements WechatService {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Cacheable(value = "createNative", keyGenerator = "keyGenerator")
    @Override
    public Map createNative(String orderId) throws Exception {
        Map payMap;

        // 获取预约记录信息
        OrderInfo orderInfo = orderInfoService.getById(orderId);

        // 调用微信生成二维码接口
        Map<String, String> paramMap = new HashMap<>(10);
        paramMap.put("appid", ConstantPropertiesUtils.APPID);
        paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        paramMap.put("out_trade_no", orderInfo.getOutTradeNo());
        paramMap.put("total_fee", orderInfo.getAmount().toString());
        paramMap.put("spbill_create_ip", ConstantPropertiesUtils.URL);
        paramMap.put("body", "校园教室预约统一平台产品");
        paramMap.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
        paramMap.put("trade_type", "NATIVE");

        // 根据URL访问第三方接口并且传递参数
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        // 设置参数
        client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY));
        client.setHttps(true);
        client.post();

        // 返回相关数据
        String xml = client.getContent();
        log.info(xml);
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

        // 封装并返回结果
        payMap = new HashMap(10);
        payMap.put("orderId", orderId);
        payMap.put("totalFee", String.valueOf(orderInfo.getAmount()));
        payMap.put("resultCode", resultMap.get("result_code"));
        payMap.put("codeUrl", resultMap.get("code_url"));

        // redis缓存，120分钟失效
        if (!StringUtils.isEmpty(resultMap.get("result_code"))) {
            redisTemplate.opsForValue().set(orderId, payMap, 120, TimeUnit.MINUTES);
        }
        return payMap;
    }

    /**
     * 查询微信订单状态
     *
     * @param outTradeNo
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(String outTradeNo) throws Exception {
        OrderInfo orderInfo = orderInfoService.getByOutTradeNo(outTradeNo);

        Map paramMap = new HashMap<>(5);
        paramMap.put("appid", ConstantPropertiesUtils.APPID);
        paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
        paramMap.put("out_trade_no", orderInfo.getOutTradeNo());
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());

        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY));
        client.setHttps(true);
        client.post();

        String xml = client.getContent();
        log.info(xml);
        return WXPayUtil.xmlToMap(xml);
    }

    /**
     * 微信退款
     *
     * @param paymentInfo
     * @return
     */
    @Override
    public Map<String, String> refund(PaymentInfo paymentInfo) throws Exception {
        // 调用微信接口实现退款
        Map<String, String> paramMap = new HashMap<>(10);
        paramMap.put("appid", ConstantPropertiesUtils.APPID);
        paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        paramMap.put("transaction_id", paymentInfo.getTradeNo());
        paramMap.put("out_trade_no", paymentInfo.getOutTradeNo());
        paramMap.put("out_refund_no", "tk" + paymentInfo.getOutTradeNo());
        paramMap.put("total_fee", String.valueOf(paymentInfo.getTotalAmount()));
        paramMap.put("refund_fee", String.valueOf(paymentInfo.getTotalAmount()));
        String paramXml = WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY);

        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/secapi/pay/refund");
        client.setXmlParam(paramXml);
        client.setHttps(true);
        client.setCert(true);
        client.setCertPassword(ConstantPropertiesUtils.PARTNER);
        client.post();

        String xml = client.getContent();
        log.info(xml);
        Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

        if (!ObjectUtils.isEmpty(resultMap) && WXPayConstants.SUCCESS.equalsIgnoreCase(resultMap.get("result_code"))) {
            return resultMap;
        }

        return null;
    }
}

