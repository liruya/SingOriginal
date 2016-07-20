package com.singoriginal.model;

/**
 * 音乐.推荐页面 轮播图数据类
 * Created by lanouhn on 16/7/20.
 */
public class Advert
{
    private int id;
    private String title;
    private String imgUrl;
    private String linkUrl;
    private int behaviorType;

    public Advert(int id, String title, String imgUrl, String linkUrl, int behaviorType)
    {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.linkUrl = linkUrl;
        this.behaviorType = behaviorType;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public String getLinkUrl()
    {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl)
    {
        this.linkUrl = linkUrl;
    }

    public int getBehaviorType()
    {
        return behaviorType;
    }

    public void setBehaviorType(int behaviorType)
    {
        this.behaviorType = behaviorType;
    }
}
