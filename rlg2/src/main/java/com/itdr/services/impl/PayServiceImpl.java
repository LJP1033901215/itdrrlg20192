package com.itdr.services.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.OrderMapper;
import com.itdr.mappers.Order_itemMapper;
import com.itdr.mappers.PayinfoMapper;
import com.itdr.pojo.Order;
import com.itdr.pojo.Order_item;
import com.itdr.pojo.Payinfo;
import com.itdr.pojo.pay.Configs;
import com.itdr.pojo.pay.ZxingUtils;
import com.itdr.services.PayService;
import com.itdr.utils.DateUtils;
import com.itdr.utils.JsonUtils;
import com.itdr.utils.PoToVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    Order_itemMapper orderItemMapper;
    @Autowired
    PayinfoMapper payinfoMapper;
    //订单支付
    @Override
    public ServerResponse aliPay(Long orderNo ,Integer uid) {
        if (orderNo==null||orderNo<=0){
            return ServerResponse.defeatedRs(Const.NOT_PARAMETER);
        }
        //判断订单是否存在
        Order order = orderMapper.selectByorderNo(orderNo);
        if (order==null){
            return ServerResponse.defeatedRs("该订单不存在");
        }
        //判断订单和用户名是否匹配
        Order order1 = orderMapper.selectByUidAndOrderNo(uid, orderNo);
            if (order1==null){
                return ServerResponse.defeatedRs("用户和订单账号不匹配");
            }
            if (order1.getStatus()!=10){
                return ServerResponse.defeatedRs("状态码不是未付款状态，不能进行此操作");
            }
         //根据订单号查询对应的商品详情
        List<Order_item> orderItems = orderItemMapper.selectByUidAndOrderNo(uid,order.getOrderNo());
            //判断是否付款
        if (order1.getStatus()!=10){
            return ServerResponse.defeatedRs("状态不是未付款状态");
        }
        //调用支付宝接口获取二维码
        try {
            AlipayTradePrecreateResponse response = test_trade_precreate(order, orderItems);
            //响应成功才执行
            if (response.isSuccess()){
                //将二维码信息串生成图片，并保存，（需要修改为运行机器上的路径）
                String filePath = String.format(Configs.getSavecode_test() + "qr-%s.png",
                        response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(),256,filePath);
                //预下单成功获取返回信息（二维码字符串）
                Map map = new HashMap();
                map.put("orderNo",order.getOrderNo());

                map.put("qrCide",filePath);
                return ServerResponse.successRs(map);
            }else{
                //失败的情况
                return ServerResponse.defeatedRs("下单失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ServerResponse.defeatedRs("下单失败");
        }

    }
    //验证回调信息
    @Override
    public ServerResponse alipayCaliback(Map<String, String> newMao) {
        //获取订单编号
        Long orderNo = Long.parseLong(newMao.get("out_trade_no"));
        //获取流水号
        String tarde_no = newMao.get("trade_no");
        //获取支付支付状态
        String trade_status = newMao.get("trade_status");
        //获取支付时间
        String payment_time = newMao.get("gmt_payment");
        //获取金额
        BigDecimal total_anount = new BigDecimal(newMao.get("total_amount"));
        //获取用户ID
        Integer uid = Integer.parseInt(newMao.get("uid"));


        Order order = orderMapper.selectByorderNo(orderNo);

        //验证订单的状态，防止支付宝回调
        if (order.getStatus() != 10){
            return ServerResponse.defeatedRs("订单不是未付款状态");
        }
        //验证订单是否存在
        if (order == null){
            return ServerResponse.defeatedRs("不是付款的订单");
        }
        //判断金额是否相等
        if (order.getPayment().equals(total_anount)){
            return ServerResponse.defeatedRs("支付金额不匹配");
        }
        //判断用户ID是否匹配
        Order order1 = orderMapper.selectByUidAndOrderNo(uid, orderNo);
        if (order1==null){
            return ServerResponse.defeatedRs("用户和订单号不匹配");
        }

        //判断交易状态
        if (trade_status.equals(Const.TRADE_SUCCESS)){
            //更改状态
            order.setStatus(20);
            //更改时间
            order.setPaymentTime(DateUtils.strToDate(payment_time));
            orderMapper.updateByPrimaryKey(order);

            //支付成功，删除本地存在的二维码
            String str = String.format(Configs.getSavecode_realy() + "qr-%s.png",
                    order.getOrderNo());
            File file = new File(str);
            boolean b  = file.delete();
            if (!b){
                return ServerResponse.defeatedRs("二维码删除失败");
            }

        }
        //保存支付宝支付的信息（成功失败都保存）
        Payinfo payinfo = new Payinfo();
        payinfo.setOrderNo(orderNo);//订单号
        payinfo.setPayPlatform(Const.PaymentPlatforEnum.ALIPAY.getCode());
        payinfo.setPlatformStatus(trade_status);//支付状态
        payinfo.setPlatformNumber(tarde_no);//流水号
        payinfo.setUserId(uid);//id
        //插入新的数据
        int insert = payinfoMapper.insert(payinfo);
        if (insert > 0 ){
            //支付信息保存成功，返回结果SUCCESS，让支付宝不再回调
            return ServerResponse.successRs("SUCCESS");
        }

        return null;
    }

    //测试当面支付2.0生成二维码
    private AlipayTradePrecreateResponse test_trade_precreate(Order order, List<Order_item> orderItems) throws AlipayApiException{
        Configs.init("zfbinfo.properties");

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(Configs.getOpenApiDomain(),
                Configs.getAppid(),
                Configs.getPrivateKey(),
                "json",
                "utf-8",
                Configs.getAlipayPublicKey(),
                Configs.getSignType());

        //创建Api对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        //获取一个BigContent对象，并转换成json格式
        String str = JsonUtils.obj2String(PoToVoUtil.getBizContent(order,orderItems));
        request.setBizContent(str);
        //设置支付宝的回调路径
        request.setNotifyUrl(Configs.getNotifyUrl_test());
        AlipayTradePrecreateResponse response = alipayClient.execute(request);

        //返回结果
        return  response;
    }
}
