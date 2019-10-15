package com.itdr.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//统一返回格式的类========================================
@Getter
@Setter
//如果返回值是空的话就不返回值的注解
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T>  implements Serializable {
    private Integer status;
    private T data;
    private String msg;

    //获取成功的对象，只包括成功数据
    private ServerResponse(T data){
        this.data=data;
    }
    //获取成功的对象，包括成功状态码和数据
    private ServerResponse(Integer status,T data){
    this.data=data;
    this.status=status;
    }
    //获取成功的对象，包括成功状态码和数据和状态信息
    private ServerResponse(Integer status,T data,String msg){
        this.data=data;
        this.status=status;
        this.msg=msg;
    }
    //获取失败的对象，包括失败的状态码和失败的信息
    private ServerResponse(Integer status,String msg){
        this.msg=msg;
        this.status=status;
    }
    //获取失败的对象，只包括失败的信息
    private ServerResponse(String msg){
        this.msg=msg;
    }

    public static <T> ServerResponse successRs(){
        return new ServerResponse(Const.SUCESS);
    }
    public static <T> ServerResponse successRs(String msg){
        return new ServerResponse(Const.SUCESS,msg);
    }
    //成功时传入数据
    public static <T> ServerResponse successRs(T data){
        return new ServerResponse(Const.SUCESS,data);
    }
    //成功时传入状态码和数据和信息
    public static <T> ServerResponse successRs(T data, String msg){
        return new ServerResponse(Const.SUCESS,data,msg);
    }
    //成功只传入数据
    public static <T> ServerResponse defeatedRs(T data){
        return new ServerResponse(data);
    }
    //失败时传入状态码和错误信息
    public static <T> ServerResponse defeatedRs(Integer status , String mag){
        return new ServerResponse(status,mag);
    }
    //失败时传入错误信息
    public static <T> ServerResponse defeatedRs(String mag){
        return new ServerResponse(mag);
    }

    //判断是否是成功的方法
    @JsonIgnore//处理返回的问题
    public boolean isSuccess(){
        return this.status == Const.SUCESS;
    }
}
