package com.kargo.yycg.base.dao.mapper;

import com.kargo.yycg.base.pojo.vo.Menu;

import java.util.Date;
import java.util.List;

/**
 * Created by lwf on 17-2-3.
 */
public interface SystemConfigMapperCustom {
    /**
     * 根据角色查询相关的模块及操作
     *
     */
    public List<Menu> findModuleAndOperateByRole(String role) throws Exception;
    /**
     * 查询数据库服务器当前时间
     *
     *
     * @returndatetimeStr 当前时间值
     * @throws Exception
     */
    public Date getDataBaseTime()throws Exception;
}
