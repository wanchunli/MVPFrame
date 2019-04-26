package com.spring.mvpframe.demo;
import com.spring.mvpframe.base.BaseView;

public interface MainView extends BaseView<MainPresenter> {

    void getDataCallback(String data);

    void showDialog(String msg);//显示进度对话框

    void showTipToast(String message);//显示Toast

    void cancleDialog();//关闭进度对话框

}
