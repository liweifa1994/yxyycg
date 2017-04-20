package com.kargo.yycg.base.filter;

import com.kargo.yycg.base.pojo.vo.ActiveUser;
import com.kargo.yycg.base.pojo.vo.Operation;
import com.kargo.yycg.base.process.context.Config;
import com.kargo.yycg.base.process.context.Context;
import com.kargo.yycg.base.process.result.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by lwf on 17-2-3.
 */
public class PermissionInterceptor  implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(PermissionInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod)handler;
        Object action = method.getBean();
        // TODO Auto-generated method stub
        //log.info("==============执行顺序: 1、preHandle================");
		/*String url = request.getRequestURL().toString();
		if (mappingURL == null || url.matches(mappingURL)) {
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
			return false;
		}

		request.getRequestDispatcher("/login.action").forward(request, response);*/
        //请求url
        String url = request.getRequestURL().toString();
        String[]  paths= url.split("/");
        url = paths[paths.length-1];
        //公开权限不再拦截
        if(Context.getAnonymousActions().contains(new Operation(url))){
            return true;
        }
        //公共权限范围内的请求不再拦截
        if(Context.getCommonActions().contains(new Operation(url))){
            return true;
        }
        //用户权限范围内的请求不再拦截
        HttpSession session = request.getSession();
        ActiveUser activeuser = (ActiveUser)session.getAttribute(Config.ACTIVEUSER_KEY);
        if (activeuser != null && activeuser.getOperationList() != null
                && activeuser.getOperationList().contains(new Operation(url))) {
            return true;
        }
		/*if (activeuser != null && activeuser.getIscenteruser() != null
				&& activeuser.getIscenteruser().equals("1")) {
			return true;
		}*/

        //抛出无权限操作异常
        //request.getRequestDispatcher(Config.REFUSE_PAGE).forward(request, response);
        System.out.println("*******nopermission*********");
        System.out.println(url);
        ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE,105, null));

        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
