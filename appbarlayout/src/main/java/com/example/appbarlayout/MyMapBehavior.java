package com.example.appbarlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

public class MyMapBehavior extends CoordinatorLayout.Behavior<View> {
    private float deltaY;

    public MyMapBehavior() {
    }

    public MyMapBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
     * 确定使用Behavior的View要依赖的View的类型
     * parant     代表CoordinatorLayout，
     * child      代表使用该Behavior的View，观察者
     * dependency 代表要监听的View，被观察者
     * */
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof NestedScrollView;
    }

    //当被依赖的View状态改变时回调
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        int offset = dependency.getTop() - child.getTop();
        ViewCompat.offsetTopAndBottom(child, offset);
        return true;
    }
}
