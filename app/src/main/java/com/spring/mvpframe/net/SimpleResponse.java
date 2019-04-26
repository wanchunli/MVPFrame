package com.spring.mvpframe.net;

import java.io.Serializable;

//***************************************/
//包名:com.anso.common_library.net
//创建人: Mr.Fang 
//创建时间:2018/9/20 16:28
//联系方式:itbofang@gmail.com
//功能描述:TODO
//**************************************/
public class SimpleResponse implements Serializable {
    private static final long serialVersionUID = -1477609349345966116L;

    public int code;
    public String message;

    public BaseResponse toBaseResponse() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.code = code;
        baseResponse.message = message;
        return baseResponse;
    }
}
