package com.singoriginal.model;

/**
 * 乐库.推荐.音乐专题数据类
 * Created by lanouhn on 16/7/21.
 */
public class MusicTopics
{
    private int Id;
    private String Title;
    private String Url;
    private String ImgUrl;
    private long CreateTime;

    public MusicTopics(int id, String title, String url, String imgUrl, long createTime)
    {
        Id = id;
        Title = title;
        Url = url;
        ImgUrl = imgUrl;
        CreateTime = createTime;
    }

    public int getId()
    {
        return Id;
    }

    public void setId(int id)
    {
        Id = id;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getUrl()
    {
        return Url;
    }

    public void setUrl(String url)
    {
        Url = url;
    }

    public String getImgUrl()
    {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        ImgUrl = imgUrl;
    }

    public long getCreateTime()
    {
        return CreateTime;
    }

    public void setCreateTime(long createTime)
    {
        CreateTime = createTime;
    }
}
