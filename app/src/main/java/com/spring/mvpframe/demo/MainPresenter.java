package com.spring.mvpframe.demo;
import android.app.Activity;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.spring.mvpframe.base.BasePresenter;
import com.spring.mvpframe.bean.DataBean;
import com.spring.mvpframe.callback.JsonCallback;
import com.spring.mvpframe.callback.OwnerStringCallback;
import com.spring.mvpframe.net.BaseResponse;

import org.json.JSONObject;

public class MainPresenter extends BasePresenter<MainView> {
    private Context context;
    private MainView mainView;

    public MainPresenter(Activity context) {
        this.context = context;
    }

    public void getData(String param){
        mainView = getView();
        if (mainView != null) {
            //由于对获得的数据格式要求不同，特意给出了两种请求回调，按照具体需求选择使用
            //接口数据自定义直接返回泛型
            OkGo.<BaseResponse<DataBean>>get("请求的接口地址")
                    .tag(this)
                    .params("param", param)
                    .execute(new JsonCallback<BaseResponse<DataBean>>(context,true) {
                        @Override
                        public void onSuccess(Response<BaseResponse<DataBean>> response) {
                            //此处可直接得到传入的泛型
                            DataBean dataBean = response.body().data;
                        }
                    });
            //直接得到接口json数据
            OkGo.<String>get("请求的接口地址")
                    .tag(this)
                    .params("param", param)
                    .execute(new OwnerStringCallback(context, true) {
                        @Override
                        public void onSuccess(Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

}
