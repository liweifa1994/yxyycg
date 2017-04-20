package com.kargo.yycg.base.process.context;

import com.kargo.yycg.base.pojo.vo.Menu;
import com.kargo.yycg.base.pojo.vo.Operation;
import com.kargo.yycg.common.utils.FileUtil;
import com.kargo.yycg.common.utils.JacksonUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lwf on 17-2-3.
 */
public class Context {
    /**
     * 系统配置参数存储map
     * key在Config.java类中定义
     */
    private static Map<String,String> config = new HashMap<String, String>();

    /**
     * 系统公开action，无需登录即可使用
     */
    private static List<Operation> anonymousActions = new ArrayList<Operation>();

    /**
     * 系统公用action，无需授权即可使用
     */
    private static List<Operation> commonActions = new ArrayList<Operation>();



    /**
     * 获取配置参数静态方法
     * @param key
     * @return 配置参数值
     */
    public static String getConfig(String key){
        return config.get(key);
    }
    /**
     * 设置配置参数方法
     * @param key
     * @param value
     * @return
     */
    public static void setConfig(String key,String value){
        config.put(key,value);
    }


    public static List<Operation> getCommonActions() {
        return commonActions;
    }
    public static void setCommonActions(List<Operation> commonActions) {
        Context.commonActions = commonActions;
    }
    public static List<Operation> getAnonymousActions() {
        return anonymousActions;
    }
    public static void setAnonymousActions(List<Operation> anonymousActions) {
        Context.anonymousActions = anonymousActions;
    }


    /**
     * 实时获取单个用户类型的全部菜单信息
     * @throws Exception
     */
    public static Menu getMenuByGroupid(String groupid) throws Exception {
        //加载菜单权限
        //获取菜单权限文件
        if(groupid == null){
            return null;
        }
        //菜单文件
        String fileName = "menu_"+groupid+".json";
        InputStream inputStream = Context.class.getClassLoader().getResourceAsStream(fileName);
        //json串
        String jsonString = FileUtil.readFileToString(inputStream);
        Menu menu = JacksonUtils .jsonToPojo(jsonString, Menu.class);
        return menu;

    }
}
