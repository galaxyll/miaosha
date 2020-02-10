package com.yxh.miaosha.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author galaxy
 * @date 20-2-10 - 下午11:00
 */
public class ValidatoUtil {
    private static Pattern mobilePattern = Pattern.compile("^1(3|4|5|6|7|8|9)\\d{9}$");

    public static boolean isMobile(String mobile){
        if (mobile==null||mobile.isEmpty()){
            return false;
        }
        Matcher matcher = mobilePattern.matcher(mobile);
        return matcher.matches();
    }

}
