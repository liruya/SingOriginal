package com.singoriginal.model;

/**
 * 音乐人数据类
 * Created by lanouhn on 16/7/28.
 */
public class Musician
{
    private int ID;
    private int follow;
    private String NN;
    private String I;
    private int TFS;
    private long YCRQ;
    private long FCRQ;
    private String M;
    private Song Song;

    public Musician(long FCRQ,
                    int follow,
                    String i,
                    int ID,
                    String m,
                    String NN,
                    Song song,
                    int TFS,
                    long YCRQ)
    {
        this.FCRQ = FCRQ;
        this.follow = follow;
        this.I = i;
        this.ID = ID;
        this.M = m;
        this.NN = NN;
        this.Song = song;
        this.TFS = TFS;
        this.YCRQ = YCRQ;
    }

    public long getFCRQ()
    {
        return FCRQ;
    }

    public void setFCRQ(long FCRQ)
    {
        this.FCRQ = FCRQ;
    }

    public int getFollow()
    {
        return follow;
    }

    public void setFollow(int follow)
    {
        this.follow = follow;
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

    public String getM()
    {
        return M;
    }

    public void setM(String m)
    {
        M = m;
    }

    public String getNN()
    {
        return NN;
    }

    public void setNN(String NN)
    {
        this.NN = NN;
    }

    public Song getSong()
    {
        return Song;
    }

    public void setSong(Song song)
    {
        Song = song;
    }

    public int getTFS()
    {
        return TFS;
    }

    public void setTFS(int TFS)
    {
        this.TFS = TFS;
    }

    public long getYCRQ()
    {
        return YCRQ;
    }

    public void setYCRQ(long YCRQ)
    {
        this.YCRQ = YCRQ;
    }

    public static class Song
    {
        private int ID;
        private String SN;
        private String SK;
        private String SW;
        private int SS;
        private int ST;
        private String SI;
        private long CT;
        private String M;
        private String S;
        private String ZQ;
        private String WO;
        private String ZC;
        private String HY;
        private String YG;
        private int CK;
        private int D;
        private int RQ;
        private int DD;
        private int E;
        private int R;
        private int RC;
        private int SG;
        private int C;
        private int CS;
        private int LV;
        private String LG;
        private String SY;
        private int UID;
        private int PT;
        private int SCSR;
        private int SC;

        public Song(int c,
                    int CK,
                    int CS,
                    long CT,
                    int d,
                    int DD,
                    int e,
                    String HY,
                    int ID,
                    String LG,
                    int LV,
                    String m,
                    int PT,
                    int r,
                    int RC,
                    int RQ,
                    String s,
                    int SC,
                    int SCSR,
                    int SG,
                    String SI,
                    String SK,
                    String SN,
                    int SS,
                    int ST,
                    String SW,
                    String SY,
                    int UID,
                    String WO,
                    String YG,
                    String ZC,
                    String ZQ)
        {
            C = c;
            this.CK = CK;
            this.CS = CS;
            this.CT = CT;
            D = d;
            this.DD = DD;
            E = e;
            this.HY = HY;
            this.ID = ID;
            this.LG = LG;
            this.LV = LV;
            M = m;
            this.PT = PT;
            R = r;
            this.RC = RC;
            this.RQ = RQ;
            S = s;
            this.SC = SC;
            this.SCSR = SCSR;
            this.SG = SG;
            this.SI = SI;
            this.SK = SK;
            this.SN = SN;
            this.SS = SS;
            this.ST = ST;
            this.SW = SW;
            this.SY = SY;
            this.UID = UID;
            this.WO = WO;
            this.YG = YG;
            this.ZC = ZC;
            this.ZQ = ZQ;
        }

        public int getC()
        {
            return C;
        }

        public void setC(int c)
        {
            C = c;
        }

        public int getCK()
        {
            return CK;
        }

        public void setCK(int CK)
        {
            this.CK = CK;
        }

        public int getCS()
        {
            return CS;
        }

        public void setCS(int CS)
        {
            this.CS = CS;
        }

        public long getCT()
        {
            return CT;
        }

        public void setCT(long CT)
        {
            this.CT = CT;
        }

        public int getD()
        {
            return D;
        }

        public void setD(int d)
        {
            D = d;
        }

        public int getDD()
        {
            return DD;
        }

        public void setDD(int DD)
        {
            this.DD = DD;
        }

        public int getE()
        {
            return E;
        }

        public void setE(int e)
        {
            E = e;
        }

        public String getHY()
        {
            return HY;
        }

        public void setHY(String HY)
        {
            this.HY = HY;
        }

        public int getID()
        {
            return ID;
        }

        public void setID(int ID)
        {
            this.ID = ID;
        }

        public String getLG()
        {
            return LG;
        }

        public void setLG(String LG)
        {
            this.LG = LG;
        }

        public int getLV()
        {
            return LV;
        }

        public void setLV(int LV)
        {
            this.LV = LV;
        }

        public String getM()
        {
            return M;
        }

        public void setM(String m)
        {
            M = m;
        }

        public int getPT()
        {
            return PT;
        }

        public void setPT(int PT)
        {
            this.PT = PT;
        }

        public int getR()
        {
            return R;
        }

        public void setR(int r)
        {
            R = r;
        }

        public int getRC()
        {
            return RC;
        }

        public void setRC(int RC)
        {
            this.RC = RC;
        }

        public int getRQ()
        {
            return RQ;
        }

        public void setRQ(int RQ)
        {
            this.RQ = RQ;
        }

        public String getS()
        {
            return S;
        }

        public void setS(String s)
        {
            S = s;
        }

        public int getSC()
        {
            return SC;
        }

        public void setSC(int SC)
        {
            this.SC = SC;
        }

        public int getSCSR()
        {
            return SCSR;
        }

        public void setSCSR(int SCSR)
        {
            this.SCSR = SCSR;
        }

        public int getSG()
        {
            return SG;
        }

        public void setSG(int SG)
        {
            this.SG = SG;
        }

        public String getSI()
        {
            return SI;
        }

        public void setSI(String SI)
        {
            this.SI = SI;
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

        public int getSS()
        {
            return SS;
        }

        public void setSS(int SS)
        {
            this.SS = SS;
        }

        public int getST()
        {
            return ST;
        }

        public void setST(int ST)
        {
            this.ST = ST;
        }

        public String getSW()
        {
            return SW;
        }

        public void setSW(String SW)
        {
            this.SW = SW;
        }

        public String getSY()
        {
            return SY;
        }

        public void setSY(String SY)
        {
            this.SY = SY;
        }

        public int getUID()
        {
            return UID;
        }

        public void setUID(int UID)
        {
            this.UID = UID;
        }

        public String getWO()
        {
            return WO;
        }

        public void setWO(String WO)
        {
            this.WO = WO;
        }

        public String getYG()
        {
            return YG;
        }

        public void setYG(String YG)
        {
            this.YG = YG;
        }

        public String getZC()
        {
            return ZC;
        }

        public void setZC(String ZC)
        {
            this.ZC = ZC;
        }

        public String getZQ()
        {
            return ZQ;
        }

        public void setZQ(String ZQ)
        {
            this.ZQ = ZQ;
        }
    }
}
