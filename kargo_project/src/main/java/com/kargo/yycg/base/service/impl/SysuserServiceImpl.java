package com.kargo.yycg.base.service.impl;

import com.kargo.yycg.base.dao.mapper.*;
import com.kargo.yycg.base.pojo.po.*;
import com.kargo.yycg.base.pojo.vo.SysuserCustom;
import com.kargo.yycg.base.pojo.vo.SysuserQueryVo;
import com.kargo.yycg.base.process.context.Config;
import com.kargo.yycg.base.process.result.ResultUtil;
import com.kargo.yycg.base.service.SysuserService;
import com.kargo.yycg.common.utils.MD5;
import com.kargo.yycg.common.utils.UUIDBuild;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lwf on 16-12-6.
 */

@Service
public class SysuserServiceImpl implements SysuserService {

    @Autowired
    private SysuserMapperCustom sysuserMapperCustom ;
    @Autowired
    private SysuserMapper sysuserMapper ;
    @Autowired
    private UseryyMapper useryyMapper ;
    @Autowired
    private UserjdMapper userjdMapper ;
    @Autowired
    private UsergysMapper usergysMapper ;
    public List<SysuserCustom> findSysuserListByCondition(SysuserQueryVo sysuserQueryVo) throws Exception {


        return sysuserMapperCustom.findSysuserListByCondition(sysuserQueryVo);
    }

    public void addSysUser(SysuserCustom sysuserCustom) throws Exception {
        if(sysuserCustom == null || StringUtils.isEmpty(sysuserCustom.getUserid()) || StringUtils.isEmpty(sysuserCustom.getGroupid()) || StringUtils.isEmpty(sysuserCustom.getUsername())){
            ResultUtil.throwExcepion(901);
        }

        //fei kong jiao yan
        Sysuser sysuser = findSysUserByUserId(sysuserCustom.getUserid());
        if(sysuser != null){
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 208, null));
        }

        String sysId = checkSysmcByGroupIdAndSysMc(sysuserCustom);
        if(sysId!=null){
            //设置主键
            sysuserCustom.setId(UUIDBuild.getUUID());
            //设置单位id
            if("admin".equals(sysId)){
                sysId = null;
            }
            sysuserCustom.setSysid(sysId);
            sysuserCustom.setCreatetime(new Date());
            sysuserMapper.insert(sysuserCustom);
        }else{
            ResultUtil.throwExcepion(204);
        }
    }

    public Sysuser findSysUserByUserId(String userId) throws Exception {
        if(StringUtils.isEmpty(userId)){
            ResultUtil.throwExcepion(215);
            return null;
        }
        SysuserExample example = new SysuserExample();
        SysuserExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        List<Sysuser> sysusers = sysuserMapper.selectByExample(example);
        if(sysusers!=null&&sysusers.size()==1){
            return sysusers.get(0);
        }
        return null;
    }

    public Useryy findUseryyBySysmc(String sysmc) throws Exception {
        if(StringUtils.isEmpty(sysmc)){
            ResultUtil.throwExcepion(210);
        }
        UseryyExample useryyExample = new UseryyExample();
        UseryyExample.Criteria criteria = useryyExample.createCriteria();
        criteria.andMcEqualTo(sysmc);
        List<Useryy> useryyList = useryyMapper.selectByExample(useryyExample);
        if(useryyList!=null && useryyList.size()==1){
            return useryyList.get(0);
        }
        return null;
    }

    public Userjd findUserjdBySysmc(String sysmc) throws Exception {
        if(StringUtils.isEmpty(sysmc)){
            ResultUtil.throwExcepion(209);
            return null;
        }
        UserjdExample userjdExample = new UserjdExample();
        UserjdExample.Criteria criteria = userjdExample.createCriteria();
        criteria.andMcEqualTo(sysmc);
        List<Userjd> userjdList = userjdMapper.selectByExample(userjdExample);
        if(userjdList !=null &&userjdList.size()==1){
            return userjdList.get(0);
        }
        return null;
    }

    public Usergys findUsergysBySysmc(String sysmc) throws Exception {
        if(StringUtils.isEmpty(sysmc)){
            ResultUtil.throwExcepion(211);
            return null;
        }
        UsergysExample usergysExample = new UsergysExample();
        UsergysExample.Criteria criteria = usergysExample.createCriteria();
        criteria.andMcEqualTo(sysmc);
        List<Usergys> usergysList = usergysMapper.selectByExample(usergysExample);
        if(usergysList !=null &&usergysList.size()==1){
            return usergysList.get(0);
        }
        return null;
    }

    public String checkSysmcByGroupIdAndSysMc(SysuserCustom sysuserCustom) throws Exception {
        String sysmc = sysuserCustom.getSysmc();
        String groupId = sysuserCustom.getGroupid();
        if(StringUtils.isEmpty(groupId)||StringUtils.isEmpty(sysmc)){
            ResultUtil.throwExcepion(211);
        }
        if("1".equals(groupId)||"2".equals(groupId)){
            Userjd sysmc1 = findUserjdBySysmc(sysmc);
            if(sysmc1!=null){
                return sysmc1.getId();
            }else {
                ResultUtil.throwExcepion(209);
            }
        }else if("3".equals(groupId)){
            Useryy useryy = findUseryyBySysmc(sysmc);
            if(useryy!=null){
                return useryy.getId();
            }else {
                ResultUtil.throwExcepion(210);
            }
        }else if("4".equals(groupId)){

            Usergys usergys = findUsergysBySysmc(sysmc);
            if(usergys!=null){
                return usergys.getId();
            }else {
                ResultUtil.throwExcepion(211);
            }
        }else if("0".equals(groupId)){
          return "admin";
        }
        return null;
    }

    public int deleteSysUserById(String userId) throws Exception {
        if(StringUtils.isEmpty(userId)){
            ResultUtil.throwExcepion(901);
        }
        int key = sysuserMapper.deleteByPrimaryKey(userId);

        return key;
    }

    public SysuserCustom findSysuserById(String id) throws Exception {
        //从数据库查询用户
        Sysuser sysuser  =sysuserMapper.selectByPrimaryKey(id);

        //根据sysid查询单位名称
        String groupid = sysuser.getGroupid();
        String sysid = sysuser.getSysid();//单位id
        String sysmc  =null;
        if(groupid.equals("1") || groupid.equals("2")){
            //监督单位
            //根据单位id查询单位信息
            Userjd userjd = userjdMapper.selectByPrimaryKey(sysid);
            if(userjd==null){
                //抛出异常，可预知异常
                ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
            }
            sysmc = userjd.getMc();
        }else if(groupid.equals("3")){
            //卫生室
            //根据单位id查询单位信息
            Useryy useryy = useryyMapper.selectByPrimaryKey(sysid);
            if(useryy==null){
                //抛出异常，可预知异常
                ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
            }
            sysmc = useryy.getMc();
        }else if(groupid.equals("4")){
            //供货商
            //根据单位id查询单位信息
            Usergys usergys = usergysMapper.selectByPrimaryKey(sysid);
            if(usergys==null){
                //抛出异常，可预知异常
                ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 217, null));
            }
            sysmc = usergys.getMc();
        }

        SysuserCustom sysuserCustom = new SysuserCustom();

        //将sysuser中数据设置到sysuserCustom
        BeanUtils.copyProperties(sysuser, sysuserCustom);

        sysuserCustom.setSysmc(sysmc);//单位名称

        return sysuserCustom;
    }

    public int  editSysuser(String userId, SysuserCustom sysuser) throws Exception {
        if(StringUtils.isEmpty(userId)){
            ResultUtil.throwExcepion(215);
        }
        Sysuser update_user = findSysUserByUserId(userId);
        if(update_user ==null){
            ResultUtil.throwExcepion(215);
        }
        String userid = update_user.getUserid();
        String updata_userId = sysuser.getUserid();
        if(!userid.equals(updata_userId)){
           Sysuser orther_user =  findSysUserByUserId(updata_userId);
           if(orther_user !=null){
               ResultUtil.throwExcepion(213);
           }
        }
        String update_userName = sysuser.getUsername();
        String update_userPwd = sysuser.getPwd();
        if(StringUtils.isNotEmpty(update_userName)){
            update_user.setUsername(update_userName);
        }
        if(StringUtils.isNotEmpty(update_userPwd)){

            update_user.setPwd(update_userPwd);
        }
        String check = checkSysmcByGroupIdAndSysMc(sysuser);
        if(StringUtils.isEmpty(check)){
            ResultUtil.throwExcepion(217);
        }
        update_user.setGroupid(sysuser.getGroupid());
        String updata_sysid = null;
        if(!"admin".equals(check)){
            updata_sysid=check;
        }
        update_user.setSysid(updata_sysid);
        update_user.setUserstate(sysuser.getUserstate());
       return sysuserMapper.updateByPrimaryKey(update_user);
    }
}
