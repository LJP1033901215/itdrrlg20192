package com.itdr.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//加载配置文件的工具类
public class PropertiesUitl {
    public static String getValue(String key) {
    //创建加载配置文件的类
    Properties p = new Properties();
    //通过ProprtiesUitl.class中的方法加载配置文件
    InputStream ip = PropertiesUitl.class.getClassLoader().getResourceAsStream("ProductVo.properties");
    //将通过IO流加载的配置文件放入到Properties类中进行加载
    try {
            p.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //通过健去配置文件中寻找健对应的值
        String key1 = p.getProperty(key);
        //返回值
        return key1;
    }

    //测试
    public static void main(String[] args) {
        System.out.println( getValue("imageHost"));
    }
}

