package com.singoriginal.impl;

import com.singoriginal.view.CustomScrollView;

/**
 * 用于实现ScrollView滚动事件监听
 * Created by lanouhn on 16/7/26.
 */
public interface ScrollViewListener
{
    void onSrollChanged(CustomScrollView scrollView, int x, int y, int oldx, int oldy);
}
