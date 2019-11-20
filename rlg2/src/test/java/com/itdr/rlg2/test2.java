package com.itdr.rlg2;

import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.google.common.base.Utf8;
import org.junit.Test;
import org.springframework.http.HttpRequest;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class test2 {
    @Test
    public void aa (){
        String S= "aa";
        String[] split = S.split("");
        System.out.println(Arrays.toString(split));
    }
    @Test
    public void ce() throws Exception {
        String url = "http://ddc-open.demo.yyigou.com/rest?timestamp=2019-01-01 00:00:00&apiCode=scs.openlink.delivery.order.push&apiVersion=1.0& signMethod=md5&appSecret=47bbe2fbe7d92d3be07c59bb09b788fad01c37a987eb4bbe067b6fb74994d79a&appKey=974885f2dea9b0224ebba36c323742c10b2b0d1a2671f92d01ed97f07bc74a36&token=1adf1caaa0203cc94338ff7288af76bd";
        String data = getData(url);
        System.out.println(data);
    }
    public String getData(String urlString) throws Exception{
        String res = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            URLConnection conn = (URLConnection)url.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            System.out.println("创建URL");
            String line;
            while ((line = reader.readLine()) !=null){
                System.out.println("分别获取每行内容："+line);
                res+=line;
            }
            reader.close();

        }catch (Exception e){
            e.getMessage();
        }
        System.out.println(res);
        return res;
    }
    {
    }


}

