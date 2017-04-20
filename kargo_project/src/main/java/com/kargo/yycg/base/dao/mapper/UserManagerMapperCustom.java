package com.kargo.yycg.base.dao.mapper;

import com.kargo.yycg.base.pojo.vo.*;

import java.util.List;

/**
 * Created by lwf on 17-1-17.
 */
public interface UserManagerMapperCustom {
    /**
     *  query sysuser
     * @param sysuserQueryVo
     * @return
     * @throws Exception
     */
    List<SysuserCustom> findSysuserListByCondition(SysuserQueryVo sysuserQueryVo) throws Exception;
    /**
     * 查询yy构信息列表
     * @param userjdQueryVo
     * @return
     * @throws Exception
     */

    List<UseryyCustom> findUseryyListByConndition(UseryyQueryVo useryyQueryVo) throws Exception;
    /**
     * 查询监督机构信息列表
     * @param userjdQueryVo
     * @return
     * @throws Exception
     */
    List<UserjdCustom> findUserjdlist(UserjdQueryVo userjdQueryVo)throws Exception;

    /**
     * 查询供应商信息列表
     * @param usergysQueryVo
     * @return
     * @throws Exception
     */
    List<UsergysCustom> findUsergyslist(UsergysQueryVo usergysQueryVo)throws Exception;
}
