package com.spring.mvpframe.net;

import com.spring.mvpframe.bean.BaseBean;

import java.io.Serializable;

//*******************************************************
//* 项目名称：as-outwork-platform
//* 创建者： Mr.Fang
//* 创建日期： 2018/6/4 16:37
//* Email：itfang@126.com
//* 描述：统一返回结果处理
//*******************************************************
public class BaseResponse<T extends BaseBean>  implements Serializable {
    public  int code;
    public String message;
    public  T data;

}
