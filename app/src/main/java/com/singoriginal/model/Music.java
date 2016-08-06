package com.singoriginal.model;

/**
 * 音乐数据类 整合几种类似的数据类为统一格式
 * Created by lanouhn on 16/7/30.
 */
public class Music
{
    private String songid;
    private String songname;
    private String songtype;
    private int userid;
    private String username;
    private String userimg;

    public Music(String songid,
                 String songname,
                 String songtype,
                 int userid,
                 String username,
                 String userimg)
    {
        this.songid = songid;
        this.songname = songname;
        this.songtype = songtype;
        this.userid = userid;
        this.userimg = userimg;
        this.username = username;
    }

    public String getSongid()
    {
        return songid;
    }

    public void setSongid(String songid)
    {
        this.songid = songid;
    }

    public String getSongname()
    {
        return songname;
    }

    public void setSongname(String songname)
    {
        this.songname = songname;
    }

    public String getSongtype()
    {
        return songtype;
    }

    public void setSongtype(String songtype)
    {
        this.songtype = songtype;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    public String getUserimg()
    {
        return userimg;
    }

    public void setUserimg(String userimg)
    {
        this.userimg = userimg;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

}
