package com.spring.mvpframe.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *基础的Fragment
 *MVPBaseFragment
 *author wanchun
 *email 1596900283@qq.com
 *create 2018/3/5 14:18
 */
public abstract class MVPBaseFragment<V, T extends BasePresenter<V>> extends Fragment implements View.OnClickListener {

    protected T mPresenter;

    protected abstract T createPresenter();

    protected abstract int createViewLayoutId();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (createPresenter() != null) {
            mPresenter = createPresenter();
            mPresenter.attachView((V) this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(createViewLayoutId(), container, false);
        initView(rootView);
        return rootView;
    }

    protected void initView(View rootView) {
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null) {
            mPresenter.detachView();
        }
    }


}

