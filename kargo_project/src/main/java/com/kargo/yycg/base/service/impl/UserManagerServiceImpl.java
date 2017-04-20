package com.kargo.yycg.base.service.impl;

import com.kargo.yycg.base.dao.BaseDaoFacade;
import com.kargo.yycg.base.pojo.po.*;
import com.kargo.yycg.base.pojo.vo.*;
import com.kargo.yycg.base.process.context.Config;
import com.kargo.yycg.base.process.result.ResultUtil;
import com.kargo.yycg.base.service.UserManagerService;
import com.kargo.yycg.common.utils.MD5;
import com.kargo.yycg.common.utils.UUIDBuild;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lwf on 17-1-17.
 */

@Service
public class UserManagerServiceImpl implements UserManagerService {

    @Autowired
   private BaseDaoFacade baseDaoFacade ;

    public Sysuser userValidator(Sysuser user) throws Exception {
        // 校验用户账号
        if (user.getUserid() == null || user.getUserid().equals("")) {
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE,
                    110, null));
        }
        // 校验密码
        if (user.getPwd() == null || user.getPwd().equals("")) {
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE,
                    111, null));
        }
        // 从数据库获取用户信息
        Sysuser user_active = this.findSysUserByUserId(user.getUserid());
        if (user_active == null) {
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE,101,null));// 用户不存在
        } else {
            String user_password = user_active.getPwd();
            if (user_password == null
                    || !user_password.equalsIgnoreCase(new MD5()
                    .getMD5ofStr(user.getPwd()))) {
                // 密码错误
                ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE,
                        114, null));
            }
        }

        return user_active;

    }

    public List<SysuserCustom> findSysuserListByCondition(SysuserQueryVo sysuserQueryVo) throws Exception {

        return baseDaoFacade.getUserManagerMapperCustom().findSysuserListByCondition(sysuserQueryVo);
    }

    public SysuserCustom findSysuserById(String id) throws Exception {
        //从数据库查询用户
        Sysuser sysuser  =baseDaoFacade.getSysuserMapper().selectByPrimaryKey(id);

        //根据sysid查询单位名称
        String groupid = sysuser.getGroupid();
        String sysid = sysuser.getSysid();//单位id
        String sysmc  =null;
        if(groupid.equals("1") || groupid.equals("2")){
            //监督单位
            //根据单位id查询单位信息
            Userjd userjd = baseDaoFacade.getUserjdMapper().selectByPrimaryKey(sysid);
            if(userjd==null){
                //抛出异常，可预知异常
                ResultUtil.throwExcepion(217);
            }
            sysmc = userjd.getMc();
        }else if(groupid.equals("3")){
            //卫生室
            //根据单位id查询单位信息
            Useryy useryy =baseDaoFacade.getUseryyMapper().selectByPrimaryKey(sysid);
            if(useryy==null){
                //抛出异常，可预知异常
                ResultUtil.throwExcepion(217);
            }
            sysmc = useryy.getMc();
        }else if(groupid.equals("4")){
            //供货商
            //根据单位id查询单位信息
            Usergys usergys = baseDaoFacade.getUsergysMapper().selectByPrimaryKey(sysid);
            if(usergys==null){
                //抛出异常，可预知异常
                ResultUtil.throwExcepion(217);
            }
            sysmc = usergys.getMc();
        }

        SysuserCustom sysuserCustom = new SysuserCustom();

        //将sysuser中数据设置到sysuserCustom
        BeanUtils.copyProperties(sysuser, sysuserCustom);

        sysuserCustom.setSysmc(sysmc);//单位名称

        return sysuserCustom;
    }

    public Sysuser findSysUserByUserId(String userId) throws Exception {
        if(StringUtils.isEmpty(userId)){
            ResultUtil.throwExcepion(215);
        }
        SysuserExample example = new SysuserExample();
        SysuserExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        List<Sysuser> sysusers = baseDaoFacade.getSysuserMapper().selectByExample(example);
        if(sysusers!=null&&sysusers.size()==1){
            return sysusers.get(0);
        }
        return null;
    }


    public void addSysUser(SysuserCustom sysuserCustom) throws Exception {

        if(sysuserCustom == null || StringUtils.isEmpty(sysuserCustom.getUserid()) || StringUtils.isEmpty(sysuserCustom.getGroupid()) || StringUtils.isEmpty(sysuserCustom.getUsername())){
            ResultUtil.throwExcepion(901);
        }
        //唯一性校验
        Sysuser sysuser = findSysUserByUserId(sysuserCustom.getUserid());
        if(sysuser != null){
            ResultUtil.throwExcepion(208);
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
            baseDaoFacade.getSysuserMapper().insert(sysuserCustom);
        }else{
            ResultUtil.throwExcepion(204);
        }
    }

    public int editSysuser(String userId, SysuserCustom sysuser) throws Exception {
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
        return baseDaoFacade.getSysuserMapper().updateByPrimaryKey(update_user);
    }

    public int deleteSysUserById(String userId) throws Exception {
        if(StringUtils.isEmpty(userId)){
            ResultUtil.throwExcepion(901);
        }
        int key = baseDaoFacade.getSysuserMapper().deleteByPrimaryKey(userId);

        return key;
    }

    public String checkSysmcByGroupIdAndSysMc(SysuserCustom sysuserCustom) throws Exception {
        String sysmc = sysuserCustom.getSysmc();
        String groupId = sysuserCustom.getGroupid();
        if(groupId.equals("0")){
            return "admin";
        }
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



    public Useryy findUseryyBySysmc(String sysmc) throws Exception {
        if(StringUtils.isEmpty(sysmc)){
            ResultUtil.throwExcepion(210);
        }
        UseryyExample useryyExample = new UseryyExample();
        UseryyExample.Criteria criteria = useryyExample.createCriteria();
        criteria.andMcEqualTo(sysmc);
        List<Useryy> useryyList = baseDaoFacade.getUseryyMapper().selectByExample(useryyExample);
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
        List<Userjd> userjdList = baseDaoFacade.getUserjdMapper().selectByExample(userjdExample);
        if(userjdList !=null &&userjdList.size()==1){
            return userjdList.get(0);
        }
        return null;
    }

    public void insertUserjdCheck(Userjd userjd) throws Exception{
        if(findUserjdBySysmc(userjd.getMc())!=null){
            ResultUtil.throwExcepion(201);
        }

    }
    public void insertUserjd(Userjd userjd) throws Exception {
        if(userjd == null ||userjd.getMc() == null){
            ResultUtil.throwExcepion(901);
        }
        insertUserjdCheck(userjd);
        userjd.setId(UUIDBuild.getUUID());
        baseDaoFacade.getUserjdMapper().insert(userjd);
    }
    public void updateUserjdCheck(Userjd userjd) throws Exception{

        Userjd oldUserjd = findUserjdBySysmc(userjd.getMc());
        if(oldUserjd!=null && !oldUserjd.getId().equals(oldUserjd.getId())){
            ResultUtil.throwExcepion(201);
        }

    }
    public void updateUserjd(Userjd userjd) throws Exception {
        if(userjd == null || userjd.getId()==null|| userjd.getMc() ==null){
            ResultUtil.throwExcepion(901);
        }
        //唯一性校验
        updateUserjdCheck(userjd);
        //更新监督机构信息
        baseDaoFacade.getUserjdMapper().updateByPrimaryKey(userjd);
    }

    public void deleteUserjd(String id) throws Exception {
        try {
            baseDaoFacade.getUserjdMapper().deleteByPrimaryKey(id);
        } catch (Exception e) {
            ResultUtil.throwExcepion(202);
        }
    }

    public List<UsergysCustom> findUsergyslist(UsergysQueryVo usergysQueryVo) throws Exception {
        return baseDaoFacade.getUserManagerMapperCustom().findUsergyslist(usergysQueryVo);
    }

    public Usergys getUsergysById(String id) throws Exception {
        if(StringUtils.isEmpty(id)){
            ResultUtil.throwExcepion(901);
        }
        return baseDaoFacade.getUsergysMapper().selectByPrimaryKey(id);
    }

    public Usergys findUsergysBySysmc(String sysmc) throws Exception {
        if(StringUtils.isEmpty(sysmc)){
            ResultUtil.throwExcepion(211);
            return null;
        }
        UsergysExample usergysExample = new UsergysExample();
        UsergysExample.Criteria criteria = usergysExample.createCriteria();
        criteria.andMcEqualTo(sysmc);
        List<Usergys> usergysList = baseDaoFacade.getUsergysMapper().selectByExample(usergysExample);
        if(usergysList !=null &&usergysList.size()==1){
            return usergysList.get(0);
        }
        return null;
    }
    /**
     * 供应商唯一性校验
     */
    private void insertUsergysCheck(Usergys usergys) throws Exception{
        if(findUsergysBySysmc(usergys.getMc())!=null){
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 201, null));
        }
    }
    private void updateUsergysCheck(Usergys usergys)throws Exception{
        Usergys usergys_l = findUsergysBySysmc(usergys.getMc());
        if(usergys_l!=null && !usergys_l.getId().equals(usergys_l.getId())){
            ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 201, null));
        }
    }
    /**
     * 添加供应商信息
     * @param usergysQueryVo
     * @throws Exception
     */
    public void insertUsergys(Usergys usergys,String usergysghdqid) throws Exception{
        if(usergys == null || usergys.getMc() ==null){
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 901, null));
        }

        //唯一性校验
        insertUsergysCheck(usergys);
        usergys.setId(UUIDBuild.getUUID());
        //添加供应商信息
        baseDaoFacade.getUsergysMapper().insert(usergys);

        //供货区域
        if(usergysghdqid != null){
            saveUsergysGhdq(usergys.getId(),usergysghdqid.split(","));
        }
    }
    /**
     * 修改供应商信息
     * @param usergysQueryVo
     * @throws Exception
     */
    public void updateUsergys(Usergys usergys,String usergysghdqid) throws Exception{
        if(usergys == null || usergys.getId()==null|| usergys.getMc() ==null){
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 901, null));
        }
        //唯一性校验
        updateUsergysCheck(usergys);
        //更新供应商信息
        baseDaoFacade.getUsergysMapper().updateByPrimaryKeyWithBLOBs(usergys);

        //供货区域
        if(StringUtils.isNoneBlank(usergysghdqid)){
            saveUsergysGhdq(usergys.getId(),usergysghdqid.split(","));
        }
    }
    //保存供货区域
    private void saveUsergysGhdq(String usergysid,String[] usergysghdqid_s){
        //删除供应商原来的供货地区
        UsergysareaExample usergysareaExample = new UsergysareaExample();
        UsergysareaExample.Criteria criteria = usergysareaExample.createCriteria();
        criteria.andUsergysidEqualTo(usergysid);
        baseDaoFacade.getUsergysareaMapper().deleteByExample(usergysareaExample);

        //重新添加供货区域
        if(usergysghdqid_s!=null){
            for(String ghdqid:usergysghdqid_s){
                if(ghdqid!=null && !ghdqid.equals("")){
                    //插入供货区域表
                    Usergysarea usergysarea = new Usergysarea();
                    usergysarea.setUsergysid(usergysid);
                    usergysarea.setAreaid(ghdqid);
                    baseDaoFacade.getUsergysareaMapper().insert(usergysarea);
                }
            }
        }

    }
    public void deleteUsergys(String id) throws Exception {
        baseDaoFacade.getUsergysMapper().deleteByPrimaryKey(id);
    }

    public List<UseryyCustom> findUseryyList(UseryyQueryVo useryyQueryVo) throws Exception {
        return baseDaoFacade.getUserManagerMapperCustom().findUseryyListByConndition(useryyQueryVo);
    }

    public Useryy findUseryyById(String id) throws Exception {

        return baseDaoFacade.getUseryyMapper().selectByPrimaryKey(id);
    }
    public void insertUseryyCheck(Useryy useryy) throws Exception{
        if(findUseryyBySysmc(useryy.getMc()) != null){
            ResultUtil.throwExcepion(201);
        }
    }
    public void insertUseryy(Useryy useryy) throws Exception {
        if(useryy == null || useryy.getMc() ==null){
            ResultUtil.throwExcepion(901);
        }
        //唯一性校验
        insertUseryyCheck(useryy);
        useryy.setId(UUIDBuild.getUUID());
        //添加医院信息
        baseDaoFacade.getUseryyMapper().insert(useryy);
    }

    public void updateUseryyCheck(Useryy useryy) throws Exception{
        Useryy oldUseryy = findUseryyBySysmc(useryy.getMc());
        if(oldUseryy == null && !oldUseryy.getId().equals(useryy.getId())){
            ResultUtil.throwExcepion(201);
        }
    }
    public void updateUseryy(Useryy useryy) throws Exception {
        if(useryy == null || useryy.getId()==null|| useryy.getMc() ==null){
            ResultUtil.throwExcepion(901);
        }
        //唯一性校验
        updateUseryyCheck(useryy);
        //更新医院信息
        baseDaoFacade.getUseryyMapper().updateByPrimaryKey(useryy);
    }

    public void deleteUseryy(String id) throws Exception {
         baseDaoFacade.getUseryyMapper().deleteByPrimaryKey(id);
    }

    public List<UserjdCustom> findUserjdlist(UserjdQueryVo userjdQueryVo) throws Exception {

        return baseDaoFacade.getUserManagerMapperCustom().findUserjdlist(userjdQueryVo);
    }

    public Userjd getUserjdById(String id) throws Exception {
        if(StringUtils.isEmpty(id)){
            ResultUtil.throwExcepion(901);
        }
        return baseDaoFacade.getUserjdMapper().selectByPrimaryKey(id);
    }

    /**
     * 根据用户类型和单位名称获取单位id
     * @param groupid
     * @param sysmc
     * @return
     * @throws Exception
     */
    public String getSysidBySysmc(String groupid,String sysmc)throws Exception{
        if(sysmc==null){
            return null;
        }
        String sysid=null;
        if(groupid.equals("1") || groupid.equals("2")){//监督单位
            Userjd userjd = findUserjdBySysmc(sysmc);
            if(userjd != null){
                sysid = userjd.getId();
            }
        }else if(groupid.equals("3")){//医院
            Useryy useryy = findUseryyBySysmc(sysmc);
            if(useryy != null){
                sysid = useryy.getId();
            }
        }else if(groupid.equals("4")){//供货商
            Usergys usergys = findUsergysBySysmc(sysmc);
            if(usergys != null){
                sysid = usergys.getId();
            }
        }
        return sysid;
    }
    /**
     * 根据用户类型和单位id获取单位名称
     * @param groupid
     * @param sysid
     * @return
     * @throws Exception
     */
    public String getSysmcBySysid(String groupid,String sysid)throws Exception{
        if(sysid==null){
            return null;
        }
        String sysmc=null;
        if(groupid.equals("1") || groupid.equals("2")){//监督单位
            Userjd userjd = getUserjdById(sysid);
            if(userjd != null){
                sysmc = userjd.getMc();
            }
        }else if(groupid.equals("3")){//医院
            Useryy useryy = findUseryyById(sysid);
            if(useryy != null){
                sysmc = useryy.getMc();
            }
        }else if(groupid.equals("4")){//供货商
            Usergys usergys = getUsergysById(sysid);
            if(usergys != null){
                sysmc = usergys.getMc();
            }
        }
        return sysmc;
    }
}
