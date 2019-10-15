package com.itdr.common;

//统一创建返回状态码和状态信息的类
public class Const {
    public static final String LOGINUSER="login_user";
    public static final String TRADE_SUCCESS ="TRADE_SUCCESS";
    public static final String AUTOLOGINTOKEN = "autoLoginToken";
    public static final String JESSIONID_COOKIE = "JESSIONID_COOKIE";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    //收获地址的数量上限
    public static final Integer SHIPPING_AMOUNT = 5;

    /*非空判断非法参数*/
    public static final String NOT_PARAMETER="非法参数";

    /*cart接口*/
    public interface Cart{
       String LIMITQUANTITYSUCCESS ="LIMIT_NUM_SUCCESS";
       String LIMITQUANTITYFAILED ="LIMIT_NUM_FALED";
       Integer CHECK = 1;
       Integer UNCHECK = 0 ;
    }

    /*支付模块使用的状态码*/
    public  enum PaymentPlatforEnum{
        ALIPAY(1,"支付宝"),
        ALIPAY_FALSE(301,"支付宝预下单失败"),
        VERIFY_SIGNATURE_FALSE(302,"支付宝验签失败"),
        REPEAT_USEALIPAY(304,"支付宝重复调用"),
        SAVEPAYMSG_FALSE(305,"支付信息保存失败");

        private int code;
        private String desc;
        private PaymentPlatforEnum(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    /*成功的状态码*/
    public static final int SUCESS = 200;

    /*失败的状态码*/
    public static final int ERROR = 100;

    /*支付类型*/
    public enum PaymentTypeEnum {
            ONLINE_PAYMENT(1,"在线支付");

        private int code;
        private String desc;

        private PaymentTypeEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    //使用枚举
    public enum UsersEnum{
        NEED_LOGIN(2,"需要登录"),
        NO_LOGIN(101,"用户未登录");

        private int code;
        private  String desc;
        private  UsersEnum(int code ,String desc){
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}

