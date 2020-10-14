package com.nb.shiro.utils;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * create by lihaoyang on 2020/9/25
 */
public class CommonsLang3Test {

    public static void main(String[] args) {

        ///////////////随机数///////////////
        //随机生成n位数数字
        long st = System.currentTimeMillis();
        for(int i=0;i<100;i++){
            String salt = RandomStringUtils.random(8,true,true);
            System.err.println(salt);
        }
        long end = System.currentTimeMillis();
        System.err.println((end-st));
//        //在指定字符串中生成长度为n的随机字符串
//        random = RandomStringUtils.random(3, "abcdefghijk");
//        System.err.println(random);









    }
}
