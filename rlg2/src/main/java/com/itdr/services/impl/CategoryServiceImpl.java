package com.itdr.services.impl;

import com.itdr.common.ServerResponse;
import com.itdr.mappers.CategoryMapper;
import com.itdr.mappers.ProductMapper;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    //自己更改====================================
    @Autowired
    ProductMapper productMapper;
    //============================================
    //根据分类ID查询所有子类（包括本身）
    @Override
    public ServerResponse getDeepCategory(Integer categoryId) {
        if (categoryId ==null){
            return ServerResponse.defeatedRs("输入的参数不可为空");
        }
        //创建ArrayList集合存放数据
        List<Integer> li = new ArrayList<>();
        //自己更改====================================
        List<Product> pi = new ArrayList<>();
        //============================================

        //调用递归的方法

        getAll(categoryId,li);
        //自己更改=================================================
        for (Integer p:li) {
            System.out.println(p);
            Product product =productMapper.selectByCategoryId(p);
            pi.add(product);
        }
        //==========================================================
        //使用统一返回的对象
        ServerResponse rs = ServerResponse.successRs(pi);
        return rs;
    }


    //查询平级ID子类
    @Override
    public ServerResponse getCategory(Integer categoryId) {
        if (categoryId==null){
            return ServerResponse.defeatedRs("参数不可为空");
        }
        List<Product> product = productMapper.selectByListCategoryId(categoryId);

        return ServerResponse.successRs(product);
    }

    //创建递归的方法
    private void getAll(Integer pid, List<Integer> list){
        //创建出集合接收根据ID查询出来的数据
        List<Category> li = categoryMapper.selectByParentId(pid);
        //判断集合中数据是否为零
        if (li != null && li.size() !=0){
            //进行循环，将li中的的数据进行循环
            for (Category categorys :li){
                //将循环得到的ID放入到集合中
                list.add(categorys.getId());
                //继续调用方法，将查询出来的ID继续传入进去，继续查询数据
                getAll(categorys.getId(),list);
            }
        }
    }
}
