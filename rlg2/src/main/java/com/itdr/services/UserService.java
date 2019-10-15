package com.itdr.services;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;

import javax.servlet.http.HttpSession;


public interface UserService {
    ServerResponse<Users> login(String username, String password, HttpSession session);

    ServerResponse<Users> register(Users users);

    ServerResponse<Users> checkUsername(String str, String type);

    ServerResponse<Users> getInforamtion(Users users);

    ServerResponse<Users> updateInformation(Users u);

    ServerResponse<Users> forgetGetQuestion(String username);

    ServerResponse<Users> forgetCheckAnswer(String username, String question, String answer);

    ServerResponse<Users> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<Users> reset_password(Users users, String passwordOld, String passwordNew);
}



