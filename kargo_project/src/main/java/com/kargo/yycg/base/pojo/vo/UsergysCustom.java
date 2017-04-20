package com.kargo.yycg.base.pojo.vo;

import com.kargo.yycg.base.pojo.po.BssSysArea;
import com.kargo.yycg.base.pojo.po.Usergys;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lwf on 17-1-18.
 */
public class UsergysCustom extends Usergys {

    // 类别名称
    private String lbmc;

    //供货地区
    private Set<BssSysArea> areas = new HashSet<BssSysArea>();
    //供货地区
    private String useryyghdq="";

    //供货地区id
    private String useryyghdqid="";

    public String getLbmc() {
        return lbmc;
    }

    public void setLbmc(String lbmc) {
        this.lbmc = lbmc;
    }

    public Set<BssSysArea> getAreas() {
        return areas;
    }

    public void setAreas(Set<BssSysArea> areas) {
        this.areas = areas;
    }

    public String getUseryyghdq() {
        return useryyghdq;
    }

    public void setUseryyghdq(String useryyghdq) {
        this.useryyghdq = useryyghdq;
    }

    public String getUseryyghdqid() {
        return useryyghdqid;
    }

    public void setUseryyghdqid(String useryyghdqid) {
        this.useryyghdqid = useryyghdqid;
    }
}
