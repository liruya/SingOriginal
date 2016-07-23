package com.singoriginal.util;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * 富文本工具类 用于同一个组件显示不同颜色,大小的文本
 * Created by lanouhn on 16/7/23.
 */
public class RtfUtil
{
    SpannableStringBuilder style = new SpannableStringBuilder();

    public static SpannableStringBuilder getRtf(SpannableStringBuilder style, String add, int color, int size)
    {
        int start;
        int end;
        if (add == null || add.equals(""))
        {
            return style;
        }
        if (style == null)
        {
            style = new SpannableStringBuilder();
        }
        start = style.length();
        end = start + add.length();
        style.append(add);
        style.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(size), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }
}
