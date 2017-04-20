package com.kargo.yycg.base.control;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kargo.yycg.base.pojo.po.Sysuser;
import com.kargo.yycg.base.pojo.vo.SysuserCustom;
import com.kargo.yycg.base.pojo.vo.SysuserQueryVo;
import com.kargo.yycg.base.process.context.Config;
import com.kargo.yycg.base.process.result.DataGridResultInfo;
import com.kargo.yycg.base.process.result.ResultUtil;
import com.kargo.yycg.base.process.result.SubmitResultInfo;
import com.kargo.yycg.base.service.SysuserService;
import com.kargo.yycg.base.service.impl.BaseServiceFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lwf on 17-1-17.
 */
@Controller
@RequestMapping("/user")
public class UserControl {

    @Autowired
    private SysuserService sysuserService ;

    @Autowired
    private BaseServiceFacade baseServiceFacade;
    /**
     * 修改密码
     * @return
     * @throws Exception
     */
    @RequestMapping("/updatepwd.action")
    public String updatepwdshow() throws Exception {

        return View.toBase("/user/updatepwd");
    }

    /**
     * 修改密码提交
     * @param request
     * @param newpwd
     * @param oldpwd
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveapwd.action")
    public @ResponseBody SubmitResultInfo saveapwd(HttpServletRequest request, String[] newpwd, String oldpwd, Model model) throws Exception {

//        if(oldpwd == null || oldpwd.equals("")){
//            return ResultUtil.createSubmitResult(ResultUtil.createWarning(Config.MESSAGE,121, null));
//        }
//        if(newpwd == null || newpwd.length!=2){
//            return ResultUtil.createSubmitResult(ResultUtil.createWarning(Config.MESSAGE,122, null));
//        }
//
//        if(!MyUtil.isNotNullAndEmpty(newpwd[0]) || !MyUtil.isNotNullAndEmpty(newpwd[1])){
//            return ResultUtil.createSubmitResult(ResultUtil.createWarning(Config.MESSAGE,122, null));
//        }
//
//        String pwd = newpwd[0].trim();
//        String pwdrepeat = newpwd[1].trim();
//        if(!pwd.equals(pwdrepeat)){
//            return ResultUtil.createSubmitResult(ResultUtil.createWarning(Config.MESSAGE,123, null));
//        }
//        ActiveUser activeUser = (ActiveUser)request.getSession().getAttribute(Config.ACTIVEUSER_KEY);
//        //更新密码
//        baseServiceFacade.getUserManagerService().updateUserPWD(activeUser.getUserid(),pwd,oldpwd);
//        //记录日志
//        baseServiceFacade.getSystemConfigService().saveSuccessLog(activeUser.getUserid(),activeUser.getUsername(),IpUtil.getClientIp(request),ResultUtil.createInfo(Config.MESSAGE,120, null).getMessage());
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE,906, null));

    }
    /**
     * 系统退出
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/logout.action")
    public String logout(HttpServletRequest request) throws Exception {

        request.getSession().invalidate();
        return View.redirect("/first.action");
    }


    /********************系统用户维护*********************/


    /**
     * 查询系统用户信息页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/sysuserquery")
    public String sysuserquery(Model model)throws Exception{
        List usergroupList =  baseServiceFacade.getSystemConfigService().findDicttypeinfolist("s01");
        model.addAttribute("usergroupList", usergroupList);
        return View.toBase("/user/sysuserquery");
    }

    @RequestMapping("/sysuserquery_result")
    @ResponseBody
    public DataGridResultInfo findSysuser(SysuserQueryVo sysuser, int page,int rows) throws Exception{
        //非空校验
        sysuser = sysuser!=null?sysuser:new SysuserQueryVo();
        Page<SysuserCustom> pages=PageHelper.startPage(page,rows);
        List<SysuserCustom> sysuserCustomList = sysuserService.findSysuserListByCondition(sysuser);
        DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
        //填充 total
        dataGridResultInfo.setTotal((int)pages.getTotal());
        //填充  rows
        dataGridResultInfo.setRows(sysuserCustomList);
        return dataGridResultInfo;
    }

    @RequestMapping("/sysuseredit")
    public String sysuseredit(String editid,Model model)throws Exception{
        if(editid!=null){
            Sysuser sysuser = baseServiceFacade.getUserManagerService().findSysuserById(editid);
            if(sysuser !=null){
                model.addAttribute("sysuser", sysuser);
                //获取单位名称
                String sysmc = baseServiceFacade.getUserManagerService().getSysmcBySysid(sysuser.getGroupid(), sysuser.getSysid());
                if(sysmc!=null){
                    model.addAttribute("sysmc", sysmc);
                }
            }else{
                ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 902, null));
            }
        }
        List usergroupList =  baseServiceFacade.getSystemConfigService().findDicttypeinfolist("s01");
        model.addAttribute("usergroupList", usergroupList);
        return View.toBase("/user/sysuseredit");
    }
    //add
    @RequestMapping("/addsysuser")
    public String addSysUser(Model model)throws Exception{
        return "/base/user/addsysuser";
    }
    @RequestMapping("/sysusersave")
    public @ResponseBody SubmitResultInfo sysusersave(SysuserQueryVo sysuserQueryVo)throws Exception{

        if(sysuserQueryVo == null || sysuserQueryVo.getSysuserCustom() == null || sysuserQueryVo.getSysuserCustom().getUserid()==null){
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 901, null));
        }
        SysuserCustom sysuser= sysuserQueryVo.getSysuserCustom();
        String groupid = sysuser.getGroupid();
        if(!groupid.equals("1") && !groupid.equals("2") && !groupid.equals("3") && !groupid.equals("4") && !groupid.equals("0")){
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE,203, null));
        }
        //非系统管理员时校验单位
        if(!groupid.equals("0")){
            //校验单位信息
            String sysid = baseServiceFacade.getUserManagerService().getSysidBySysmc(sysuserQueryVo.getSysuserCustom().getGroupid(),sysuserQueryVo.getSysuserCustom().getSysmc());
            if(sysid == null){
                ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE,204, null));
            }
            sysuser.setSysid(sysid);//单位id
        }
        if(sysuser.getId()!=null && sysuser.getId().length()>0){
            baseServiceFacade.getUserManagerService().editSysuser(sysuser.getUserid(),sysuser);
        }else{
            baseServiceFacade.getUserManagerService().addSysUser(sysuser);
        }

        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }
    @RequestMapping("/sysuserdel")
    public @ResponseBody SubmitResultInfo sysuserdel(String delid)throws Exception{
        if(delid == null || delid.equals("")){
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE,901, null));
        }
        baseServiceFacade.getUserManagerService().deleteSysUserById(delid);

        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }




}
