package com.lzq.jsyy.cmn.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.cmn.mapper.ScheduleMapper;
import com.lzq.jsyy.cmn.service.ScheduleService;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Schedule;
import com.lzq.jsyy.vo.cmn.add.ScheduleAddVo;
import com.lzq.jsyy.vo.cmn.query.ScheduleQueryVo;
import com.lzq.jsyy.vo.cmn.update.ScheduleUpdateVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * @author lzq
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {

    @Override
    public Page<Schedule> selectPage(Page<Schedule> pageParam, ScheduleQueryVo scheduleQueryVo) {
        if (ObjectUtils.isEmpty(scheduleQueryVo)) {
            return null;
        }

        String facilityId = scheduleQueryVo.getFacilityId();
        String roomId = scheduleQueryVo.getRoomId();
        String workDate = scheduleQueryVo.getWorkDate();
        String id = scheduleQueryVo.getId();
        String lastPendingPermission = scheduleQueryVo.getLastPendingPermission();
        Integer isOrdered = scheduleQueryVo.getIsOrdered();

        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper.eq("facility_id", facilityId);
        }
        if (!StringUtils.isEmpty(roomId)) {
            wrapper.eq("room_id", roomId);
        }
        if (!StringUtils.isEmpty(workDate)) {
            wrapper.eq("work_date", workDate);
        }
        if (!StringUtils.isEmpty(id)) {
            wrapper.eq("id", id);
        }
        if (!StringUtils.isEmpty(lastPendingPermission)) {
            wrapper.eq("last_pending_permission", lastPendingPermission);
        }
        if (!ObjectUtils.isEmpty(isOrdered)) {
            wrapper.eq("is_ordered", isOrdered);
        }

        Page<Schedule> page = baseMapper.selectPage(pageParam, wrapper);

        return page;
    }

    @Override
    public Map<String, Object> add(ScheduleAddVo scheduleAddVo) {
        Map<String, Object> map = new HashMap<>(1);

        if (ObjectUtils.isEmpty(scheduleAddVo)) {
            map.put("state", ResultCodeEnum.SCHEDULE_ADD_ERROR);
            return map;
        }

        // 判断时间段是否已被占据
        String workDate = scheduleAddVo.getWorkDate();
        String beginTime = scheduleAddVo.getBeginTime();
        String endTime = scheduleAddVo.getEndTime();

        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(workDate)) {
            wrapper.eq("work_date", workDate);
        }
        if (!StringUtils.isEmpty(beginTime)) {
            wrapper.ge("begin_time", beginTime);
        }
        if (!StringUtils.isEmpty(endTime)) {
            wrapper.le("end_time", endTime);
        }

        Schedule schedule1 = baseMapper.selectOne(wrapper);

        if (StringUtils.isEmpty(schedule1)) {
            map.put("state", ResultCodeEnum.SUCCESS);
            Schedule schedule = new Schedule();
            schedule.setFacilityId(scheduleAddVo.getFacilityId());
            schedule.setRoomId(scheduleAddVo.getRoomId());
            schedule.setOpenDate(scheduleAddVo.getOpenDate());
            schedule.setCloseDate(scheduleAddVo.getCloseDate());
            schedule.setWorkDate(workDate);
            schedule.setBeginTime(beginTime);
            schedule.setEndTime(endTime);
            schedule.setQuitDate(scheduleAddVo.getQuitDate());
            schedule.setAmount(scheduleAddVo.getAmount());
            schedule.setLastPendingPermission(scheduleAddVo.getLastPendingPermission());
            schedule.setIsOrdered(scheduleAddVo.getIsOrdered());

            map.put("schedule", schedule);
            baseMapper.insert(schedule);
        } else {
            map.put("state", ResultCodeEnum.SCHEDULE_ADD_ERROR);
        }

        return map;
    }

    @Override
    public Map<String, Object> change(ScheduleUpdateVo scheduleUpdateVo) {
        Map<String, Object> map = new HashMap<>(1);

        if (ObjectUtils.isEmpty(scheduleUpdateVo)) {
            map.put("state", ResultCodeEnum.SCHEDULE_CHANGE_ERROR);
            return map;
        }

        // 判断时间段是否已被占据
        String workDate = scheduleUpdateVo.getWorkDate();
        String beginTime = scheduleUpdateVo.getBeginTime();
        String endTime = scheduleUpdateVo.getEndTime();
        String id = scheduleUpdateVo.getId();
        String lastPendingPermission = scheduleUpdateVo.getLastPendingPermission();
        Integer isOrdered = scheduleUpdateVo.getIsOrdered();

        QueryWrapper<Schedule> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(workDate)) {
            wrapper.eq("work_date", workDate);
        }
        if (!StringUtils.isEmpty(beginTime)) {
            wrapper.ge("begin_time", beginTime);
        }
        if (!StringUtils.isEmpty(endTime)) {
            wrapper.le("end_time", endTime);
        }
        if (!StringUtils.isEmpty(lastPendingPermission)) {
            wrapper.eq("last_pending_permission", lastPendingPermission);
        }
        if (!ObjectUtils.isEmpty(isOrdered)) {
            wrapper.eq("is_ordered", isOrdered);
        }
        wrapper.ne("id", id);

        Schedule schedule1 = baseMapper.selectOne(wrapper);

        if (StringUtils.isEmpty(schedule1)) {
            map.put("state", ResultCodeEnum.SUCCESS);
            Schedule schedule = new Schedule();
            schedule.setFacilityId(scheduleUpdateVo.getFacilityId());
            schedule.setRoomId(scheduleUpdateVo.getRoomId());
            schedule.setOpenDate(scheduleUpdateVo.getOpenDate());
            schedule.setCloseDate(scheduleUpdateVo.getCloseDate());
            schedule.setWorkDate(workDate);
            schedule.setBeginTime(beginTime);
            schedule.setEndTime(endTime);
            schedule.setQuitDate(scheduleUpdateVo.getQuitDate());
            schedule.setAmount(scheduleUpdateVo.getAmount());
            schedule.setLastPendingPermission(scheduleUpdateVo.getLastPendingPermission());

            map.put("schedule", schedule);
            baseMapper.updateById(schedule);
        } else {
            map.put("state", ResultCodeEnum.SCHEDULE_CHANGE_ERROR);
        }

        return map;
    }
}
