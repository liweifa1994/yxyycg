package com.kargo.yycg.base.dao.mapper;

import com.kargo.yycg.base.pojo.po.BssSysArea;
import com.kargo.yycg.base.pojo.vo.AreaCustom;

import java.util.List;

/**
 * Created by lwf on 17-2-3.
 */
public interface AreaManagerMapperCustom {

    /**
     * 查询用户权限区域id列表
     *
     * @return  List<SchedulMO>
     * @param String userid 用户表主键
     * @throws Exception
     */
    public List<String> selectUserAreaIDlist(String sysid) throws Exception;

    /**
     * 根据区域根id获取区域树
     * @param areaid
     * @return
     * @throws Exception
     */
    public List<AreaCustom> selectAreaTreeByAreaid(BssSysArea bssSysArea) throws Exception;
    /**
     * 根据区域id获取子区域集合
     * @param areaid
     * @return
     * @throws Exception
     */
    public List<AreaCustom> selectAreaListByParentid(String areaid) throws Exception;
}
