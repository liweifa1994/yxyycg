package com.kargo.yycg.base.dao.mapper;

import com.kargo.yycg.base.pojo.vo.SysuserCustom;
import com.kargo.yycg.base.pojo.vo.SysuserQueryVo;

import java.util.List;

/**
 * Created by lwf on 16-12-6.
 */
public interface SysuserMapperCustom {

    /**
     *  query sysuser
     * @param sysuserQueryVo
     * @return
     * @throws Exception
     */
    List<SysuserCustom> findSysuserListByCondition(SysuserQueryVo sysuserQueryVo) throws Exception;




}
