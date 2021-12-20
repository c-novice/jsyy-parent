package com.lzq.jsyy.order.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.cmn.client.ScheduleFeignClient;
import com.lzq.jsyy.common.exception.JsyyException;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.enums.OrderInfoStatusEnum;
import com.lzq.jsyy.enums.PaymentInfoStatusEnum;
import com.lzq.jsyy.model.order.OrderInfo;
import com.lzq.jsyy.model.order.PaymentInfo;
import com.lzq.jsyy.order.mapper.PaymentInfoMapper;
import com.lzq.jsyy.order.service.OrderInfoService;
import com.lzq.jsyy.order.service.PaymentInfoService;
import com.lzq.jsyy.order.service.WechatService;
import com.lzq.jsyy.user.client.UserFeignClient;
import com.lzq.jsyy.vo.order.query.PaymentInfoQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {
    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ScheduleFeignClient scheduleFeignClient;

    @Override
    public Page<PaymentInfo> selectPage(Page<PaymentInfo> pageParam, PaymentInfoQueryVo paymentInfoQuery) {
        if (ObjectUtils.isEmpty(paymentInfoQuery)) {
            return null;
        }
        String orderId = paymentInfoQuery.getOrderId();
        Integer paymentStatus = paymentInfoQuery.getPaymentStatus();
        String callbackTime = paymentInfoQuery.getCallbackTime();


        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(orderId)) {
            wrapper.eq("order_id", orderId);
        }
        if (!StringUtils.isEmpty(callbackTime)) {
            wrapper.eq("call_back_time", callbackTime);
        }
        if (!StringUtils.isEmpty(paymentStatus)) {
            wrapper.eq("payment_status", paymentStatus);
        }

        Page<PaymentInfo> page = baseMapper.selectPage(pageParam, wrapper);
        return page;
    }

    @Override
    public boolean add(OrderInfo orderInfo) {
        if (ObjectUtils.isEmpty(orderInfo)) {
            return false;
        }
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderId(orderInfo.getId());
        paymentInfo.setOutTradeNo(orderInfo.getOutTradeNo());
        paymentInfo.setTotalAmount(orderInfo.getAmount());
        paymentInfo.setPaymentStatus(PaymentInfoStatusEnum.REMAINING.getStatus());

        return baseMapper.insert(paymentInfo) > 0;
    }

    @Transactional(rollbackFor = JsyyException.class)
    @Override
    public Map<String, Object> pay(String orderId) throws Exception {
        Map<String, Object> map = new HashMap<>(2);
        if (StringUtils.isEmpty(orderId)) {
            map.put("state", ResultCodeEnum.PAYMENT_ADD_ERROR);
            return map;
        }

        OrderInfo orderInfo = orderInfoService.getById(orderId);
        if (ObjectUtils.isEmpty(orderInfo)) {
            map.put("state", ResultCodeEnum.PAYMENT_ADD_ERROR);
            return map;
        }

        // 如果未查到待支付的订单，拒绝添加
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(paymentInfo) || !paymentInfo.getPaymentStatus().equals(PaymentInfoStatusEnum.REMAINING.getStatus())) {
            map.put("state", ResultCodeEnum.PAYMENT_ADD_ERROR);
            return map;
        }

        // 获取微信支付二维码
        Map<String, Object> native2 = wechatService.createNative(orderId);
        if (ObjectUtils.isEmpty(native2)) {
            map.put("state", ResultCodeEnum.PAYMENT_ADD_ERROR);
            return map;
        }

        map.put("paymentInfo", paymentInfo);
        map.put("native", native2);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

    @Transactional(rollbackFor = JsyyException.class)
    @Override
    public boolean cancel(String outTradeNo) {
        if (StringUtils.isEmpty(outTradeNo)) {
            return false;
        }
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no", outTradeNo);
        wrapper.eq("payment_status", PaymentInfoStatusEnum.REMAINING.getStatus());
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);

        if (ObjectUtils.isEmpty(paymentInfo)) {
            return false;
        }

        // 更新订单状态为取消
        paymentInfo.setPaymentStatus(PaymentInfoStatusEnum.CANCELED.getStatus());
        baseMapper.updateById(paymentInfo);

        // 更新预约记录为失效
        OrderInfo orderInfo = orderInfoService.getById(paymentInfo.getOrderId());
        orderInfo.setOrderStatus(OrderInfoStatusEnum.LOSE_EFFICACY.getStatus());
        orderInfoService.updateById(orderInfo);

        return true;
    }

    @Transactional(rollbackFor = JsyyException.class)
    @Override
    public void success(String outTradeNo, Map<String, String> resultMap) {
        // 更新支付记录
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no", outTradeNo);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);

        paymentInfo.setPaymentStatus(PaymentInfoStatusEnum.PAYED.getStatus());
        paymentInfo.setCallbackTime(String.valueOf(new Date()));
        paymentInfo.setTradeNo(resultMap.get("transaction_id"));
        paymentInfo.setCallbackContent(resultMap.get("sign"));
        baseMapper.updateById(paymentInfo);

        // 更新订单记录
        OrderInfo orderInfo = orderInfoService.getById(paymentInfo.getOrderId());
        // 当订单下一审批权限不存在或最终权限等于用户权限时直接设置为预约成功
        // 获取用户权限
        String permissionName = userFeignClient.getPermissionByUsername(orderInfo.getUsername());
        if (StringUtils.isEmpty(orderInfo.getNextNeedPermission()) || orderInfo.getLastPendingPermission().equals(permissionName)) {
            orderInfo.setOrderStatus(OrderInfoStatusEnum.ORDERED.getStatus());
        } else {
            orderInfo.setOrderStatus(OrderInfoStatusEnum.PENDING.getStatus());
        }
        orderInfoService.updateById(orderInfo);

        // 更新预约排班
        scheduleFeignClient.updateOrdered(orderInfo.getScheduleId());
    }

    @Cacheable(value = "getByOutTradeNo", keyGenerator = "keyGenerator")
    @Override
    public PaymentInfo getByOutTradeNo(String outTradeNo) {
        if (StringUtils.isEmpty(outTradeNo)) {
            return null;
        }

        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();

        wrapper.eq("out_trade_no", outTradeNo);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public void loss(String outTradeNo) {
        if (StringUtils.isEmpty(outTradeNo)) {
            return;
        }
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no", outTradeNo);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(paymentInfo)) {
            return;
        }
        paymentInfo.setPaymentStatus(PaymentInfoStatusEnum.OVER_TIME.getStatus());
        baseMapper.updateById(paymentInfo);
    }
}
