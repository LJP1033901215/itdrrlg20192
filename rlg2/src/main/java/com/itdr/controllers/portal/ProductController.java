package com.itdr.controllers.portal;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Product;
import com.itdr.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
@RequestMapping("/portal/product/")
public class ProductController {

    @Autowired
    ProductService productService;

    //.获取产品子分类
    @RequestMapping("topcategory.do")
    public ServerResponse toPcategory(Integer sid){
        ServerResponse<Product> sr = productService.toPcategory(sid);
        return sr;
    }

    //产品详情
    //通过RequestMapping注解定位地址
    @RequestMapping("detail.do")
    //创建方法
    public ServerResponse detail(Integer productId,
                                 //@RequestParam设置默认值，设置默认值
                                 @RequestParam(value = "is_new",required =false,defaultValue = "0") Integer is_new,
                                 @RequestParam(value = "is_hot",required =false,defaultValue = "0")  Integer is_hot,
                                 @RequestParam(value = "is_banner",required =false,defaultValue = "0") Integer is_banner){
        //调用业务层的方法，返回ServerResponse
       ServerResponse sr =  productService.detail(productId,is_new,is_hot,is_banner);
        return sr;
    }
    @RequestMapping("detailNewOrHetOrBanner.do")
    //获取最新最热商品
    public ServerResponse detailNewOrHetOrBanner(
                                 //@RequestParam设置默认值，设置默认值
                                 @RequestParam(value = "is_new",required =false,defaultValue = "0") Integer is_new,
                                 @RequestParam(value = "is_hot",required =false,defaultValue = "0")  Integer is_hot,
                                 @RequestParam(value = "is_banner",required =false,defaultValue = "0") Integer is_banner){
        //调用业务层的方法，返回ServerResponse
        ServerResponse sr =  productService.detailNewOrHetOrBanner(is_new,is_hot,is_banner);
        return sr;
    }

    //产品搜索及动态排序List
    @RequestMapping("list.do")
    public ServerResponse selectIdOrName(Integer productId, String keyword,
                                        @RequestParam(value = "pageNum",required =false,defaultValue = "1")Integer pageNum,
                                         @RequestParam(value = "pageSize",required =false,defaultValue = "10")Integer pageSize,
                                         @RequestParam(value = "orderBy",required = false,defaultValue = "price_desc") String orderBy){
        ServerResponse sr = productService.selectIdOrName(productId,keyword,pageNum,pageSize,orderBy);
        return sr;
    }


}
