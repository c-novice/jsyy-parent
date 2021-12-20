package com.lzq.jsyy.msm.service;


import com.lzq.jsyy.vo.msm.MsmVo;

/**
 * @author lzq
 */
public interface MsmService {
    /**
     * 发送短信服务
     *
     * @param phone
     * @param sixBitRandom
     * @return
     */
    boolean send(String phone, String sixBitRandom);

    /**
     * mq使用的，订单发送短信服务
     *
     * @param msmVo
     * @return
     */
    boolean send(MsmVo msmVo);
}
