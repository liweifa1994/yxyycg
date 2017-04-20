package com.kargo.yycg.base.control;

import com.kargo.yycg.base.pojo.vo.ActiveUser;
import com.kargo.yycg.base.pojo.vo.Menu;
import com.kargo.yycg.base.process.context.Config;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lwf on 1/7/17.
 */

@Controller
public class IndexControl {


    @RequestMapping("/first")
    public String index(Model model)throws Exception{
        return View.toBase("/first");
    }

    @RequestMapping("/welcome")
    public String welcome(Model model){
        return View.toBase("/welcome");
    }

    @RequestMapping("/menu")
    public @ResponseBody
    Menu menu(HttpServletRequest request) {
        return ((ActiveUser)request.getSession().getAttribute(Config.ACTIVEUSER_KEY)).getMenu();
    }
}
