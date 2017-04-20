package com.kargo.yycg.base.pojo.vo;

import com.kargo.yycg.base.pojo.po.Useryy;

/**
 * Created by lwf on 17-1-17.
 */
public class UseryyQueryVo {
    //医院po
    private Useryy useryy;
    //医院自定义vo
    private UseryyCustom useryyCustom;

    public Useryy getUseryy() {
        return useryy;
    }

    public void setUseryy(Useryy useryy) {
        this.useryy = useryy;
    }

    public UseryyCustom getUseryyCustom() {
        return useryyCustom;
    }

    public void setUseryyCustom(UseryyCustom useryyCustom) {
        this.useryyCustom = useryyCustom;
    }
}
