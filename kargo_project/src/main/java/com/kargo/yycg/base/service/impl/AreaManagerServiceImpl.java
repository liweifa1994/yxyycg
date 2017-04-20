package com.kargo.yycg.base.service.impl;

import com.kargo.yycg.base.dao.BaseDaoFacade;
import com.kargo.yycg.base.pojo.po.BssSysArea;
import com.kargo.yycg.base.pojo.po.BssSysAreaExample;
import com.kargo.yycg.base.pojo.vo.AreaCustom;
import com.kargo.yycg.base.service.AreaManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lwf on 17-2-3.
 */
@Service
public class AreaManagerServiceImpl implements AreaManagerService {
    @Autowired
    private BaseDaoFacade baseDaoFacade;

    public BssSysArea getAreaByID(String id) throws Exception {
        return baseDaoFacade.getBssSysAreaMapper().selectByPrimaryKey(id);
    }
    /**
     * 根据设备区域名称得到设备区域对象信息
     *
     * @param areaname
     *            区域名称
     * @return
     * @throws Exception
     */
    public List<BssSysArea> getAreaByAreaname(String areaname, int arealevel) throws Exception {
        BssSysAreaExample areaExample = new BssSysAreaExample();
        BssSysAreaExample.Criteria criteria = areaExample.createCriteria();
        criteria.andAreanameEqualTo(areaname);
        criteria.andArealevelEqualTo(arealevel);
        return baseDaoFacade.getBssSysAreaMapper().selectByExample(areaExample);
    }
    /**
     * 根据id列表得到区域列表(辅助类)
     *
     * @param areaidlist
     *            区域id列表
     * @return List<Area>
     * @throws Exception
     */
    public List<BssSysArea> findSelfAreaListByidlist(List areaidList) throws Exception {
        BssSysAreaExample areaExample = new BssSysAreaExample();
        BssSysAreaExample.Criteria criteria = areaExample.createCriteria();
        criteria.andAreaidIn(areaidList);
        return baseDaoFacade.getBssSysAreaMapper().selectByExample(areaExample);
    }
    /**
     * 根据区域根id获取区域树
     * @param areaid
     * @return
     * @throws Exception
     */
    public List<AreaCustom> selectAreaTreeByAreaid(BssSysArea bssSysArea) throws Exception {
        List<AreaCustom> list = baseDaoFacade.getAreaManagerMapperCustom().selectAreaTreeByAreaid(bssSysArea);
        return list;
    }
    /**
     * 根据区域id获取子区域集合
     * @param areaid
     * @return
     * @throws Exception
     */
    public List<AreaCustom> selectAreaListByParentid(String parentid) throws Exception {
        List<AreaCustom> list = baseDaoFacade.getAreaManagerMapperCustom().selectAreaListByParentid(parentid);
        return list;
    }
    /**
     * 查询用户权限区域id列表
     *
     * @return List<SchedulMO>
     * @param String
     *            username
     * @throws Exception
     */
    public List<String> findUserAreaidlist(String sysid) throws Exception {
        return baseDaoFacade.getAreaManagerMapperCustom().selectUserAreaIDlist(sysid);
    }
}
