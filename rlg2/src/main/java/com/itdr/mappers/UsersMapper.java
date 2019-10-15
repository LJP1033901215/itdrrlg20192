package com.itdr.mappers;

import com.itdr.pojo.Users;
import org.apache.ibatis.annotations.Param;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);
    //新增一个
    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
    //根据密码和账户验证账户是否存在
    Users selectUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    //根据用户名或者邮箱查询用户
    int selectUserNameOrEmail(@Param("str") String str, @Param("type") String type);
    //根据邮箱查询非自己之外的其他邮箱
    int selectByEmailandAndId(@Param("email") String email, @Param("id") Integer id);
    //通过用户名查询密保问题
    String selectByUserNname(@Param("username") String username);
    //提交密保问题答案
    int selectByUserNameAndQuestionAndAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);
    //忘记密码重设密码
    int updateByUserNameAndPassword(@Param("username") String username, @Param("passwordNew") String passwordNew);
    //登录状态下根据ID和就密码查询
    int selectByAndPassword(@Param("id") Integer id, @Param("passwordOld") String passwordOld);

}