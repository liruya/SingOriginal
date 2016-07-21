package com.singoriginal.model;

/**
 * 乐库.推荐.LIVE直播数据类
 * Created by lanouhn on 16/7/21.
 */
public class Liveroom
{
    private String roomId;
    private String usrId;
    private String imgPath;
    private String recommendTime;
    private String nickName;
    private String wsingId;
    private int audience;
    private String userLogo;
    private int fans;
    private String week;
    private String time;
    private String type;

    public Liveroom(String roomId,
                    String usrId,
                    String imgPath,
                    String recommendTime,
                    String nickName,
                    String wsingId,
                    int audience,
                    String userLogo,
                    int fans,
                    String week,
                    String time,
                    String type)
    {
        this.roomId = roomId;
        this.usrId = usrId;
        this.imgPath = imgPath;
        this.recommendTime = recommendTime;
        this.nickName = nickName;
        this.wsingId = wsingId;
        this.audience = audience;
        this.userLogo = userLogo;
        this.fans = fans;
        this.week = week;
        this.time = time;
        this.type = type;
    }

    public String getRoomId()
    {
        return roomId;
    }

    public void setRoomId(String roomId)
    {
        this.roomId = roomId;
    }

    public String getUsrId()
    {
        return usrId;
    }

    public void setUsrId(String usrId)
    {
        this.usrId = usrId;
    }

    public String getImgPath()
    {
        return imgPath;
    }

    public void setImgPath(String imgPath)
    {
        this.imgPath = imgPath;
    }

    public String getRecommendTime()
    {
        return recommendTime;
    }

    public void setRecommendTime(String recommendTime)
    {
        this.recommendTime = recommendTime;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getWsingId()
    {
        return wsingId;
    }

    public void setWsingId(String wsingId)
    {
        this.wsingId = wsingId;
    }

    public int getAudience()
    {
        return audience;
    }

    public void setAudience(int audience)
    {
        this.audience = audience;
    }

    public String getUserLogo()
    {
        return userLogo;
    }

    public void setUserLogo(String userLogo)
    {
        this.userLogo = userLogo;
    }

    public int getFans()
    {
        return fans;
    }

    public void setFans(int fans)
    {
        this.fans = fans;
    }

    public String getWeek()
    {
        return week;
    }

    public void setWeek(String week)
    {
        this.week = week;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
