package com.itdr.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.ProductMapper;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.ProductVO;
import com.itdr.services.ProductService;
import com.itdr.utils.PoToVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    //获取产品子分类
    @Override
    public ServerResponse toPcategory(Integer sid) {
        if (sid ==null){
            return ServerResponse.defeatedRs("非法参数");
        }
        List<Product> li = productMapper.selectByListCategoryId(sid);
        if (li==null){
            return ServerResponse.defeatedRs("查询错误");
        }
        if (li.size()==0){
            return ServerResponse.defeatedRs("该ID无分类");
        }
        return ServerResponse.successRs(li);
    }

    //.返回商品信息
    @Override
    public ServerResponse detail(Integer productId, Integer is_new, Integer is_hot, Integer is_banner) {
        //判断参数是不是为空
        if (productId == null||productId < 0){//为什么判断是不是空，一定要放前面？因为如果不放前面会出现空指针异常
            return ServerResponse.defeatedRs("参数无效");
        }
        //通过方法调用数据层的方法
         Product  p = productMapper.selectByProductId(productId,is_new,is_hot,is_banner);
        //判断是否查询到信息，如果没有，则返回商品不存在
        if (p==null){
            return ServerResponse.defeatedRs("商品不存在");
        }
        //通过设置工具类对Product进行封装，并将封装的Product类放进去
        ProductVO pvo = PoToVoUtil.ProductToProductVO(p);
        //返回封装好的类
        return ServerResponse.successRs(pvo);
    }
    //产品搜索及动态排序List
    @Override
    public ServerResponse selectIdOrName(Integer productId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        if ((productId==null||productId <0) &&(keyword==null||keyword.equals(""))){
            return ServerResponse.defeatedRs("非法的参数");
        }
        //分割传入的参数
        String[] split = new String[2];
        if (!orderBy.equals("")) {
             split = orderBy.split("_");
        }

        String word = "%"+keyword+"%";

        PageHelper.startPage(pageNum,pageSize,split[0]+"  "+split[1]);
        List<Product> li  = productMapper.selectByIdOrNamen(productId, word,split[0],split[1]);
        PageInfo pf = new PageInfo(li);


        return ServerResponse.successRs(pf);
    }
    //获取最热商品，
    @Override
    public ServerResponse detailNewOrHetOrBanner(Integer is_new, Integer is_hot, Integer is_banner) {
        List<Product> li = productMapper.selectNewOrHetOrBanner(is_new,is_hot,is_banner);
        List<ProductVO> voList = new ArrayList<>();
        for (Product product : li) {
            ProductVO pv = PoToVoUtil.ProductToProductVO(product);
            voList.add(pv);
        }
        return ServerResponse.successRs(voList);
    }
}
