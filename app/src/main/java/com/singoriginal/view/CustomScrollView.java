package com.singoriginal.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.singoriginal.impl.ScrollViewListener;

/**
 * 自定义ScrollView 可以监听滚动事件
 * Created by lanouhn on 16/7/26.
 */
public class CustomScrollView extends ScrollView
{
    private ScrollViewListener scrollViewListener = null;

    public CustomScrollView(Context context)
    {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置监听
     * @param scrollViewListener
     */
    public void setScrollViewListener(ScrollViewListener scrollViewListener)
    {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null)
        {
            scrollViewListener.onSrollChanged(this, l, t, oldl, oldt);
        }
    }
}
