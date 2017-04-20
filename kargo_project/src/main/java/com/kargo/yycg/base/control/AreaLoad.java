package com.kargo.yycg.base.control;

import com.kargo.yycg.base.pojo.po.BssSysArea;
import com.kargo.yycg.base.pojo.vo.AreaCustom;
import com.kargo.yycg.base.service.impl.BaseServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lwf on 17-2-3.
 */
@Controller
@RequestMapping("/areaload")
public class AreaLoad{
    @Autowired
    BaseServiceFacade baseServiceFacade;

    @RequestMapping("/async")
    public @ResponseBody
    List<AreaCustom> async(String areaid) throws Exception{
        if(areaid == null){
            areaid="0";
        }
        List<AreaCustom> list = baseServiceFacade.getAreaManagerService().selectAreaListByParentid(areaid);

        return list;
    }

    @RequestMapping("/sync")
    public @ResponseBody List<AreaCustom> sync(BssSysArea area) throws Exception{
        if(area == null){
            area = new BssSysArea();
        }
        if(area.getAreaid() == null){
            area.setAreaid("0");
        }
        List<AreaCustom> list = baseServiceFacade.getAreaManagerService().selectAreaTreeByAreaid(area);

        return list;
    }


}