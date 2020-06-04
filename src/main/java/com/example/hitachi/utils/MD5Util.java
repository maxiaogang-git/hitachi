package com.example.hitachi.utils;

import org.springframework.util.DigestUtils;

public class MD5Util {

    public static String getMD5Str(String str){
        String md5Str = DigestUtils.md5DigestAsHex(str.getBytes());
        return md5Str;
    }
}
