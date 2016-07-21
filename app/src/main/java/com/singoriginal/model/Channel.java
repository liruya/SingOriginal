package com.singoriginal.model;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lanouhn on 16/7/20.
 */
public class Channel {

    private int IM;
    private String NA;

    public int getIM() {
        return IM;
    }

    public void setIM(int IM) {
        this.IM = IM;
    }

    public String getNA() {
        return NA;
    }

    public void setNA(String NA) {
        this.NA = NA;
    }

    public Channel(int IM, String NA) {
        this.IM = IM;
        this.NA = NA;
    }
}
