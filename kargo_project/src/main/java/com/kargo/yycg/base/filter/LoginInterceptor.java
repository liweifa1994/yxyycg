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
public class LoginInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(LoginInterceptor.class);


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod)handler;
        Object action = method.getBean();
        // TODO Auto-generated method stub
        log.info("==============执行顺序: 1、preHandle================");
        log.info("==============request url: "+request.getRequestURL());
		/*String url = request.getRequestURL().toString();
		if (mappingURL == null || url.matches(mappingURL)) {
			request.getRequestDispatcher("/msg.jsp").forward(request, response);
			return false;
		}*/
        //请求url
        String url = request.getRequestURL().toString();
        String[]  paths= url.split("/");
        url = paths[paths.length-1];
        HttpSession session = request.getSession();
        //当前登陆用户
        ActiveUser activeUser = (ActiveUser)session.getAttribute(Config.ACTIVEUSER_KEY);

        if(activeUser!=null){//已登录的用户不再拦截
            return true;
        }else if(Context.getAnonymousActions().contains(new Operation(url))){//未登录的用户操作公开权限不再拦截
            return true;
        }

        //request.getRequestDispatcher(Config.LOGIN_PAGE).forward(request, response);
        ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE,106, null));

        return false;
    }

    // 在业务处理器处理请求执行完成后,生成视图之前执行的动作
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("==============执行顺序: 2、postHandle================");
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       // TODO Auto-generated method stub
        log.info("==============执行顺序: 3、afterCompletion================");
    }
}