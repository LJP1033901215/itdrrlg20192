package com.itdr.mappers;

import com.itdr.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//控制层
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Product selectByCategoryId(@Param("id") Integer id);
    //.获取产品子分类
    List<Product> selectByListCategoryId(@Param("categoryId") Integer categoryId);
    //.获取产品子分类
        Product selectByProductId(@Param("productId") Integer productId,
                                  @Param("is_new") Integer is_new,
                                  @Param("is_hot") Integer is_hot,
                                  @Param("is_banner") Integer is_banner);
    //产品搜索及动态排序List
    List<Product> selectByIdOrNamen(@Param("productId") Integer productId,
                                    @Param("keyword") String keyword,
                                    @Param("ord") String ord,
                                    @Param("by") String by);

    //根据ID查询商品详情
    Product selectByProductIdAll(@Param("productId") Integer productId);
    //根据对象更改商品
    int updateById(@Param("stock") Integer stock, @Param("id") Integer id);
    //返回最热视频
    List<Product> selectNewOrHetOrBanner(@Param("is_new") Integer is_new,
                                         @Param("is_hot") Integer is_hot,
                                         @Param("is_banner") Integer is_banner);
}