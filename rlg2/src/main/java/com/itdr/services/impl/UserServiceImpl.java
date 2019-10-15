package com.itdr.services.impl;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.common.TokenCache;
import com.itdr.mappers.UsersMapper;
import com.itdr.pojo.Users;
import com.itdr.services.UserService;
import com.itdr.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.UUID;

//用户业务层
@Service("userService")
//添加事务注解
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    UsersMapper usersMapper;

    //业务层用户登录=============================================================================
    @Override
    public ServerResponse<Users> login(String username, String password, HttpSession session) {
        //非空判断===========================================================================================
        //缺少一个查询用户名不存在的非空判断
        if (username==null||username.equals("")){
             return ServerResponse.defeatedRs("用户名不能为空");
        }
        if (password==null||password.equals("")){
            return ServerResponse.defeatedRs("密码不能为空");
        }
        //判断用户名是不是存在
        int i = usersMapper.selectUserNameOrEmail(username, "username");
        if(i<=0){
            return ServerResponse.defeatedRs("用户名不存在");
        }
        //使用MD5加密
        String mdSPassword = MD5Utils.getMD5Code(password);

        //根据用户和密码查询用户是否存在
        Users users= usersMapper.selectUsernameAndPassword(username,mdSPassword);
        if (users==null){
            return ServerResponse.defeatedRs("账户或密码不正确");
        }
        //将查询出来的users对象放入Session中
        session.setAttribute(Const.LOGINUSER,users);

        //封装数据并返回
        ServerResponse sr =ServerResponse.successRs(users);
        Users users2 = new Users();
        if (sr.isSuccess()){
            users2.setId(users.getId());
            users2.setEmail(users.getEmail());
            users2.setPhone(users.getPhone());
            users2.setCreateTime(users.getCreateTime());
            users2.setUpdateTime(users.getUpdateTime());
            users2.setUsername(users.getUsername());
            users2.setPassword("");
        }
        sr = ServerResponse.successRs(users2);
        return sr;
    }
    //业务层用户注册=================================================================================================
    @Override
    public ServerResponse<Users> register(Users users) {
        if (users.getUsername() ==null||users.getUsername().equals("")){
            return  ServerResponse.defeatedRs("用户名不能为空");
        }
        if (users.getPassword() ==null||users.getPassword().equals("")){
            return  ServerResponse.defeatedRs("密码不能为空");
        }
        if (users.getEmail() ==null||users.getEmail().equals("")){
            return  ServerResponse.defeatedRs("Eeail不可以为空");
        }
//        if (users.getPhone() ==null||users.getPhone().equals("")){
//            return  ServerResponse.defeatedRs("电话不可以为空");
//        }
        if (users.getQuestion() ==null||users.getQuestion().equals("")){
            return  ServerResponse.defeatedRs("密保问题不可以为空");
        }
        if (users.getAnswer() ==null||users.getAnswer().equals("")){
            return  ServerResponse.defeatedRs("密保答案不可以为空");
        }


        //判断用户名是不是存在
        int i = usersMapper.selectUserNameOrEmail(users.getUsername(), "username");
        if(i>0){
            return ServerResponse.defeatedRs("要注册的用户已经存在");
        }
        //缺少检查邮箱是不是存在
        int i2 = usersMapper.selectUserNameOrEmail(users.getEmail(), "email");
        if(i2>0){
            return ServerResponse.defeatedRs("要注册的邮箱已经存在");
        }

        //使用MD5工具加密==
        users.setPassword(MD5Utils.getMD5Code(users.getPassword()));

        //传入参数
        int insert = usersMapper.insert(users);
        if (insert<=0){
        return ServerResponse.defeatedRs("注册失败");
        }
        return ServerResponse.successRs("用户注册成功");
    }
    //业务层检查用户名或者邮箱是否存在=========================================================
    @Override
    public ServerResponse<Users> checkUsername(String str, String type) {
        if (str==null||str.equals("")){
            if (type==null||type.equals("")){
                return ServerResponse.defeatedRs("参数类型或者参数不能为空");
            }
        }

        int i  = usersMapper.selectUserNameOrEmail(str,type);
        if (i>0 && type.equals("username")){
            return ServerResponse.defeatedRs("用户已经存在");
        }
        if (i>0 && type.equals("email")){
            return ServerResponse.defeatedRs("邮箱已经存在");
        }

        return ServerResponse.successRs("效验成功");
    }
    //获取当前登录对象的详细对象=====================================================================
    @Override
    public ServerResponse<Users> getInforamtion(Users users) {
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NO_LOGIN.getCode(),Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        Users users1 = usersMapper.selectByPrimaryKey(users.getId());
        if (users1 ==null){
            return ServerResponse.defeatedRs("用户不存在");
        }
        users1.setPassword("");
        return ServerResponse.successRs(users1);
    }
    //登录状态下更改用户的信息=========================================================================
    @Override
    public ServerResponse<Users> updateInformation(Users u) {
        //判断除了自己之外其他人的邮箱是否存在
        int i2 = usersMapper.selectByEmailandAndId(u.getEmail(),u.getId());
        if (i2 > 0){
            return ServerResponse.successRs("邮箱已经存在");
        }
        int i = usersMapper.updateByPrimaryKeySelective(u);
        if (i <= 0){
            return ServerResponse.successRs("更新失败");
        }
        return ServerResponse.successRs("更新成功");
    }
    //非登录状态下忘记密码，输入用户名，返回密保问题=========================================
    @Override
    public ServerResponse<Users> forgetGetQuestion(String username) {
        if (username==null || username.equals("")){
            return ServerResponse.defeatedRs("参数不能为空");
        }
        int i = usersMapper.selectUserNameOrEmail(username, Const.USERNAME);
        if (i<=0){
            return ServerResponse.defeatedRs("用户名不存在");
        }
        //返回密保问题。
        String question = usersMapper.selectByUserNname(username);
        if (question==null || "".equals(question)){
            return  ServerResponse.defeatedRs("用户未设置密保问题");
        }
        ServerResponse sr = ServerResponse.successRs(question);
        return sr;
    }
    //非登录状态下，提交密保问题答案=====================================================================
    @Override
    public ServerResponse<Users> forgetCheckAnswer(String username, String question, String answer) {
        //参数为空
        if (username==null || username.equals("")){
            return ServerResponse.defeatedRs("用户名不能为空");
        }
        if (question==null || question.equals("")){
            return ServerResponse.defeatedRs("密保问题不能为空");
        }
        if (answer==null || answer.equals("")){
            return ServerResponse.defeatedRs("密保答案不能为空");
        }
        //判断用户是否存在
        int i2 = usersMapper.selectUserNameOrEmail(username,"username");
        if (i2<=0){
            return ServerResponse.defeatedRs("用户不存在");
        }
        //判断问题是否匹配
        int i = usersMapper.selectByUserNameAndQuestionAndAnswer(username,question,answer);
        if (i<=0){
            return ServerResponse.defeatedRs("问题答案不匹配");
        }
        //产生随机字符令牌
        String token = UUID.randomUUID().toString();

        //将令牌放入缓存中，这里使用的goole的guava缓存
        TokenCache.set("token_"+username,token);

        //返回令牌
        return ServerResponse.successRs(token);
    }
    //非登录状态下忘记密码重设密码==============================================================================
    @Override
    public ServerResponse<Users> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        //参数为空
        if (username==null || username.equals("")){
            return ServerResponse.defeatedRs("用户名不能为空");
        }
        if (passwordNew==null || passwordNew.equals("")){
            return ServerResponse.defeatedRs("新密码不能为空");
        }
        if (forgetToken==null || forgetToken.equals("")){
            return ServerResponse.defeatedRs("非法的令牌参数");
        }

        //使用DM5加密
        String mdSPassword = MD5Utils.getMD5Code(passwordNew);

        //判断缓存中的token
        String token = TokenCache.get("token_" + username);
        if (token==null||token.equals("")){
            return ServerResponse.defeatedRs("token过期了");
        }
        if (!token.equals(forgetToken)){
            return ServerResponse.defeatedRs("token是非法的");
        }
        int i =  usersMapper.updateByUserNameAndPassword(username,mdSPassword);
        if (i<=0){
            return ServerResponse.defeatedRs("修改密码操作失败");
        }
        return ServerResponse.successRs("修改密码成功");
    }


    //登录中状态重置密码==================================================================================
    @Override
    public ServerResponse<Users> reset_password(Users users, String passwordOld, String passwordNew) {
        if (passwordOld==null || passwordOld.equals("")){
            return ServerResponse.defeatedRs("旧密码不能为空");
        }
        if (passwordNew==null || passwordNew.equals("")){
            return ServerResponse.defeatedRs("新密码不能为空");
        }
        //DM5加密
        String mdSPassword = MD5Utils.getMD5Code(passwordOld);
        int i = usersMapper.selectByAndPassword(users.getId(),mdSPassword);
        if (i<=0){
            return ServerResponse.defeatedRs("旧密码输入错误");
        }
        //DM5加密
        String mdSPassword2 = MD5Utils.getMD5Code(passwordNew);
        int i1 = usersMapper.updateByUserNameAndPassword(users.getUsername(), mdSPassword2);
        if (i1<=0){
            return ServerResponse.defeatedRs("修改密码失败");
        }
        return ServerResponse.defeatedRs("修改密码成功");
    }

}
