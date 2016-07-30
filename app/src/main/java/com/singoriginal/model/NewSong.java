package com.singoriginal.model;

/**排行榜明细数据类
 * Created by lanouhn on 16/7/27.
 */
public class NewSong
{
    private int ID;
    private String SN;
    private String SK;
    private int UID;
    private int ST;
    private int DD;
    private long CT;
    private String KM5;
    private User user;

    public NewSong(int DD, int ID, String SK, String SN, int ST, int UID, User user)
    {
        this.DD = DD;
        this.ID = ID;
        this.SK = SK;
        this.SN = SN;
        this.ST = ST;
        this.UID = UID;
        this.user = user;
    }

    public int getDD()
    {
        return DD;
    }

    public void setDD(int DD)
    {
        this.DD = DD;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public String getSK()
    {
        return SK;
    }

    public void setSK(String SK)
    {
        this.SK = SK;
    }

    public String getSN()
    {
        return SN;
    }

    public void setSN(String SN)
    {
        this.SN = SN;
    }

    public int getST()
    {
        return ST;
    }

    public void setST(int ST)
    {
        this.ST = ST;
    }

    public int getUID()
    {
        return UID;
    }

    public void setUID(int UID)
    {
        this.UID = UID;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public static class User
    {
        private int ID;
        private String NN;
        private String I;

        public User(String i, int ID, String NN)
        {
            I = i;
            this.ID = ID;
            this.NN = NN;
        }

        public String getI()
        {
            return I;
        }

        public void setI(String i)
        {
            I = i;
        }

        public int getID()
        {
            return ID;
        }

        public void setID(int ID)
        {
            this.ID = ID;
        }

        public String getNN()
        {
            return NN;
        }

        public void setNN(String NN)
        {
            this.NN = NN;
        }
    }

}
