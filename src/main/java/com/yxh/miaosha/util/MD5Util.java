package com.yxh.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author galaxy
 * @date 20-2-10 - 下午8:30
 */
public class MD5Util {

    private static String salt = "1a2b3c4d";

    public   static String md5(String src){
        return DigestUtils.md5Hex(src);
    }
    public static String inputPassToFormPass(String inputPass){
        String src = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        return md5(src);
    }
    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        String formPass = inputPassToFormPass("000000");
        System.out.println(formPassToDBPass(formPass,salt));
    }

}
