package com.itdr.services;

import com.itdr.common.ServerResponse;

public interface CartService {

    //添加一个商品到购物车
    ServerResponse addList(Integer productId, Integer count, Integer id);
    //购物车List列表
    ServerResponse listCart(Integer id);

    ServerResponse updateCart(Integer productId, Integer count, Integer id);
    //删除购物车
    ServerResponse deleteProduct(String productIds, Integer id);
    //返回购物车数量
    ServerResponse getCartProductCount(Integer id);
    //购物车全选
    ServerResponse selectAll(Integer id, Integer checked, String listPid);
}
