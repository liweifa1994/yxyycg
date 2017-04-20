package com.kargo.yycg.base.control;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kargo.yycg.base.pojo.po.Usergys;
import com.kargo.yycg.base.pojo.vo.UsergysCustom;
import com.kargo.yycg.base.pojo.vo.UsergysQueryVo;
import com.kargo.yycg.base.pojo.vo.UserjdCustom;
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
public class UsergysControl {
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
    @RequestMapping("/usergysquery")
    public String usergysquery(Model model)throws Exception{
        return View.toBase("/user/usergysquery");
    }

    /**
     * 查询医院信息结果
     * @param usergysQueryVo
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @RequestMapping("/usergysquery_result")
    public @ResponseBody
    DataGridResultInfo usergysquery_result(UsergysQueryVo usergysQueryVo, int page, int rows)throws Exception{

        if(usergysQueryVo == null){
            usergysQueryVo = new UsergysQueryVo();
        }
        Page<UsergysCustom> pages= PageHelper.startPage(page,rows);

        //查询医院列表
        List<UsergysCustom> list = userManagerService.findUsergyslist(usergysQueryVo);

        DataGridResultInfo queryResultInfo = ResultUtil.createQueryResult(ResultUtil.createSuccess(Config.MESSAGE,906, null));

        queryResultInfo.setRows(list);
        queryResultInfo.setTotal((int)pages.getTotal());
        return queryResultInfo;
    }
    @RequestMapping("/usergysview")
    public String usergysview(String editid,Model model)throws Exception{
        if(editid!=null){
            Usergys usergys = userManagerService.getUsergysById(editid);
            if(usergys !=null){
                model.addAttribute("usergys", usergys);

            }
        }

        return View.toBase("/user/usergysview");
    }
    @RequestMapping("/usergysedit")
    public String usergysedit(String editid,Model model)throws Exception{
        if(editid!=null){
            Usergys usergys = userManagerService.getUsergysById(editid);
            if(usergys !=null){
                model.addAttribute("usergys", usergys);

                //供货地区
                UsergysQueryVo usergysQueryVo  =new UsergysQueryVo();
                UsergysCustom usergysCustom = new UsergysCustom();
                usergysCustom.setId(editid);
                usergysQueryVo.setUsergysCustom(usergysCustom);
                List<UsergysCustom> list = userManagerService.findUsergyslist(usergysQueryVo);
                if(list!=null && list.size()==1){
                    //供货地区
                    String usergysghdq;
                    String usergysghdqid;
                    usergysghdq = list.get(0).getUseryyghdq();
                    usergysghdqid = list.get(0).getUseryyghdqid();
                    model.addAttribute("usergysghdq", usergysghdq);
                    model.addAttribute("usergysghdqid", usergysghdqid);
                }
            }else{
                ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 902, null));
            }
        }

        return View.toBase("/user/usergysedit");
    }
    @RequestMapping("/usergyssave")
    public @ResponseBody
    SubmitResultInfo usergyssave(UsergysQueryVo usergysQueryVo)throws Exception{

        if(usergysQueryVo == null || usergysQueryVo.getUsergys() == null || usergysQueryVo.getUsergys().getMc()==null){
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE, 901, null));
        }
        Usergys usergys = usergysQueryVo.getUsergys();
        if(usergys.getId()!=null && usergys.getId().length()>0){
            userManagerService.updateUsergys(usergys,usergysQueryVo.getUsergysghdqid());
        }else{
            userManagerService.insertUsergys(usergys,usergysQueryVo.getUsergysghdqid());
        }

        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }
    @RequestMapping("/usergysdel")
    public @ResponseBody SubmitResultInfo usergysdel(String delid)throws Exception{
        if(delid == null || delid.equals("")){
            ResultUtil.throwExcepion(ResultUtil.createWarning(Config.MESSAGE,901, null));
        }
        userManagerService.deleteUsergys(delid);

        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null));
    }
}
