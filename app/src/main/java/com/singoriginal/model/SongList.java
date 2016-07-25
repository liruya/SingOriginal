package com.singoriginal.model;

/**
 * 音乐集合数据类
 * Created by lanouhn on 16/7/25.
 */
public class SongList
{
    private String ID;
    private int CS;
    private int H;
    private long CT;
    private String T;
    private String C;
    private String P;
    private int E;
    private int S;
    private User user;
    private String L;
    private Songs songs;

    public SongList(String ID,
                    int CS,
                    int h,
                    long CT,
                    String t,
                    String c,
                    String p,
                    int e,
                    int s,
                    User user,
                    String l,
                    Songs songs)
    {
        this.ID = ID;
        this.CS = CS;
        H = h;
        this.CT = CT;
        T = t;
        C = c;
        P = p;
        E = e;
        S = s;
        this.user = user;
        L = l;
        this.songs = songs;
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public int getCS()
    {
        return CS;
    }

    public void setCS(int CS)
    {
        this.CS = CS;
    }

    public int getH()
    {
        return H;
    }

    public void setH(int h)
    {
        H = h;
    }

    public long getCT()
    {
        return CT;
    }

    public void setCT(long CT)
    {
        this.CT = CT;
    }

    public String getT()
    {
        return T;
    }

    public void setT(String t)
    {
        T = t;
    }

    public String getC()
    {
        return C;
    }

    public void setC(String c)
    {
        C = c;
    }

    public String getP()
    {
        return P;
    }

    public void setP(String p)
    {
        P = p;
    }

    public int getE()
    {
        return E;
    }

    public void setE(int e)
    {
        E = e;
    }

    public int getS()
    {
        return S;
    }

    public void setS(int s)
    {
        S = s;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public String getL()
    {
        return L;
    }

    public void setL(String l)
    {
        L = l;
    }

    public Songs getSongs()
    {
        return songs;
    }

    public void setSongs(Songs songs)
    {
        this.songs = songs;
    }

    public static class User
    {
        private int ID;
        private String NN;
        private String I;

        public User(int ID, String NN, String i)
        {
            this.ID = ID;
            this.NN = NN;
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

        public String getI()
        {
            return I;
        }

        public void setI(String i)
        {
            I = i;
        }
    }

    public static class Songs
    {
        private int ID;
        private String SN;
        private int UID;
        private String SK;
        private User user;

        public Songs(int ID, String SN, int UID, String SK, User user)
        {
            this.ID = ID;
            this.SN = SN;
            this.UID = UID;
            this.SK = SK;
            this.user = user;
        }

        public int getID()
        {
            return ID;
        }

        public void setID(int ID)
        {
            this.ID = ID;
        }

        public String getSN()
        {
            return SN;
        }

        public void setSN(String SN)
        {
            this.SN = SN;
        }

        public int getUID()
        {
            return UID;
        }

        public void setUID(int UID)
        {
            this.UID = UID;
        }

        public String getSK()
        {
            return SK;
        }

        public void setSK(String SK)
        {
            this.SK = SK;
        }

        public User getUser()
        {
            return user;
        }

        public void setUser(User user)
        {
            this.user = user;
        }
    }
}
