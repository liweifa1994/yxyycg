package com.kargo.yycg.base.service;

import com.kargo.yycg.base.pojo.po.Basicinfo;
import com.kargo.yycg.base.pojo.po.Dictinfo;
import com.kargo.yycg.base.pojo.vo.Menu;

import java.util.List;

/**
 * Created by lwf on 17-1-17.
 */
public interface SystemConfigService {
    /**
     * 查询字典类别详细详细列表
     * @param typecode
     * @return
     * @throws Exception
     */
    public List<Dictinfo> findDicttypeinfolist(String typecode)  throws Exception;

    /**
     * 根据id查询字典类别信息
     * @param id
     * @return
     * @throws Exception
     */
    public Dictinfo getDictinfoById(String id) throws Exception;

    /**
     * 根据类型和代码获取字典
     * @param typecode
     * @param code
     * @return
     * @throws Exception
     */
    public Dictinfo findDictinfoByDictinfocode(String typecode, String code) throws Exception;

    /**
     * 根据类型和信息获取字典
     * @param typecode
     * @param code
     * @return
     * @throws Exception
     */
    public Dictinfo findDictinfoByDictinfoname(String typecode, String info) throws Exception;

    /**
     * 获取系统配置表数据
     * @param id
     * @return
     * @throws Exception
     */
    public Basicinfo findBasicinfoById(String id)throws Exception;

    /**
     * 根据角色查询相关的模块及操作
     *
     */
    public List<Menu> findModuleAndOperateByRole(String role) throws Exception;

    /**
     *
     * 记录操作成功日志
     *
     * @param username   操作人姓名
     * @param userid   操作人账号
     * @param userip  管理员IP地址
     * @param messages  操作信息描述
     * @throws Exception
     *
     */
    public void saveSuccessLog(String userid, String username,String userip, String message)throws Exception ;

    /**
     *
     * 记录操作失败日志
     *
     * @param username   操作人姓名
     * @param userid   操作人账号
     * @param userip  管理员IP地址
     * @param messages  操作信息描述
     * @throws Exception
     *
     */
    public void saveFailLog(String userid,String username, String userip, String message)throws Exception;
}
