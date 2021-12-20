package com.lzq.jsyy.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.order.RefundInfo;
import com.lzq.jsyy.vo.order.query.RefundInfoQueryVo;

/**
 * @author lzq
 */
public interface RefundInfoService extends IService<RefundInfo> {
    /**
     * 条件分页查询
     *
     * @param pageParam
     * @param refundInfoQuery
     * @return
     */
    Page<RefundInfo> selectPage(Page<RefundInfo> pageParam, RefundInfoQueryVo refundInfoQuery);

    /**
     * 申请退预约
     *
     * @param outTradeNo
     * @return
     * @throws Exception
     */
    boolean apply(String outTradeNo) throws Exception;
}
