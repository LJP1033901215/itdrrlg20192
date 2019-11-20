package com.itdr.rlg2;

import com.itdr.utils.MD5Util;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Test;

public class test3 {
    @Test
    public void name(){
        String ceshi = ceshi("scs.openlink.sales.order.pull",
                "1.0",
                "974885f2dea9b0224ebba36c323742c10b2b0d1a2671f92d01ed97f07bc74a36",
                "47bbe2fbe7d92d3be07c59bb09b788fad01c37a987eb4bbe067b6fb74994d79a",
                "1adf1caaa0203cc94338ff7288af76bd",
                "",
                "2019-10-30 15:56:27",
                "{\"params\":{\"gift\":\"N\",\"orderNo\":\"120000000472\"}}");
        System.out.println(ceshi);
    }
    public String ceshi(String apiCode,//方法名
                        String apiVersion,//版本号
                        String appKey,//应用在平台的唯一标识
                        String appSecret,//请求参数的签名密钥，参与签名，但不要附在URL中传递
                        String token,//身份凭证
                        String signMethod,//签名方法，默认为hmac,应用MD5
                        String timestamp,//调用时间戳，用于开放平台校验请求合法性，时间格式：2016-05-17 13:39:44
                        String json)
     {
        StringBuffer sd = new StringBuffer();
        sd.append(appSecret);
        sd.append("apiCode"+apiCode);
        sd.append("apiVersion"+apiVersion);
        sd.append("appKey"+appKey);
        sd.append("appSecret"+appSecret);
        sd.append("timestamp"+timestamp);
        sd.append("token"+token);
        sd.append(json);
        sd.append(appSecret);
         String s = MD5Util.MD5EncodeUtf8(sd.toString());
         return s;
    }
}
