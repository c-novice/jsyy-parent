package com.lzq.jsyy.cmn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.cmn.mapper.RoomMapper;
import com.lzq.jsyy.cmn.service.RoomService;
import com.lzq.jsyy.common.result.ResultCodeEnum;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.vo.cmn.add.RoomAddVo;
import com.lzq.jsyy.vo.cmn.query.RoomQueryVo;
import com.lzq.jsyy.vo.cmn.update.RoomUpdateVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

    @Override
    public Page<Room> selectPage(Page<Room> pageParam, RoomQueryVo roomQueryVo) {
        if (ObjectUtils.isEmpty(roomQueryVo)) {
            return null;
        }

        String facilityId = roomQueryVo.getFacilityId();
        String roomId = roomQueryVo.getRoomId();
        String type = roomQueryVo.getType();
        Integer seatingLow = roomQueryVo.getSeatingLow();
        Integer seatingHigh = roomQueryVo.getSeatingHigh();

        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper.eq("facility_id", facilityId);
        }
        if (!StringUtils.isEmpty(roomId)) {
            wrapper.like("room_id", roomId);
        }
        if (!StringUtils.isEmpty(type)) {
            wrapper.like("type", type);
        }
        if (!ObjectUtils.isEmpty(seatingLow)) {
            wrapper.ge("seating", seatingLow);
        }
        if (!ObjectUtils.isEmpty(seatingHigh)) {
            wrapper.le("seating", seatingHigh);
        }

        Page<Room> page = baseMapper.selectPage(pageParam, wrapper);
        return page;
    }


    @Override
    public Map<String, Object> add(RoomAddVo roomAddVo) {
        Map<String, Object> map = new HashMap<>(1);
        if (ObjectUtils.isEmpty(roomAddVo)) {
            map.put("state", ResultCodeEnum.ROOM_ADD_ERROR);
            return map;
        }

        String roomId = roomAddVo.getRoomId();

        QueryWrapper<Room> wrapper1 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(roomAddVo)) {
            wrapper1.eq("room_id", roomId);
        }

        Room room1 = baseMapper.selectOne(wrapper1);
        if (!StringUtils.isEmpty(room1)) {
            map.put("state", ResultCodeEnum.ROOM_ADD_ERROR);
            return map;
        }

        Room room = new Room();
        room.setRoomId(roomId);
        room.setFacilityId(roomAddVo.getFacilityId());
        room.setSeating(roomAddVo.getSeating());
        room.setDescription(roomAddVo.getDescription());
        room.setType(roomAddVo.getType());

        baseMapper.insert(room);
        map.put("state", ResultCodeEnum.SUCCESS);
        map.put("room", baseMapper.selectById(room));
        return map;
    }

    @Override
    public Map<String, Object> change(RoomUpdateVo roomUpdateVo) {
        Map<String, Object> map = new HashMap<>(1);
        if (ObjectUtils.isEmpty(roomUpdateVo)) {
            map.put("state", ResultCodeEnum.ROOM_ADD_ERROR);
            return map;
        }

        String roomId = roomUpdateVo.getRoomId();
        String id = roomUpdateVo.getId();

        QueryWrapper<Room> wrapper1 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(roomUpdateVo)) {
            wrapper1.eq("room_id", roomId);
        }
        if (!StringUtils.isEmpty(id)) {
            wrapper1.or().eq("id", id);
        }

        Room room1 = baseMapper.selectOne(wrapper1);
        if (!StringUtils.isEmpty(room1)) {
            map.put("state", ResultCodeEnum.ROOM_ADD_ERROR);
            return map;
        }

        Room room = new Room();
        room.setRoomId(roomId);
        room.setType(roomUpdateVo.getType());
        room.setId(id);
        room.setDescription(roomUpdateVo.getDescription());
        room.setSeating(roomUpdateVo.getSeating());
        room.setSchedules(room.getSchedules());

        baseMapper.updateById(room);
        map.put("state", ResultCodeEnum.SUCCESS);
        map.put("room", room);
        return map;
    }

    @Override
    public Integer count(String facilityId) {
        if (StringUtils.isEmpty(facilityId)) {
            return null;
        }

        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        wrapper.eq("facility_id", facilityId);

        return baseMapper.selectCount(wrapper);
    }
}
