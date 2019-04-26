/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.spring.mvpframe.callback;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.base.Request;
import com.spring.mvpframe.AppContext;
import com.spring.mvpframe.R;
import com.spring.mvpframe.net.BaseResponse;
import com.spring.mvpframe.net.HttpRequestCode;
import com.spring.mvpframe.net.SimpleResponse;
import com.spring.mvpframe.utils.ToastUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class JsonCallback<T> extends AbsCallback<T> {
    private Context activity;
    private Type type;
    private Class<T> clazz;
    private String mString;

    public JsonCallback(Type type) {
        this.type = type;
    }

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     *
     * @param activity
     * @param isShowLoading(可在此自定义是否显示progressDialog)
     */
    public JsonCallback(Context activity, boolean isShowLoading) {
        this.activity = activity;
    }


    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            throw new IllegalStateException("返回数据为空");
            // return null;
        }
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalStateException("没有填写泛型!");
        }
        Type rawType = ((ParameterizedType) type).getRawType();
        Type realType = ((ParameterizedType) type).getActualTypeArguments()[0];

        T data = null;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(response.body().charStream());
        int code = HttpRequestCode.SUCCESS;
        String msg = "";

        if (rawType != BaseResponse.class) {
            data = gson.fromJson(jsonReader, type);
            response.close();
            return data;
        } else {
            //传入的Baserespon<Void>
            if (realType == Void.class) {
                SimpleResponse simpleResponse = gson.fromJson(jsonReader, SimpleResponse.class);
                code = simpleResponse.code;
                msg = simpleResponse.message;
                response.close();
                if (code == HttpRequestCode.SUCCESS) {
                    return (T) simpleResponse.toBaseResponse();
                } else if (code == HttpRequestCode.ERROR_REQUEST) {//在onError中回调
                    throw new IllegalStateException(msg);
                }  else if (code == HttpRequestCode.FAILE) {
                    throw new IllegalStateException(msg);
                }else if (code == HttpRequestCode.UNAUTHORIZED_TOKEN) {
                    throw new IllegalStateException(msg);
                } else if (code == HttpRequestCode.NOT_ACCEPT) {
                    throw new IllegalStateException(msg);
                } else if (code == HttpRequestCode.INVALID_TOKEN) {
                    //token失效
                    throw new IllegalStateException(msg);
                } else if (code == HttpRequestCode.REQUIRED_TOKEN) {
                    throw new IllegalStateException(msg);
                } else if (code == HttpRequestCode.SERVER_ERROR) {
                    throw new IllegalStateException(msg);
                } else {
                    throw new IllegalStateException(msg);
                }
            } else {
                //有数据类型，表示有data
                BaseResponse commonResponseData = gson.fromJson(jsonReader, type);
                response.close();
                code = commonResponseData.code;
                msg = commonResponseData.message;
                //0 代表返回成功
                if (code == HttpRequestCode.SUCCESS) {
                    return (T) commonResponseData;
                } else if (code == HttpRequestCode.ERROR_REQUEST) {//在onError中回调
                    throw new IllegalStateException(msg);
                } else if (code == HttpRequestCode.UNAUTHORIZED_TOKEN) {
                    throw new IllegalStateException(msg);
                } else if (code == HttpRequestCode.NOT_ACCEPT) {
                    throw new IllegalStateException(msg);
                } else if (code == HttpRequestCode.INVALID_TOKEN) {
                    //token失效
                    throw new IllegalStateException(msg);
                } else if (code == HttpRequestCode.REQUIRED_TOKEN) {
                    throw new IllegalStateException(msg);
                } else if (code == HttpRequestCode.SERVER_ERROR) {
                    throw new IllegalStateException(msg);
                } else {
                    throw new IllegalStateException(msg);
                }
            }
        }
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        Throwable e = response.getException();
        if (e instanceof UnknownHostException || e instanceof ConnectException) {
            ToastUtils.getInstance().show("网络连接失败，请连接网络....");
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.getInstance().show("网络请求超时");
        } else if (e instanceof HttpRetryException) {
            ToastUtils.getInstance().show("服务器拒绝请求");
        } else if (e instanceof SocketException) {
            ToastUtils.getInstance().show("网络请求取消！");
        } else if (e instanceof IllegalStateException) {
            //此处为自定义的异常
            ToastUtils.getInstance().show(e.getMessage());
        } else {
            ToastUtils.getInstance().show("网络错误！" + e);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}
