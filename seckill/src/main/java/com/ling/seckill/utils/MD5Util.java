package com.ling.seckill.utils;/**
 * Created by Ky2Fe on 2021/7/26 15:55
 */

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @program: seckill
 * @description: md5加密工具类
 **/

public class MD5Util {
    private static final String salt="1a2b3c4d";

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    public static String inputPassToFormPass(String inputPass){
        String str=""+salt.charAt(2)+salt.charAt(4)+inputPass+salt.charAt(5)+salt.charAt(6);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass,String salt){
        String str=""+salt.charAt(2)+salt.charAt(4)+formPass+salt.charAt(5)+salt.charAt(6);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String salt){
        String formPass = inputPassToFormPass(inputPass);
        String dBPass = formPassToDBPass(formPass, salt);
        return dBPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(inputPassToDBPass("123456",salt));
    }

}
