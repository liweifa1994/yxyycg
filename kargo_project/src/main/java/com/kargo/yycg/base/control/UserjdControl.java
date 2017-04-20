package com.kargo.yycg.base.control;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kargo.yycg.base.pojo.po.BssSysArea;
import com.kargo.yycg.base.pojo.po.Userjd;
import com.kargo.yycg.base.pojo.vo.SysuserCustom;
import com.kargo.yycg.base.pojo.vo.UserjdCustom;
import com.kargo.yycg.base.pojo.vo.UserjdQueryVo;
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
public class UserjdControl {

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
    @RequestMapping("/userjdquery")
    public String userjdquery(Model model)throws Exception{
        return View.toBase("/user/userjdquery");
    }

    /**
     * 查询医院信息结果
     * @param userjdQueryVo
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/userjdquery_result")
    public @ResponseBody
    DataGridResultInfo userjdquery_result(UserjdQueryVo userjdQueryVo, int page, int rows)throws Exception{

        if(userjdQueryVo == null){
            userjdQueryVo = new UserjdQueryVo();
        }
        Page<UserjdCustom> pages= PageHelper.startPage(page,rows);
        //查询医院列表
        List<UserjdCustom> list = userManagerService.findUserjdlist(userjdQueryVo);

        DataGridResultInfo queryResultInfo = ResultUtil.createQueryResult(ResultUtil.createSuccess(Config.MESSAGE,906, null));

        queryResultInfo.setRows(list);
        queryResultInfo.setTotal((int)pages.getTotal());
        return queryResultInfo;
    }
    @RequestMapping("/userjdview")
    public String userjdview(String editid,Model model)throws Exception{
        if(editid!=null){
            Userjd userjd = userManagerService.getUserjdById(editid);
            if(userjd !=null){
                model.addAttribute("userjd", userjd);
                //获取地区名称
                BssSysArea area = baseServiceFacade.getAreaManagerService().getAreaByID(userjd.getDq());
                if(area!=null){
                    model.addAttribute("userjddqmc", area.getAreaname());
                }

            }
        }

        return View.toBase("/user/userjdview");
    }
    @RequestMapping("/userjdedit")
    public String userjdedit(String editid,Model model)throws Exception{
        if(editid!=null){
            Userjd userjd = userManagerService.getUserjdById(editid);
            if(userjd !=null){
                model.addAttribute("userjd", userjd);
                //获取地区名称
                BssSysArea area = baseServiceFacade.getAreaManagerService().getAreaByID(userjd.getDq());
                if(area!=null){
                    model.addAttribute("userjddqmc", area.getAreaname());
                }
            }else{
                ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 902, null));
            }
        }

        return View.toBase("/user/userjdedit");
    }
    @RequestMapping("/userjdsave")
    public @ResponseBody
    SubmitResultInfo userjdsave(UserjdQueryVo userjdQueryVo)throws Exception{

        if(userjdQueryVo == null || userjdQueryVo.getUserjdCustom() == null || userjdQueryVo.getUserjdCustom().getMc()==null){
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 901, null));
        }
        Userjd userjd = userjdQueryVo.getUserjdCustom();
        if(userjd.getId()!=null){
            userManagerService.updateUserjd(userjd);
        }else{
            userManagerService.insertUserjd(userjd);
        }

        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }
    @RequestMapping("/userjddel")
    public @ResponseBody SubmitResultInfo userjddel(String delid)throws Exception{
        if(delid == null || delid.equals("")){
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE,901, null));
        }
        userManagerService.deleteUserjd(delid);

        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }
}
