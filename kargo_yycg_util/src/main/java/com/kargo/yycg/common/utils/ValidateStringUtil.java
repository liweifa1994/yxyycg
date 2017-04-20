package com.kargo.yycg.common.utils;

/**
 * Created by lwf on 16-12-5.
 */
public class ValidateStringUtil {

    /**
     *
     * @param str
     * @return
     */
    public static  boolean isNull(String str){
        if( str != null && str.length()>0){
            return  false;
        }else {
            return true;
        }

    }

    public static boolean objectIsNull(Object obj){
        if( obj != null){
            return false;
        }else{
            return true;
        }
    }
}
