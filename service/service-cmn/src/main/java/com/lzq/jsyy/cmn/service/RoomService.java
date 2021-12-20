package com.lzq.jsyy.cmn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.cmn.Room;
import com.lzq.jsyy.vo.cmn.add.RoomAddVo;
import com.lzq.jsyy.vo.cmn.query.RoomQueryVo;
import com.lzq.jsyy.vo.cmn.update.RoomUpdateVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface RoomService extends IService<Room> {
    /**
     * 添加一个房间
     *
     * @param roomAddVo
     * @return
     */
    Map<String, Object> add(RoomAddVo roomAddVo);

    /**
     * 条件分页查询房间
     *
     * @param pageParam
     * @param roomQueryVo
     * @return
     */
    Page<Room> selectPage(Page<Room> pageParam, RoomQueryVo roomQueryVo);

    /**
     * 修改一个房间
     *
     * @param roomUpdateVo
     * @return
     */
    Map<String, Object> change(RoomUpdateVo roomUpdateVo);

    /**
     * 查询设施的房间数量
     *
     * @param facilityId
     * @return
     */
    Integer count(String facilityId);
}
