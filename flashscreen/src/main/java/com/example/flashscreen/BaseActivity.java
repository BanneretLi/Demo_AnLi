package com.example.flashscreen;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2319:41<p>
 * <p>更改时间：2019/1/2319:41<p>
 * <p>版本号：1<p>
 */
public abstract class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected abstract int getLayout();

    protected abstract void initViews();

    protected abstract void setOnclick();

    protected abstract void logic();

    protected abstract void release();


    void init() {
        if (getLayout() != 0) {
            setContentView(getLayout());
            initViews();
            setOnclick();
            logic();
        }
    }

    public  void fullScreen(Activity activity, boolean blackStatusBarText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                //在6.0增加了View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR，这个字段就是把状态栏标记为浅色，然后状态栏的字体颜色自动转换为深色
                window.setStatusBarColor(Color.TRANSPARENT);
                if (blackStatusBarText) {
                    option = option | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    window.setStatusBarColor(Color.GRAY);
                }
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //导航栏颜色也可以正常设置
                //window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
                // attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }
}
