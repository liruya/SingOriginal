package com.singoriginal.model;

/**
 * 乐库.推荐.热门歌单数据类
 * Created by lanouhn on 16/7/21.
 */
public class Hotlist
{
    private int PlayCount;
    private String Title;
    private String Picture;
    private int PositionId;
    private String SongListId;

    public Hotlist(int playCount, String title, String picture, int positionId, String songListId)
    {
        PlayCount = playCount;
        Title = title;
        Picture = picture;
        PositionId = positionId;
        SongListId = songListId;
    }

    public int getPlayCount()
    {
        return PlayCount;
    }

    public void setPlayCount(int playCount)
    {
        PlayCount = playCount;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getPicture()
    {
        return Picture;
    }

    public void setPicture(String picture)
    {
        Picture = picture;
    }

    public int getPositionId()
    {
        return PositionId;
    }

    public void setPositionId(int positionId)
    {
        PositionId = positionId;
    }

    public String getSongListId()
    {
        return SongListId;
    }

    public void setSongListId(String songListId)
    {
        SongListId = songListId;
    }
}
