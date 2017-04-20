package com.kargo.yycg.base.process.context;


import com.kargo.yycg.base.pojo.vo.Operation;
import com.kargo.yycg.common.utils.ResourcesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwf on 17-2-3.
 */
public class ConfigLoaderListener implements ServletContextListener {
    private static Logger log = LoggerFactory.getLogger(ConfigLoaderListener.class);
    public void contextInitialized(ServletContextEvent sce) {
        //加载系统参数
        loadSysParams();
        //加载公共权限
        loadCommonActions();
        //加载公开权限
        loadAnonymousActions();
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
    /**
     * 系统参数加载方法
     * baseurl,version_info，加载至Context中的config中，可通过Context中的getConfig获取
     */
    private static  void loadSysParams() {

        log.info("==============初始化系统参数===============");

        //加载sysmanagerurl
        Context.setConfig(Config.SYSMANAGERURL_KEY, ResourcesUtil.getValue(Config.SYSCONFIG,Config.SYSMANAGERURL_KEY));
        log.info("sysmanagerurl="+Context.getConfig(Config.SYSMANAGERURL_KEY));

        //加载sysconfigurl
        Context.setConfig(Config.SYSCONFIGURL_KEY, ResourcesUtil.getValue(Config.SYSCONFIG,Config.SYSCONFIGURL_KEY));
        log.info("sysconfigurl="+Context.getConfig(Config.SYSCONFIGURL_KEY));

        //加载deskey
        Context.setConfig(Config.DESKEY_KEY, ResourcesUtil.getValue(Config.SYSCONFIG,Config.DESKEY_KEY));
        log.info("DESKEY_KEY="+Context.getConfig(Config.DESKEY_KEY));

        //加载版本名称
        Context.setConfig(Config.VERSION_NAME_KEY, ResourcesUtil.getValue(Config.VERSION,Config.VERSION_NAME_KEY));
        log.info("VERSION_NAME="+Context.getConfig(Config.VERSION_NAME_KEY));

        //加载版本号
        Context.setConfig(Config.VERSION_NUMBER_KEY, ResourcesUtil.getValue(Config.VERSION,Config.VERSION_NUMBER_KEY));
        log.info("VERSION_NUMBER="+Context.getConfig(Config.VERSION_NUMBER_KEY));

        //加载版本发布时间
        Context.setConfig(Config.VERSION_DATE_KEY,ResourcesUtil.getValue(Config.VERSION,Config.VERSION_DATE_KEY));
        log.info("VERSION_DATE="+Context.getConfig(Config.VERSION_DATE_KEY));

    }


    /**
     * 加载公开权限(无需登录)
     */
    private static void loadAnonymousActions() {
        List<Operation> anonymousActions = new ArrayList<Operation>();

        List<String> baseActions_list = ResourcesUtil.gekeyList(Config.ANONYMOUS_ACTIONS);
        for (String common_actionUrl:baseActions_list) {
            Operation o_i = new Operation(common_actionUrl);
            anonymousActions.add(o_i);
        }
        Context.setAnonymousActions(anonymousActions);
    }
    /**
     * 加载公共权限
     */
    private static void loadCommonActions() {
        List<Operation> commonActions = new ArrayList<Operation>();

        List<String> baseActions_list = ResourcesUtil.gekeyList(Config.COMMON_ACTIONS);
        log.info(baseActions_list.toString());
        for (String common_actionUrl:baseActions_list) {
            Operation o_i = new Operation(common_actionUrl);
            commonActions.add(o_i);
        }
        Context.setCommonActions(commonActions);
    }
}
