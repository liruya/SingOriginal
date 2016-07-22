package com.singoriginal.model;

import org.json.JSONObject;

/**
 * Created by lanouhn on 16/7/21.
 */
public class DynamicSquare {

    private String id;
    private long createtime;
    private int category;
    private String content;
    private DynamicSquareUser user;
    private DynamicSquareContent dynamicSquareContent;


    public DynamicSquareContent getDynamicSquareContent() {
        return dynamicSquareContent;
    }

    public void setDynamicSquareContent(DynamicSquareContent dynamicSquareContent) {
        this.dynamicSquareContent = dynamicSquareContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DynamicSquareUser getUser() {
        return user;
    }

    public void setUser(DynamicSquareUser user) {
        this.user = user;
    }
}
