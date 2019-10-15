package com.itdr.utils;

import com.alipay.api.domain.ExtendParams;
import com.alipay.api.domain.GoodsDetail;
import com.itdr.common.Const;
import com.itdr.pojo.*;
import com.itdr.pojo.pay.BizContent;
import com.itdr.pojo.pay.PGoodsDetail;
import com.itdr.pojo.vo.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PoToVoUtil {
    public static ProductVO ProductToProductVO(Product product){
        ProductVO productVO = new ProductVO();
        productVO.setImageHost(PropertiesUitl.getValue("imageHost"));
        productVO.setSubImages(product.getSubImages().split(","));
        productVO.setDetail(product.getDetail());
        productVO.setId(product.getId());
        productVO.setCategoryId(product.getCategoryId());
        productVO.setName(product.getName());
        productVO.setSubtitle(product.getSubtitle());
        productVO.setMainImage(product.getMainImage());
        productVO.setPrice(product.getPrice());
        productVO.setStatus(product.getStock());
        productVO.setIsNew(product.getIsNew());
        productVO.setIsHot(product.getIsHot());
        productVO.setIsBanner(product.getIsBanner());
        productVO.setCreateTime(product.getCreateTime());
        productVO.setUpdateTime(product.getUpdateTime());
        productVO.setIsNew(product.getIsNew());

        return productVO;

    }
   public static CartProductVO getOne(Cart cart,Product product){
       CartProductVO cartProductVO = new CartProductVO();
       cartProductVO.setId(cart.getId());
       cartProductVO.setUserId(cart.getUserId());
       cartProductVO.setProductId(cart.getProductId());
       cartProductVO.setQuantity(cart.getQuantity());
       cartProductVO.setProductChecked(cart.getChecked());

       if (product!=null){
           cartProductVO.setName(product.getName());
           cartProductVO.setSubtitle(product.getSubtitle());
           cartProductVO.setMainImage(product.getMainImage());
           cartProductVO.setPrice(product.getPrice());
           cartProductVO.setStock(product.getStock());
           cartProductVO.setStatus(product.getStatus());
       }
       //设置变量，方便确定 库存中的数量是否比购物车中的数量多
       Integer count = 0 ;
       //处理库存问题
       //判断库存中的数量是否比购物车中的数量多
       if (cart.getQuantity() <= product.getStock()){
           //如果库存数量大于购物车数量，就将购物车数量赋值给常量
           count = cart.getQuantity();
           cartProductVO.setLimitQuantity(Const.Cart.LIMITQUANTITYSUCCESS);
       }else {
           //如果库存数量小于购物车数量，就将库存数量赋值给常量
           count = product.getStock();
           cartProductVO.setLimitQuantity(Const.Cart.LIMITQUANTITYFAILED);
       }
       //将常量赋值给数量
       cartProductVO.setQuantity(count);
       //通过调用BigDecimalUtils工具类 计算本条购物信息总价
       BigDecimal productTotalPrice = BigDecimalUtils.mul(product.getPrice().doubleValue(),cart.getQuantity());
       //将计算出的数据赋值给价格
       cartProductVO.setProductTotalPrice(productTotalPrice);
//返回封装类
return cartProductVO;
   }
    //返回封装类订单详情的封装类
    public static OrderItemVO orderItemTOorderItemVO(Order_item orderItem){
        OrderItemVO orderItemVO = new OrderItemVO();
        orderItemVO.setOrderNo(orderItem.getOrderNo());
        orderItemVO.setProductId(orderItem.getProductId());
        orderItemVO.setProductName(orderItem.getProductName());
        orderItemVO.setProductImage(orderItem.getProductImage());
        orderItemVO.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVO.setQuantity(orderItem.getQuantity());
        orderItemVO.setTotalPrice(orderItem.getTotalPrice());
        orderItemVO.setCreateTime(orderItem.getCreateTime());
        return orderItemVO;
    }
    //返回地址的封装类
    public static ShippingVO shippingTOShippingVO(Shipping shipping){
        ShippingVO shippingVO = new ShippingVO();
        shippingVO.setReceiverName(shipping.getReceiverName());
        shippingVO.setReceiverPhone(shipping.getReceiverPhone());
        shippingVO.setReceiverMobile(shipping.getReceiverMobile());
        shippingVO.setReceiverCity(shipping.getReceiverCity());
        shippingVO.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVO.setReceiverAddress(shipping.getReceiverAddress());
        shippingVO.setReceiverZip(shipping.getReceiverZip());
        return shippingVO;
    }
    //返回对OrderVO类的封装
    public static OrderVO OrderTOOrderVO(Shipping shipping , Order order, List<OrderItemVO> itemVOList){

        ShippingVO shippingVO = PoToVoUtil.shippingTOShippingVO(shipping);
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderItemVOList(itemVOList);
        orderVO.setShippingVO(shippingVO);
        orderVO.setOrderNo(order.getOrderNo());
        orderVO.setShippingId(shippingVO.getId());
        orderVO.setPayment(order.getPayment());
        orderVO.setPostage(order.getPostage());
        orderVO.setStatus(order.getStatus());
        orderVO.setPaymentTime(order.getPaymentTime());
        orderVO.setSendTime(order.getSendTime());
        orderVO.setEndTime(order.getEndTime());
        orderVO.setCloseTime(order.getCloseTime());
        orderVO.setCreateTime(order.getCreateTime());
        return orderVO;
    }
    /*获取一个BizContent对象*/
    public static BizContent getBizContent(Order order, List<Order_item> orderItems) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = String.valueOf(order.getOrderNo());

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "睿乐GO在线平台" + order.getPayment();

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = String.valueOf(order.getPayment());

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买商品" + orderItems.size() + "件共" + order.getPayment() + "元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "001";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "001";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        for (Order_item orderItem : orderItems) {
            // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
            GoodsDetail goods = getNewPay(orderItem);
            // 创建好一个商品后添加至商品明细列表
            goodsDetailList.add(goods);
        }

        BizContent biz = new BizContent();
        biz.setSubject(subject);
        biz.setTotal_amount(totalAmount);
        biz.setOut_trade_no(outTradeNo);
        biz.setUndiscountable_amount(undiscountableAmount);
        biz.setSeller_id(sellerId);
        biz.setBody(body);
        biz.setOperator_id(operatorId);
        biz.setStore_id(storeId);
        biz.setExtend_params(extendParams);
        biz.setTimeout_express(timeoutExpress);
        //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
        //biz.setNotify_url(Configs.getNotifyUrl_test()+"portal/order/alipay_callback.do");
        biz.setGoods_detail(goodsDetailList);

        return biz;
    }

    /*商品详情和支付宝商品类转换*/
    public static PGoodsDetail getNewPay(Order_item orderItem) {
        PGoodsDetail info = new PGoodsDetail();
        info.setGoods_id(orderItem.getProductId().toString());
        info.setGoods_name(orderItem.getProductName());
        info.setPrice(orderItem.getCurrentUnitPrice().toString());
        info.setQuantity(orderItem.getQuantity().longValue());
        return info;
    }
}
