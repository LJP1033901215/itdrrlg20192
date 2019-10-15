package com.itdr.pojo.vo;

import java.math.BigDecimal;
import java.util.List;

//封装类
public class CartVO {
    //将已经封装的类再进行封装
    //传入数据集合
    private List<CartProductVO> cartProductVOS;
    //判断是否是全选状态
    private boolean allChecked;
    //返回金额
    private BigDecimal cartTotalPrice;
    //返回图片的地址
    private String imageHost;

    public List<CartProductVO> getCartProductVOS() {
        return cartProductVOS;
    }

    public void setCartProductVOS(List<CartProductVO> cartProductVOS) {
        this.cartProductVOS = cartProductVOS;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
