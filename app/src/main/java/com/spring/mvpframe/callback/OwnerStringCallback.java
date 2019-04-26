package com.spring.mvpframe.callback;

import android.content.Context;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.base.Request;
import com.spring.mvpframe.net.HttpRequestCode;
import com.spring.mvpframe.utils.ToastUtils;
import org.json.JSONObject;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import okhttp3.Response;

public abstract class OwnerStringCallback extends AbsCallback<String> {
    private Context activity;
    private StringConvert convert;

    private void initDialog() {
    }

    /**
     *
     * @param activity
     * @param isShowLoading(可在此自定义是否显示progressDialog)
     */
    public OwnerStringCallback(Context activity, boolean isShowLoading) {
        this.activity = activity;
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
    }

    @Override
    public String convertResponse(Response response) throws Throwable {
        if (response == null) {
            throw new IllegalStateException("返回为空!");
        }
        convert = new StringConvert();
        String s = convert.convertResponse(response);
        JSONObject jsonObject = new JSONObject(s);
        int code = jsonObject.getInt("code");
        String msg = jsonObject.getString("message");

        response.close();

        if (code == HttpRequestCode.SUCCESS) {
            return s;
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
        } else if (code == HttpRequestCode.UPDATE_PASSWORD_ERROR) {
            return s;
        }else {
            throw new IllegalStateException("错误信息：" + msg);
        }
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<String> response) {
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
