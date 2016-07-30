package com.singoriginal.model;

/**
 * 每日推荐数据类
 * Created by lanouhn on 16/7/28.
 */
public class DailyRecmd
{
    private String Picture;
    private String RecommendName;
    private String RecommendWords;
    private String RecommendTime;
    private String SongType;
    private String SongId;
    private int UserId;
    private String NickName;
    private String Image;
    private boolean IsNew;

    public DailyRecmd(String image,
                      boolean isNew,
                      String nickName,
                      String picture,
                      String recommendName,
                      String recommendTime,
                      String recommendWords,
                      String songId,
                      String songType,
                      int userId)
    {
        Image = image;
        IsNew = isNew;
        NickName = nickName;
        Picture = picture;
        RecommendName = recommendName;
        RecommendTime = recommendTime;
        RecommendWords = recommendWords;
        SongId = songId;
        SongType = songType;
        UserId = userId;
    }

    public String getImage()
    {
        return Image;
    }

    public void setImage(String image)
    {
        Image = image;
    }

    public boolean isNew()
    {
        return IsNew;
    }

    public void setNew(boolean aNew)
    {
        IsNew = aNew;
    }

    public String getNickName()
    {
        return NickName;
    }

    public void setNickName(String nickName)
    {
        NickName = nickName;
    }

    public String getPicture()
    {
        return Picture;
    }

    public void setPicture(String picture)
    {
        Picture = picture;
    }

    public String getRecommendName()
    {
        return RecommendName;
    }

    public void setRecommendName(String recommendName)
    {
        RecommendName = recommendName;
    }

    public String getRecommendTime()
    {
        return RecommendTime;
    }

    public void setRecommendTime(String recommendTime)
    {
        RecommendTime = recommendTime;
    }

    public String getRecommendWords()
    {
        return RecommendWords;
    }

    public void setRecommendWords(String recommendWords)
    {
        RecommendWords = recommendWords;
    }

    public String getSongId()
    {
        return SongId;
    }

    public void setSongId(String songId)
    {
        SongId = songId;
    }

    public String getSongType()
    {
        return SongType;
    }

    public void setSongType(String songType)
    {
        SongType = songType;
    }

    public int getUserId()
    {
        return UserId;
    }

    public void setUserId(int userId)
    {
        UserId = userId;
    }
}
