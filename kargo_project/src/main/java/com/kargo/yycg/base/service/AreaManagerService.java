package com.kargo.yycg.base.service;

import com.kargo.yycg.base.pojo.po.BssSysArea;
import com.kargo.yycg.base.pojo.vo.AreaCustom;

import java.util.List;

/**
 * Created by lwf on 17-2-3.
 */
public interface AreaManagerService {
// 查询所有区域信息
    //public List findAllAreaList() throws Exception;

    // 查询所有区域信息只包含区域id
    //public List findAllAreaList_Id()throws Exception;

    // 根据属性id得到设备所在区域
    public BssSysArea getAreaByID(String id) throws Exception;

    /**
     * 根据设备区域名称得到设备区域对象信息
     * @param areaname 区域名称
     * @return
     * @throws Exception
     */

    public List<BssSysArea> getAreaByAreaname(String areaname, int arealevel) throws Exception;


    /**
     * 根据id列表得到区域列表
     * @param areaidList 区域id列表
     * @returnList<Area>
     * @throws Exception
     */

    public List<BssSysArea> findSelfAreaListByidlist(List areaidList) throws Exception;

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
    public List<AreaCustom> selectAreaListByParentid(String parentid) throws Exception;


    /**
     * 查询用户权限区域id列表
     *
     * @return List<String>
     * @param String sysid 用户表主键
     * @throws Exception
     */
    public List<String> findUserAreaidlist(String sysid) throws Exception;
}
