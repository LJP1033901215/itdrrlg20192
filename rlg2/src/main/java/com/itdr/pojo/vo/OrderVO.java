package com.itdr.pojo.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderVO {

    private Long orderNo;//订单号

    private BigDecimal payment;//付款金额

    private Integer paymentType;//付款类型

    private Integer postage;

    private Integer status;//订单状态

    private Date paymentTime;//支付时间

    private Date sendTime;//发货时间

    private Date endTime;//订单关闭时间

    private Date closeTime;//结束关闭时间

    private Date createTime;//创建时间

    private List<OrderItemVO> orderItemVOList;//订单详情

    private Integer shippingId;//地址编号

    private ShippingVO shippingVO;//收货地址封装

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<OrderItemVO> getOrderItemVOList() {
        return orderItemVOList;
    }

    public void setOrderItemVOList(List<OrderItemVO> orderItemVOList) {
        this.orderItemVOList = orderItemVOList;
    }

    public ShippingVO getShippingVO() {
        return shippingVO;
    }

    public void setShippingVO(ShippingVO shippingVO) {
        this.shippingVO = shippingVO;
    }
}
