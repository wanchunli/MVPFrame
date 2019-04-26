package com.spring.mvpframe.net;

public interface HttpRequestCode {
    public static final int LOGIN_REQUEST = 99;//请求成功
    public static final int SUCCESS = 0;//请求成功
    public static final int FAILE = -1;//请求失败
    public static final int ERROR_REQUEST = 400;//错误请求
    public static final int UNAUTHORIZED_TOKEN = 401;//未授权
    public static final int NOT_ACCEPT = 406;//不接受
    public static final int INVALID_TOKEN = 408;//TOKEN过期，登录过期
    public static final int REQUIRED_TOKEN = 499;//TOKEN为空
    public static final int SERVER_ERROR = 500;//服务器异常
    public static final int SERVER_LOGIN_ERROR = 10000;//用户名或密码错误
    public static final int UPDATE_PASSWORD_ERROR = 20041;//新密码不能与旧密码一致

}
