package com.itdr.services;

import com.itdr.common.ServerResponse;

public interface OreateService {
    //创建订单
    ServerResponse createOrder(Integer id, Integer shippingId);

    ServerResponse getOrderCartProduct(Integer id, Long orderNo);

    ServerResponse getList(Integer id, Integer pageSize, Integer pageNum);

    ServerResponse countermandOrder(Integer id, Long orderNo);

    ServerResponse deleteOrder(Integer id, Long orderNo);

}
