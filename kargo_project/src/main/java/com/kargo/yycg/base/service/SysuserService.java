package com.kargo.yycg.base.service;

import com.kargo.yycg.base.pojo.po.Sysuser;
import com.kargo.yycg.base.pojo.po.Usergys;
import com.kargo.yycg.base.pojo.po.Userjd;
import com.kargo.yycg.base.pojo.po.Useryy;
import com.kargo.yycg.base.pojo.vo.SysuserCustom;
import com.kargo.yycg.base.pojo.vo.SysuserQueryVo;

import java.util.List;

/**
 * Created by lwf on 16-12-6.
 */
public interface SysuserService {
    List<SysuserCustom> findSysuserListByCondition(SysuserQueryVo sysuserQueryVo) throws Exception;
    void addSysUser(SysuserCustom sysuserCustom) throws Exception;
    //根据帐号查找用户
    Sysuser findSysUserByUserId(String userId) throws Exception;

    Useryy findUseryyBySysmc(String sysmc) throws Exception;

    Userjd findUserjdBySysmc(String sysmc)throws Exception;

    Usergys findUsergysBySysmc(String sysmc) throws Exception;

    String checkSysmcByGroupIdAndSysMc(SysuserCustom sysuserCustom)throws Exception;
    int editSysuser(String userId,SysuserCustom sysuser) throws Exception;
    int deleteSysUserById(String userId) throws Exception;
    //根据用户id获取用户信息
    SysuserCustom findSysuserById(String id) throws Exception;

}
