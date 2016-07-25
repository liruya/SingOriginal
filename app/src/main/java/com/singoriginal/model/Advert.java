package com.singoriginal.model;

/**
 * 音乐.推荐页面 轮播图数据类
 * Created by lanouhn on 16/7/20.
 */
public class Advert
{
    private int Id;
    private String Title;
    private String ImgUrl;
    private String LinkUrl;
    private String BehaviorType;

    public Advert(int Id, String Title, String ImgUrl, String LinkUrl, String BehaviorType)
    {
        this.Id = Id;
        this.Title = Title;
        this.ImgUrl = ImgUrl;
        this.LinkUrl = LinkUrl;
        this.BehaviorType = BehaviorType;
    }

    public int getId()
    {
        return Id;
    }

    public void setId(int Id)
    {
        this.Id = Id;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String Title)
    {
        this.Title = Title;
    }

    public String getImgUrl()
    {
        return ImgUrl;
    }

    public void setImgUrl(String ImgUrl)
    {
        this.ImgUrl = ImgUrl;
    }

    public String getLinkUrl()
    {
        return LinkUrl;
    }

    public void setLinkUrl(String LinkUrl)
    {
        this.LinkUrl = LinkUrl;
    }

    public String getBehaviorType()
    {
        return BehaviorType;
    }

    public void setBehaviorType(String BehaviorType)
    {
        this.BehaviorType = BehaviorType;
    }
}
