package com.itdr.controllers.backend;

import com.itdr.common.ServerResponse;
import com.itdr.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
    @RequestMapping("/manage/category/")
@ResponseBody
public class CategoryManageController {

    @Autowired
    CategoryService categoryService;
    //根据分类ID查询所有子类（包括本身）
    @RequestMapping("get_deep_category.do")
    public ServerResponse getDeepCategory(Integer categoryId){
        ServerResponse sr = categoryService.getDeepCategory(categoryId);
        return sr;
    }

    //根据分类ID查询平级分类
    @RequestMapping("get_category.do")
    public ServerResponse getCategory(Integer categoryId){
        ServerResponse sr = categoryService.getCategory(categoryId);
        return sr;
    }

}
