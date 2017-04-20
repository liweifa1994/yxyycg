package com.kargo.yycg.base.service.impl;

import com.kargo.yycg.base.dao.BaseDaoFacade;
import com.kargo.yycg.base.pojo.po.Basicinfo;
import com.kargo.yycg.base.pojo.po.BssSysLog;
import com.kargo.yycg.base.pojo.po.Dictinfo;
import com.kargo.yycg.base.pojo.po.DictinfoExample;
import com.kargo.yycg.base.pojo.vo.Menu;
import com.kargo.yycg.base.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lwf on 17-1-17.
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService{
    @Autowired
    private BaseDaoFacade baseDaoFacade;

    /**
     * 查询字典类别详细详细列表
     */
    public List<Dictinfo> findDicttypeinfolist(String typecode) throws Exception {

        DictinfoExample example = new DictinfoExample();
        DictinfoExample.Criteria criteria = example.createCriteria();
        criteria.andTypecodeEqualTo(typecode);

        return baseDaoFacade.getDictinfoMapper().selectByExample(example);
    }

    /**
     * 根据id查询字典类别信息
     */
    public Dictinfo getDictinfoById(String id) throws Exception {

        return baseDaoFacade.getDictinfoMapper().selectByPrimaryKey(id);
    }
    /**
     * 根据类型和代码获取字典
     * @param typecode
     * @param code
     * @return
     * @throws Exception
     */
    public Dictinfo findDictinfoByDictinfocode(String typecode, String code) throws Exception {
        DictinfoExample example = new DictinfoExample();
        DictinfoExample.Criteria criteria = example.createCriteria();
        criteria.andTypecodeEqualTo(typecode);
        criteria.andDictcodeEqualTo(code);
        List<Dictinfo> dictinfoList = baseDaoFacade.getDictinfoMapper().selectByExample(example);
        if (dictinfoList != null && dictinfoList.size() == 1) {
            return dictinfoList.get(0);
        }
        return null;
    }

    public Dictinfo findDictinfoByDictinfoname(String typecode, String info) throws Exception {
        DictinfoExample example = new DictinfoExample();
        DictinfoExample.Criteria criteria = example.createCriteria();
        criteria.andTypecodeEqualTo(typecode);
        criteria.andInfoEqualTo(info);
        List<Dictinfo> dictinfoList = baseDaoFacade.getDictinfoMapper().selectByExample(example);
        if (dictinfoList != null && dictinfoList.size() == 1) {
            return dictinfoList.get(0);
        }
        return null;
    }

    public Basicinfo findBasicinfoById(String id) throws Exception {
        return baseDaoFacade.getBasicinfoMapper().selectByPrimaryKey(id);
    }
    /**
     *
     * 添加日志信息
     *
     * @param username   操作人姓名
     * @param userid   操作人账号
     * @param userip  管理员IP地址
     * @param logtype   日志类型
     * @param messages  操作信息描述
     * @throws Exception
     *
     */
    private void saveLog(String userid,String username, String userip, String logtype, String messages)throws Exception {
//        BssSysLog syslog = new BssSysLog();
//        syslog.setLogId(UUIDBuild.getUUID());
//        syslog.setUsername(username);
//        syslog.setUserid(userid);
//        syslog.setUserip(userip);
//        syslog.setLogtype(logtype);
//        syslog.setOperatedate(new Date());
//        syslog.setMessages(messages);
//        baseDaoFacade.getSyslogMapper().insert(syslog);
    }
    public List<Menu> findModuleAndOperateByRole(String role) throws Exception {
        return baseDaoFacade.getSystemConfigMapperCustom().findModuleAndOperateByRole(role);
    }
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
    public void saveSuccessLog(String userid, String username, String userip, String message) throws Exception {
        this.saveLog( userid,username, userip, "1", message);
    }

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
    public void saveFailLog(String userid,String username, String userip, String message)throws Exception {
        this.saveLog(userid,username,  userip, "2", message);
    }
}
