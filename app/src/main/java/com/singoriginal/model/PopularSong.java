package com.singoriginal.model;

/**
 * 排行榜最受欢迎数据类 Created by lanouhn on 16/7/27.
 */
public class PopularSong
{
    private int ID;
    private String SN;
    private String SK;
    private int UID;
    private int ST;
    private int DD;
    private long CT;
    private String GD;
    private String KM5;
    private String SC;
    private String SCSR;
    private User user;

    public PopularSong(long CT,
                       int DD,
                       String GD,
                       int ID,
                       String KM5,
                       String SC,
                       String SCSR,
                       String SK,
                       String SN,
                       int ST,
                       int UID,
                       User user)
    {
        this.CT = CT;
        this.DD = DD;
        this.GD = GD;
        this.ID = ID;
        this.KM5 = KM5;
        this.SC = SC;
        this.SCSR = SCSR;
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

    public long getCT()
    {
        return CT;
    }

    public void setCT(long CT)
    {
        this.CT = CT;
    }

    public String getGD()
    {
        return GD;
    }

    public void setGD(String GD)
    {
        this.GD = GD;
    }

    public String getKM5()
    {
        return KM5;
    }

    public void setKM5(String KM5)
    {
        this.KM5 = KM5;
    }

    public String getSC()
    {
        return SC;
    }

    public void setSC(String SC)
    {
        this.SC = SC;
    }

    public String getSCSR()
    {
        return SCSR;
    }

    public void setSCSR(String SCSR)
    {
        this.SCSR = SCSR;
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
