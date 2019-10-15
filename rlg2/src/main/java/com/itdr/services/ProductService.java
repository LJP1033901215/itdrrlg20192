package com.itdr.services;

import com.itdr.common.ServerResponse;

public interface ProductService {
    //.获取产品子分类
    ServerResponse toPcategory(Integer sid);

    ServerResponse detail(Integer productId, Integer is_new, Integer is_hot, Integer is_banner);

    ServerResponse selectIdOrName(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy);

    ServerResponse detailNewOrHetOrBanner(Integer is_new, Integer is_hot, Integer is_banner);

}
