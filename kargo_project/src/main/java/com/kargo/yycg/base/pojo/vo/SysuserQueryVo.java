package com.kargo.yycg.base.pojo.vo;

import com.kargo.yycg.base.pojo.po.Sysuser;

/**
 * Created by lwf on 16-12-6.
 */
public class SysuserQueryVo {

    private SysuserCustom sysuserCustom;
    private int page;
    private int rows;
    //系统用户po
    private Sysuser sysuser;

    public Sysuser getSysuser() {
        return sysuser;
    }

    public void setSysuser(Sysuser sysuser) {
        this.sysuser = sysuser;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public SysuserCustom getSysuserCustom() {
        return sysuserCustom;
    }

    public void setSysuserCustom(SysuserCustom sysuserCustom) {
        this.sysuserCustom = sysuserCustom;
    }
}
