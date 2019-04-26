package com.spring.mvpframe;

import android.app.Application;
import com.spring.mvpframe.utils.ToastUtils;

/**
 * 长沙大表巡检标
 */
public class AppContext extends Application {

    private static AppContext sInstance;
    public static AppContext getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        ToastUtils.init(this);
    }

    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();
    }

}
