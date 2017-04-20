package com.kargo.yycg.base.control;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kargo.yycg.base.pojo.po.BssSysArea;
import com.kargo.yycg.base.pojo.po.Dictinfo;
import com.kargo.yycg.base.pojo.po.Useryy;
import com.kargo.yycg.base.pojo.vo.UseryyCustom;
import com.kargo.yycg.base.pojo.vo.UseryyQueryVo;
import com.kargo.yycg.base.process.context.Config;
import com.kargo.yycg.base.process.result.DataGridResultInfo;
import com.kargo.yycg.base.process.result.ResultUtil;
import com.kargo.yycg.base.process.result.SubmitResultInfo;
import com.kargo.yycg.base.service.UserManagerService;
import com.kargo.yycg.base.service.impl.BaseServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lwf on 17-1-17.
 */

@Controller
@RequestMapping("/user")
public class UseryyControl {


    @Autowired
    BaseServiceFacade baseServiceFacade;

    @Autowired
    UserManagerService userManagerService;
    /**
     * 查询医院信息页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/useryyquery")
    public String useryyQuery(Model model)throws Exception{
        //医院类型
        List yyjbList =  baseServiceFacade.getSystemConfigService().findDicttypeinfolist("005");
        List yylbList =  baseServiceFacade.getSystemConfigService().findDicttypeinfolist("006");
        model.addAttribute("yyjbList", yyjbList);
        model.addAttribute("yylbList", yylbList);
        return View.toBase("/user/useryyquery");
    }

    /**
     * 查询医院信息结果
     * @param useryyQueryVo
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/useryyquery_result")
    public @ResponseBody DataGridResultInfo useryyquery_result(UseryyQueryVo useryyQueryVo, int page, int rows)throws Exception{
        if(useryyQueryVo == null){
            useryyQueryVo = new UseryyQueryVo();
        }
        Page<UseryyCustom> pages= PageHelper.startPage(page,rows);
        //查询医院列表
        List<UseryyCustom> list = baseServiceFacade.getUserManagerService().findUseryyList(useryyQueryVo);

        DataGridResultInfo queryResultInfo = ResultUtil.createQueryResult(ResultUtil.createSuccess(Config.MESSAGE,906, null));

        queryResultInfo.setRows(list);
        queryResultInfo.setTotal((int)pages.getTotal());
        return queryResultInfo;
    }

    @RequestMapping("/useryyview")
    public String useryyview(String editid,Model model)throws Exception{
        if(editid!=null){
            Useryy useryy = userManagerService.findUseryyById(editid);
            if(useryy !=null){
                model.addAttribute("useryy", useryy);
                //获取地区名称
                BssSysArea area = baseServiceFacade.getAreaManagerService().getAreaByID(useryy.getDq());
                if(area!=null){
                    model.addAttribute("useryydqmc", area.getAreaname());
                }
                //医院级别名称
                Dictinfo yyjbinfo = baseServiceFacade.getSystemConfigService().getDictinfoById(useryy.getJb());
                if(yyjbinfo!=null){
                    model.addAttribute("useryyjbmc", yyjbinfo.getInfo());
                }
                //医院类别名称
                Dictinfo yylbinfo = baseServiceFacade.getSystemConfigService().getDictinfoById(useryy.getLb());
                if(yylbinfo!=null){
                    model.addAttribute("useryylbmc", yylbinfo.getInfo());
                }
            }
        }

        return View.toBase("/user/useryyview");
    }
    @RequestMapping("/useryyedit")
    public String useryyedit(String editid,Model model)throws Exception{
        if(editid!=null){
            Useryy useryy = userManagerService.findUseryyById(editid);
            if(useryy !=null){
                model.addAttribute("useryy", useryy);
                //获取地区名称
                BssSysArea area = baseServiceFacade.getAreaManagerService().getAreaByID(useryy.getDq());
                if(area!=null){
                    model.addAttribute("useryydqmc", area.getAreaname());
                }
            }else{
                ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 902, null));
            }
        }
        List yyjbList =  baseServiceFacade.getSystemConfigService().findDicttypeinfolist("005");
        List yylbList =  baseServiceFacade.getSystemConfigService().findDicttypeinfolist("006");
        model.addAttribute("yyjbList", yyjbList);
        model.addAttribute("yylbList", yylbList);
        return View.toBase("/user/useryyedit");
    }
    @RequestMapping("/useryysave")
    public @ResponseBody
    SubmitResultInfo useryysave(UseryyQueryVo useryyQueryVo)throws Exception{

        if(useryyQueryVo == null || useryyQueryVo.getUseryy() == null || useryyQueryVo.getUseryy().getMc()==null){
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 901, null));
        }
        Useryy useryy = useryyQueryVo.getUseryy();
        if(useryy.getId()!=null && useryy.getId().length() >0){
            userManagerService.updateUseryy(useryy);
        }else{
            userManagerService.insertUseryy(useryy);
        }

        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }
    @RequestMapping("/useryydel")
    public @ResponseBody SubmitResultInfo useryydel(String delid)throws Exception{
        if(delid == null || delid.equals("")){
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE,901, null));
        }
        userManagerService.deleteUseryy(delid);

        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }


}
