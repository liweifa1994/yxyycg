package com.kargo.yycg.base.dao;

import com.kargo.yycg.base.dao.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lwf on 17-1-17.
 */
@Service
public class BaseDaoFacade {
    //**************自动生成的mapper*****************
    // 系统用户
    @Autowired
    private SysuserMapper sysuserMapper;
    // 系统区域
    @Autowired
    private BssSysAreaMapper bssSysAreaMapper;
    // 系统配置表
    @Autowired
    private BasicinfoMapper basicinfoMapper;
    @Autowired
    private DictinfoMapper dictinfoMapper;
    // 医院信息表
    @Autowired
    private UseryyMapper useryyMapper;
    // 监督机构信息表
    @Autowired
    private UserjdMapper userjdMapper;
    // 供应商信息表
    @Autowired
    private UsergysMapper usergysMapper;
    // 供应商供货区域表
    @Autowired
    private UsergysareaMapper usergysareaMapper;

    public SysuserMapper getSysuserMapper() {
        return sysuserMapper;
    }

    public void setSysuserMapper(SysuserMapper sysuserMapper) {
        this.sysuserMapper = sysuserMapper;
    }

    public BssSysAreaMapper getBssSysAreaMapper() {
        return bssSysAreaMapper;
    }

    public void setBssSysAreaMapper(BssSysAreaMapper bssSysAreaMapper) {
        this.bssSysAreaMapper = bssSysAreaMapper;
    }

    public BasicinfoMapper getBasicinfoMapper() {
        return basicinfoMapper;
    }

    public void setBasicinfoMapper(BasicinfoMapper basicinfoMapper) {
        this.basicinfoMapper = basicinfoMapper;
    }

    public DictinfoMapper getDictinfoMapper() {
        return dictinfoMapper;
    }

    public void setDictinfoMapper(DictinfoMapper dictinfoMapper) {
        this.dictinfoMapper = dictinfoMapper;
    }

    public UseryyMapper getUseryyMapper() {
        return useryyMapper;
    }

    public void setUseryyMapper(UseryyMapper useryyMapper) {
        this.useryyMapper = useryyMapper;
    }

    public UserjdMapper getUserjdMapper() {
        return userjdMapper;
    }

    public void setUserjdMapper(UserjdMapper userjdMapper) {
        this.userjdMapper = userjdMapper;
    }

    public UsergysMapper getUsergysMapper() {
        return usergysMapper;
    }

    public void setUsergysMapper(UsergysMapper usergysMapper) {
        this.usergysMapper = usergysMapper;
    }

    public UsergysareaMapper getUsergysareaMapper() {
        return usergysareaMapper;
    }

    public void setUsergysareaMapper(UsergysareaMapper usergysareaMapper) {
        this.usergysareaMapper = usergysareaMapper;
    }

    //*************自定义mapper***************

    @Autowired
    private UserManagerMapperCustom userManagerMapperCustom;

    public UserManagerMapperCustom getUserManagerMapperCustom() {
        return userManagerMapperCustom;
    }

    public void setUserManagerMapperCustom(UserManagerMapperCustom userManagerMapperCustom) {
        this.userManagerMapperCustom = userManagerMapperCustom;
    }

    @Autowired
    private  AreaManagerMapperCustom areaManagerMapperCustom ;

    public AreaManagerMapperCustom getAreaManagerMapperCustom() {
        return areaManagerMapperCustom;
    }

    public void setAreaManagerMapperCustom(AreaManagerMapperCustom areaManagerMapperCustom) {
        this.areaManagerMapperCustom = areaManagerMapperCustom;
    }

    @Autowired
    private SystemConfigMapperCustom systemConfigMapperCustom ;

    public SystemConfigMapperCustom getSystemConfigMapperCustom() {
        return systemConfigMapperCustom;
    }

    public void setSystemConfigMapperCustom(SystemConfigMapperCustom systemConfigMapperCustom) {
        this.systemConfigMapperCustom = systemConfigMapperCustom;
    }
}
