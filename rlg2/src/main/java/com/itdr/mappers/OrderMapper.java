package com.itdr.mappers;

import com.itdr.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);
    //插入一条新的数据
    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);
//根据订单状态更改状态码+更改时间
    int updateByPrimaryKey(Order record);
    //根据订单号查询订单
    Order selectByorderNo(Long orderNo);
    //根据用户ID查询订单
    List<Order> selectByUid(@Param("uid") Integer uid);
    /*根据ID和orderNo查询订单*/
    Order selectByUidAndOrderNo(@Param("uid") Integer uid, @Param("orderNo") Long orderNo);
    /*根据ID和orderNo删除*/
    int deleteByUidAndOrderNo(@Param("id") Integer id, @Param("orderNo") Long orderNo);
    //做到了根据用户ID查询详情。。。
}