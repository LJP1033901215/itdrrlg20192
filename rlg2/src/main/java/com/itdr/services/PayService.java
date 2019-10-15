package com.itdr.services;

import com.itdr.common.ServerResponse;

import java.util.Map;

public interface PayService {
    ServerResponse aliPay(Long orderNo, Integer uid);

    ServerResponse alipayCaliback(Map<String, String> newMao);

}
