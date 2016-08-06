package com.singoriginal.model;

/**
 * 歌曲简要信息数据类
 * Created by lanouhn on 16/7/29.
 */
public class SongBrief
{
    private int code;
    private String message;
    private Data data;
    private String success;

    public SongBrief(int code, Data data, String message, String success)
    {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public Data getData()
    {
        return data;
    }

    public void setData(Data data)
    {
        this.data = data;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getSuccess()
    {
        return success;
    }

    public void setSuccess(String success)
    {
        this.success = success;
    }

    public static class Data
    {
        private int songid;
        private String songtype;
        private String squrl;
        private int sqsize;
        private String sqext;
        private String hqurl;
        private int hqsize;
        private String hqext;
        private String lqurl;
        private int lqsize;
        private String lqext;

        public Data(String hqext,
                    int hqsize,
                    String hqurl,
                    String lqext,
                    int lqsize,
                    String lqurl,
                    int songid,
                    String songtype,
                    String sqext,
                    int sqsize,
                    String squrl)
        {
            this.hqext = hqext;
            this.hqsize = hqsize;
            this.hqurl = hqurl;
            this.lqext = lqext;
            this.lqsize = lqsize;
            this.lqurl = lqurl;
            this.songid = songid;
            this.songtype = songtype;
            this.sqext = sqext;
            this.sqsize = sqsize;
            this.squrl = squrl;
        }

        public int getSongid()
        {
            return songid;
        }

        public void setSongid(int songid)
        {
            this.songid = songid;
        }

        public String getHqext()
        {
            return hqext;
        }

        public void setHqext(String hqext)
        {
            this.hqext = hqext;
        }

        public int getHqsize()
        {
            return hqsize;
        }

        public void setHqsize(int hqsize)
        {
            this.hqsize = hqsize;
        }

        public String getHqurl()
        {
            return hqurl;
        }

        public void setHqurl(String hqurl)
        {
            this.hqurl = hqurl;
        }

        public String getLqext()
        {
            return lqext;
        }

        public void setLqext(String lqext)
        {
            this.lqext = lqext;
        }

        public int getLqsize()
        {
            return lqsize;
        }

        public void setLqsize(int lqsize)
        {
            this.lqsize = lqsize;
        }

        public String getLqurl()
        {
            return lqurl;
        }

        public void setLqurl(String lqurl)
        {
            this.lqurl = lqurl;
        }

        public String getSongtype()
        {
            return songtype;
        }

        public void setSongtype(String songtype)
        {
            this.songtype = songtype;
        }

        public String getSqext()
        {
            return sqext;
        }

        public void setSqext(String sqext)
        {
            this.sqext = sqext;
        }

        public int getSqsize()
        {
            return sqsize;
        }

        public void setSqsize(int sqsize)
        {
            this.sqsize = sqsize;
        }

        public String getSqurl()
        {
            return squrl;
        }

        public void setSqurl(String squrl)
        {
            this.squrl = squrl;
        }
    }
}
