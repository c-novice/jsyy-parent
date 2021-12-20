package com.lzq.jsyy.cmn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.vo.cmn.add.ScheduleAddVo;
import com.lzq.jsyy.vo.cmn.query.ScheduleQueryVo;
import com.lzq.jsyy.vo.cmn.update.ScheduleUpdateVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface ScheduleService extends IService<Schedule> {
    /**
     * 条件分页查询
     *
     * @param pageParam
     * @param scheduleQueryVo
     * @return
     */
    Page<Schedule> selectPage(Page<Schedule> pageParam, ScheduleQueryVo scheduleQueryVo);

    /**
     * 添加预约排班
     *
     * @param scheduleAddVo
     * @return
     */
    Map<String, Object> add(ScheduleAddVo scheduleAddVo);

    /**
     * 修改预约排班
     *
     * @param scheduleUpdateVo
     * @return
     */
    Map<String, Object> change(ScheduleUpdateVo scheduleUpdateVo);
}
