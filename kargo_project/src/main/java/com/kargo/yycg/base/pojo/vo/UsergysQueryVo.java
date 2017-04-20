package com.kargo.yycg.base.pojo.vo;

import com.kargo.yycg.base.pojo.po.Usergys;

/**
 * Created by lwf on 17-1-18.
 */
public class UsergysQueryVo {
    //供货地区
    private String usergysghdqid;
    //供应商po
    private Usergys usergys;
    //供应商自定义vo
    private UsergysCustom usergysCustom;

    public String getUsergysghdqid() {
        return usergysghdqid;
    }

    public void setUsergysghdqid(String usergysghdqid) {
        this.usergysghdqid = usergysghdqid;
    }

    public Usergys getUsergys() {
        return usergys;
    }

    public void setUsergys(Usergys usergys) {
        this.usergys = usergys;
    }

    public UsergysCustom getUsergysCustom() {
        return usergysCustom;
    }

    public void setUsergysCustom(UsergysCustom usergysCustom) {
        this.usergysCustom = usergysCustom;
    }
}
