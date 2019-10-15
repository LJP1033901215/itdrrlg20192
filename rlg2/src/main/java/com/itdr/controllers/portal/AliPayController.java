package com.itdr.controllers.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;
import com.itdr.pojo.pay.Configs;
import com.itdr.services.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Controller
@ResponseBody
@RequestMapping("/portal/pay/")
public class AliPayController {

@Autowired
PayService payService;
//支付模块==============================================
    @RequestMapping("alipay.do")
    public ServerResponse aliPay(HttpSession session, Long orderNo){

        Users attribute = (Users) session.getAttribute(Const.LOGINUSER);
        if (attribute==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NO_LOGIN.getDesc());
        }
          ServerResponse  sr =   payService.aliPay(orderNo,attribute.getId());
            return sr;
    }
    //支付宝回调函数================================================
    @RequestMapping("alipay_callback.do")
    public String alipayCallback(HttpServletRequest request, HttpSession session){
        Users attribute = (Users) session.getAttribute(Const.LOGINUSER);
        //返回支付宝返回的参数，返回一个Map集合
        Map<String, String[]> parameterMap = request.getParameterMap();
        //获取集合的健的集合
        Set<String> strings = parameterMap.keySet();
        //根据健的集合遍历值得集合
        //创建迭代器
        Iterator<String> iterator = strings.iterator();
        //创建新的集合，把遍历出来的健和值放入
        Map<String,String> newMao = new HashMap<>();
        //创建迭代器数组
        while (iterator.hasNext()){
            //根据健获取对应得值
            String next = iterator.next();
            String[] strings1 = parameterMap.get(next);
            //遍历值得数组重新拼装数据
            StringBuffer ss = new StringBuffer("");
            for (int i = 0; i < strings1.length; i++) {
                ss = (i==strings1.length-1)?ss.append(strings1[i]):ss.append(strings1[i]+",");
            }
            newMao.put(next,ss.toString());

        }
        //去除不必要参数
        newMao.remove("sign_type");
        try {
            //使用官方方法进行验证
            boolean  b = AlipaySignature.rsaCheckV2(newMao,Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            //验证是否成功
            if (!b){
                return "{mag:'验签失败'}";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "{mag:'验签失败'}";
        }
        //y验签通过，去业务层执行业务
        newMao.put("uid",attribute.getId().toString());
        ServerResponse sr = payService.alipayCaliback(newMao);

        //业务层处理完，返回对应的状态信息，这个信息是直接返回给支付宝服务器的，必须要严格准确
        if (sr.isSuccess()){
            return  "SUCCESS";
        }else{
            return "FAILED";
        }


    }
}
