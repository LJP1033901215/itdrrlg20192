package com.itdr.pojo.vo;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemListVO {
    //返回获取订单详情的封装类
    private List<OrderItemVO> orderItemVOlist;
    private String imageHost;
    private BigDecimal productTotalPrice;

    public List<OrderItemVO> getOrderItemVOlist() {
        return orderItemVOlist;
    }

    public void setOrderItemVOlist(List<OrderItemVO> orderItemVOlist) {
        this.orderItemVOlist = orderItemVOlist;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }
}
