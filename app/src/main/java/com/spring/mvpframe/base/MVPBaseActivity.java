package com.spring.mvpframe.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spring.mvpframe.R;

/**
 * 所有Activity的基类
 * MVPBaseActivity
 * author wanchun
 * email 1596900283@qq.com
 * create 2018/3/5 14:17
 */
public abstract class MVPBaseActivity<V, T extends BasePresenter<V>>
        extends AppCompatActivity implements View.OnClickListener {

    protected T mPresenter;

    protected abstract T createPresenter();

    abstract protected int provideContentViewId();//用于引入布局文件

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //允许为空，不是所有都要实现MVP模式
        if (createPresenter() != null) {
            mPresenter = createPresenter();
            mPresenter.attachView((V) this);
        }
        setContentView(provideContentViewId());//布局
        init();
        initViews();
        initListener();
    }

    public void init() {
    }

    public void initViews() {
    }

    public void initListener() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

}
