package com.itdr.services;

import com.itdr.common.ServerResponse;

//递归查找分类
public interface CategoryService {
    //根据分类ID查询所有子类（包括本身）
    ServerResponse getDeepCategory(Integer categoryId);
    //查询平级ID子类
    ServerResponse getCategory(Integer categoryId);
}
