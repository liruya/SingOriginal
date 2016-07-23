package com.singoriginal.model;

/**
 * 乐库.排行榜数据类
 * Created by lanouhn on 16/7/23.
 */
public class TopRank
{
    private String id;
    private String name;
    private String photo;
    private String[] songs;
    private int count;
    private String photoBig;

    public TopRank(String id, String name, String photo, String[] songs, int count, String photoBig)
    {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.songs = songs;
        this.count = count;
        this.photoBig = photoBig;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public String[] getSongs()
    {
        return songs;
    }

    public void setSongs(String[] songs)
    {
        this.songs = songs;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public String getPhotoBig()
    {
        return photoBig;
    }

    public void setPhotoBig(String photoBig)
    {
        this.photoBig = photoBig;
    }
}
