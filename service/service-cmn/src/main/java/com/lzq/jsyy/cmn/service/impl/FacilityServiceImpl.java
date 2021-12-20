package com.lzq.jsyy.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.cmn.mapper.FacilityMapper;
import com.lzq.jsyy.cmn.service.FacilityService;
import com.lzq.jsyy.cmn.service.RoomService;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Facility;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.vo.cmn.add.FacilityAddVo;
import com.lzq.jsyy.vo.cmn.query.FacilityQueryVo;
import com.lzq.jsyy.vo.cmn.query.RoomQueryVo;
import com.lzq.jsyy.vo.cmn.update.FacilityUpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class FacilityServiceImpl extends ServiceImpl<FacilityMapper, Facility> implements FacilityService {
    @Autowired
    private RoomService roomService;

    @Override
    public Page<Facility> selectPage(Page<Facility> pageParam, FacilityQueryVo facilityQueryVo) {
        if (ObjectUtils.isEmpty(facilityQueryVo)) {
            return null;
        }

        String id = facilityQueryVo.getId();
        String name = facilityQueryVo.getName();

        QueryWrapper<Facility> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(id)) {
            wrapper.eq("id", id);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }

        Page<Facility> page = baseMapper.selectPage(pageParam, wrapper);
        // 补充教室
        Page<Room> pageParam2 = new Page<>(1, Integer.MAX_VALUE);
        for (int i = 0; i < page.getRecords().size(); i++) {
            Facility facility = page.getRecords().get(i);
            RoomQueryVo roomQueryVo = new RoomQueryVo();
            roomQueryVo.setFacilityId(facility.getId());
            facility.setRooms(roomService.selectPage(pageParam2, roomQueryVo).getRecords());
            facility.setRoomCount(roomService.count(facility.getId()));
        }
        return page;
    }

    @Override
    public Map<String, Object> add(FacilityAddVo facilityAddVo) {
        Map<String, Object> map = new HashMap<>(1);
        if (ObjectUtils.isEmpty(facilityAddVo)) {
            map.put("state", ResultCodeEnum.FACILITY_ADD_ERROR);
            return map;
        }

        String name = facilityAddVo.getName();
        String description = facilityAddVo.getDescription();

        QueryWrapper<Facility> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper.eq("name", name);
        }
        Facility facility = baseMapper.selectOne(wrapper);
        if (!StringUtils.isEmpty(facility)) {
            map.put("state", ResultCodeEnum.FACILITY_ADD_ERROR);
            return map;
        }

        Facility facility2 = new Facility();
        facility2.setName(name);
        facility2.setDescription(description);
        baseMapper.insert(facility2);
        map.put("state", ResultCodeEnum.SUCCESS);
        map.put("facility", baseMapper.selectById(facility2));
        return map;
    }

    @Override
    public Map<String, Object> change(FacilityUpdateVo facilityUpdateVo) {
        Map<String, Object> map = new HashMap<>(1);
        if (ObjectUtils.isEmpty(facilityUpdateVo)) {
            map.put("state", ResultCodeEnum.FACILITY_CHANGE_ERROR);
            return map;
        }

        String id = facilityUpdateVo.getId();

        if (StringUtils.isEmpty(id)) {
            map.put("state", ResultCodeEnum.FACILITY_CHANGE_ERROR);
            return map;
        }
        Facility facility = baseMapper.selectById(id);
        if (StringUtils.isEmpty(facility)) {
            map.put("state", ResultCodeEnum.FACILITY_CHANGE_ERROR);
            return map;
        }

        facility.setDescription(facilityUpdateVo.getDescription());
        facility.setName(facilityUpdateVo.getName());

        baseMapper.updateById(facility);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

}
