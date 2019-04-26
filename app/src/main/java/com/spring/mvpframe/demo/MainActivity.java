package com.spring.mvpframe.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.spring.mvpframe.R;
import com.spring.mvpframe.base.MVPBaseActivity;

public class MainActivity extends MVPBaseActivity<MainView, MainPresenter> implements MainView {

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(MainActivity.this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        //请求数据->在getDataCallback中获得数据
        mPresenter.getData("param");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getDataCallback(String data) {
        //getDataCallback中获得数据
        Log.i("data", data);
    }

    @Override
    public void showDialog(String msg) {

    }

    @Override
    public void showTipToast(String message) {

    }

    @Override
    public void cancleDialog() {

    }

}
