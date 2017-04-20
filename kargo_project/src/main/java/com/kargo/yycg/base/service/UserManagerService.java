package com.kargo.yycg.base.service;

import com.kargo.yycg.base.pojo.po.Sysuser;
import com.kargo.yycg.base.pojo.po.Usergys;
import com.kargo.yycg.base.pojo.po.Userjd;
import com.kargo.yycg.base.pojo.po.Useryy;
import com.kargo.yycg.base.pojo.vo.*;

import java.util.List;

/**
 * Created by lwf on 17-1-17.
 */


public interface UserManagerService {

    /********************************系统用户信息*******************************/

    /**
     * 用户登录验证
     * @throws Exception
     */
    public Sysuser userValidator(Sysuser user) throws Exception;

    /**
     *查询系统用户信息列表
     * @param sysuserQueryVo
     * @return
     * @throws Exception
     */
    List<SysuserCustom> findSysuserListByCondition(SysuserQueryVo sysuserQueryVo) throws Exception;

    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     * @throws Exception
     */
    SysuserCustom findSysuserById(String id) throws Exception;

    /**
     * 根据帐号查找用户
     * @param userId
     * @return
     * @throws Exception
     */
    Sysuser findSysUserByUserId(String userId) throws Exception;

    /**
     * 添加系统用户信息
     * @param sysuserCustom
     * @throws Exception
     */
    void addSysUser(SysuserCustom sysuserCustom) throws Exception;

    /**
     * 修改系统用户信息
     * @param userId
     * @param sysuser
     * @return
     * @throws Exception
     */
    int editSysuser(String userId,SysuserCustom sysuser) throws Exception;

    /**
     * 删除系统用户信息
     * @param userId
     * @return
     * @throws Exception
     */
    int deleteSysUserById(String userId) throws Exception;

    /**
     * 根据用户的类型和单位的名称校验单位是否存在，并且返回单位的sysid
     * @param sysuserCustom
     * @return
     * @throws Exception
     */
    String checkSysmcByGroupIdAndSysMc(SysuserCustom sysuserCustom)throws Exception;
    /**
     *
     * @param sysmc
     * @return
     * @throws Exception
     */

    /**
     * 根据用户类型和单位id获取单位名称
     * @param groupid
     * @param sysid
     * @return
     * @throws Exception
     */
    public String getSysidBySysmc(String groupid,String sysmc)throws Exception;


    /**
     * 根据用户类型和单位id获取单位名称
     * @param groupid
     * @param sysid
     * @return
     * @throws Exception
     */
    public String getSysmcBySysid(String groupid,String sysid)throws Exception;


    /********************************医院信息*******************************/

    /**
     * 查询医院信息列表
     * @param useryyQueryVo
     * @return
     * @throws Exception
     */
    List<UseryyCustom> findUseryyList(UseryyQueryVo useryyQueryVo) throws Exception;

    /**
     * 查询医院
     * @param id
     * @return
     * @throws Exception
     */
    Useryy findUseryyById(String id) throws Exception;

    /**
     * 根据医院名称获取医院信息
     * @param sysmc
     * @return
     * @throws Exception
     */
    Useryy findUseryyBySysmc(String sysmc) throws Exception;

    /**
     * 添加医院信息
     * @param useryyQueryVo
     * @throws Exception
     */
    public void insertUseryy(Useryy useryy) throws Exception;
    /**
     * 修改医院信息
     * @param useryyQueryVo
     * @throws Exception
     */
    public void updateUseryy(Useryy useryy) throws Exception;

    /**
     * 删除医院信息
     * @param useryyid
     * @throws Exception
     */
    public void deleteUseryy(String id) throws Exception;

    /********************************监督机构信息*******************************/

    /**
     * 查询监督机构信息列表
     * @param userjdQueryVo
     * @return
     * @throws Exception
     */
    public List<UserjdCustom> findUserjdlist(UserjdQueryVo userjdQueryVo)throws Exception;
    /**
     * 根据监督机构id获取信息
     * @param id
     * @return
     * @throws Exception
     */
    public Userjd getUserjdById(String id) throws Exception;
    /**
     * 根据监督机构名称获取医院信息
     * @param sysmc
     * @return
     * @throws Exception
     */
    Userjd findUserjdBySysmc(String sysmc)throws Exception;
    /**
     * 添加监督机构信息
     * @param userjdQueryVo
     * @throws Exception
     */
    public void insertUserjd(Userjd userjd) throws Exception;
    /**
     * 修改监督机构信息
     * @param userjdQueryVo
     * @throws Exception
     */
    public void updateUserjd(Userjd userjd) throws Exception;

    /**
     * 删除监督机构信息
     * @param userjdid
     * @throws Exception
     */
    public void deleteUserjd(String id) throws Exception;

    /********************************供应商信息*******************************/
    /**
     * 查询供应商信息列表
     * @param usergysQueryVo
     * @return
     * @throws Exception
     */
    public List<UsergysCustom> findUsergyslist(UsergysQueryVo usergysQueryVo)throws Exception;

    /**
     * 根据供应商id获取信息
     * @param id
     * @return
     * @throws Exception
     */
    public Usergys getUsergysById(String id) throws Exception;
    /**
     * 根据供应商名称获取医院信息
     * @param sysmc
     * @return
     * @throws Exception
     */
    Usergys findUsergysBySysmc(String sysmc) throws Exception;

    /**
     * 添加供应商信息
     * @param usergysQueryVo
     * @throws Exception
     */
    public void insertUsergys(Usergys usergys,String usergysghdqid) throws Exception;
    /**
     * 修改供应商信息
     * @param usergysQueryVo
     * @throws Exception
     */
    public void updateUsergys(Usergys usergys,String usergysghdqid) throws Exception;

    /**
     * 删除供应商信息
     * @param usergysid
     * @throws Exception
     */
    public void deleteUsergys(String id) throws Exception;
}
