package com.itdr.mappers;

import com.itdr.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    //添加一个商品到购物车
    Cart selectByProductIdAndUserId(@Param("productId") Integer productId, @Param("userId") Integer userId);
    //查询用户所有的购物车
    List<Cart> selectByUid(@Param("uid") Integer uid);
    //.更新购物车某个产品数量
    int selectByUidCheck(@Param("uid") Integer uid, @Param("uncheck") Integer uncheck);

    int deleteByProducts(@Param("productIds") List<String> productIds, @Param("id") Integer id);

    //购物车全选,可能需要ID,全选状态码1或着0,还有listpid
    int updateByAllOrPid(@Param("id") Integer id, @Param("checked") Integer checked, @Param("listpid") List<String> listpid);

    //查询选中的商品
    List<Cart> selectByUidAll(@Param("uid") Integer uid);

    //删除完成订单的购物车
    int deleteAllByAndUid(@Param("li") List<Cart> li, @Param("id") Integer id);
}