package com.itdr.mappers;

import com.itdr.pojo.Order_item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Order_itemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order_item record);

    int insertSelective(Order_item record);

    Order_item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order_item record);

    int updateByPrimaryKey(Order_item record);
    //传入订单详情
    int insertAll(@Param("oredrItem") List<Order_item> oredrItem);
    //根据ID和订单编号查询数据详情
    List<Order_item> selectByUidAndOrderNo(@Param("id") Integer id, @Param("orderNo") Long orderNo);
    /*根据orederNo删除订单详情*/
    int deleteByOrderNo(@Param("orderNo") Long orderNo);
}