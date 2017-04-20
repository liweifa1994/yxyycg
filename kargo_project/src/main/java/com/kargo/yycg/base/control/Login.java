package com.kargo.yycg.base.control;

import com.kargo.yycg.base.pojo.po.Sysuser;
import com.kargo.yycg.base.pojo.vo.ActiveUser;
import com.kargo.yycg.base.pojo.vo.Menu;
import com.kargo.yycg.base.pojo.vo.Operation;
import com.kargo.yycg.base.pojo.vo.ParamUser;
import com.kargo.yycg.base.process.context.Config;
import com.kargo.yycg.base.process.result.ResultUtil;
import com.kargo.yycg.base.process.result.SubmitResultInfo;
import com.kargo.yycg.base.service.impl.BaseServiceFacade;
import com.kargo.yycg.common.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwf on 17-2-3.
 */
@Controller
@RequestMapping("/user")
public class Login {
    @Autowired
    BaseServiceFacade baseServiceFacade;


    @RequestMapping("/login")
    public @ResponseBody
    SubmitResultInfo login(HttpServletRequest request, ParamUser paramUser, String randomcode, Model model) throws Exception {

        if(paramUser.getUser() == null){
            return ResultUtil.createSubmitResult(ResultUtil.createWarning(Config.MESSAGE,110, null));
        }
        //session对象
        HttpSession session = request.getSession();
        if (randomcode == null || randomcode.equals("")) {
            //return new ResultInfo(ResultInfo.TYPE_RESULT_WARN,112);
            return ResultUtil.createSubmitResult(ResultUtil.createWarning(Config.MESSAGE,112, null));
        }
//        if (session.getAttribute(ValidateCodeServlet.VALIDATE_CODE) == null) {
//            //return new ResultInfo(ResultInfo.TYPE_RESULT_WARN,112);
//            return ResultUtil.createSubmitResult(ResultUtil.createWarning(Config.MESSAGE,112, null));
//        }
//        String validateCode = session.getAttribute(ValidateCodeServlet.VALIDATE_CODE)
//                .toString();
//        if (!randomcode.equals(validateCode)) {
//            return ResultUtil.createSubmitResult(ResultUtil.createWarning(Config.MESSAGE,113, null));
//        }

        Sysuser user_active =  baseServiceFacade.getUserManagerService().userValidator(paramUser.getUser());

        //获取用户类型
        String userGroupid = user_active.getGroupid();

        //构造用户操作权限列表，用于校验用户权限
        List<Operation> operations = new ArrayList<Operation>();
        //通过配置文件获得用户角色对应的菜单及操作
        //Menu menu = Context.getMenuByGroupid(userGroupid);

        //从数据库获取用户角色对应的菜单操作操作
        //获取用户类型对应的角色
        String roleid = baseServiceFacade.getSystemConfigService().findDictinfoByDictinfocode("s01", userGroupid).getRemark();
        List<Menu> list_menu = baseServiceFacade.getSystemConfigService().findModuleAndOperateByRole(roleid);
        Menu menu = new Menu();
        menu.setMenus(list_menu);

        //使用递归提取用户所有菜单
        List<Menu> menus = new ArrayList<Menu>();
        getAllUserMenu(menu,menus);

        //提取菜单中所有操作
        for(Menu menu_i:menus){
            List<Operation> operations_i = menu_i.getOperations();
            if(operations_i != null){
                for(Operation o_i:operations_i){
                    operations.add(o_i);
                }
            }

        }
        //用户无操作权限时不允许登录
        if(operations.size() == 0){
            return ResultUtil.createSubmitResult(ResultUtil.createFail(Config.MESSAGE,103,null));
        }


		/*//获得用户的管理区域
		List<String> areaidlist = baseServiceFacade.getAreaManagerService().findUserAreaidlist(user_active.getId());

		//用户无管理区域时不允许登录
		if(areaidlist.size() == 0){
			return ResultUtil.createSubmitResult(ResultUtil.createFail(Config.MESSAGE,104,null));
		}*/

        // 记录用户session
        ActiveUser activeUser = new ActiveUser();
        //用户账号
        activeUser.setUserid(user_active.getUserid());
        //用户名称
        activeUser.setUsername(user_active.getUsername());
        //用户类型
        activeUser.setGroupid(user_active.getGroupid());
        String groupname = baseServiceFacade.getSystemConfigService().findDictinfoByDictinfocode("s01", user_active.getGroupid()).getInfo();
        activeUser.setGroupname(groupname);
        //用户id即关联及其业务表id
        activeUser.setSysid(user_active.getSysid());

        //用户操作菜单
        activeUser.setMenu(menu);
        //用户操作权限
        activeUser.setOperationList(operations);
        //用户管理区域
        //activeUser.setArealist(areaidlist);


        //将activeUser设置在session
        session.setAttribute(Config.ACTIVEUSER_KEY, activeUser);

        //saveLog(activeuser.getUserid(), activeuser.getUsername(), "登陆系统成功！");
        //ResultInfo resultInfo_success = new ResultInfo(ResultInfo.TYPE_RESULT_SUCCESS,107,new Object[]{activeUser.getUserid()});
        baseServiceFacade.getSystemConfigService().saveSuccessLog(activeUser.getUserid(),activeUser.getUsername(), IpUtil.getClientIp(request),ResultUtil.createInfo(Config.MESSAGE,100, null).getMessage());
        return ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE,107,new Object[]{activeUser.getUserid()}));
    }


    /**
     * 递归获得菜单
     * @param menu
     * @return
     */
    private void getAllUserMenu(Menu menu,List<Menu> menus){
        if(menu !=null && menu.getMenus() == null){
            menus.add(menu);
        }else if(menu !=null && menu.getMenus() != null){
            menus.add(menu);
            for(Menu menu_i:menu.getMenus()){
                getAllUserMenu(menu_i,menus);
            }
        }
    }
}
