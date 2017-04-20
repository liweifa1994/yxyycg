package com.kargo.yycg.base.pojo.vo;

import com.kargo.yycg.base.pojo.po.Sysuser;

/**
 * Created by lwf on 16-12-6.
 */
public class SysuserCustom extends Sysuser {
    private String sysmc ;
    //用户类型名称
    private String groupname;
    public String getSysmc() {
        return sysmc;
    }

    public void setSysmc(String sysmc) {
        this.sysmc = sysmc;
    }
    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

}
