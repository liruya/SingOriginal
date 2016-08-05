package com.singoriginal.model;

/**
 * 歌曲详情数据类
 * Created by lanouhn on 16/8/2.
 */
public class MusicDetail
{
    private String message;
    private boolean success;
    private Data data;
    private int code;

    public MusicDetail(int code, Data data, String message, boolean success)
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

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public static class Data
    {
        private int ID;
        private String SN;
        private String SK;
        private String SW;
        private int SS;
        private int ST;
        private String SI;
        private long CT;
        private String[] img;
        private String word;
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
        private String KL;
        private String KLM;
        private String KL128;
        private String GD;
        private User user;
        private String dynamicWords;

        public Data(int c,
                    int CK,
                    int CS,
                    long CT,
                    int d,
                    int DD,
                    String dynamicWords,
                    int e,
                    String GD,
                    String HY,
                    int ID,
                    String[] img,
                    String KL128,
                    String KL,
                    String KLM,
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
                    User user,
                    String WO,
                    String word,
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
            this.dynamicWords = dynamicWords;
            E = e;
            this.GD = GD;
            this.HY = HY;
            this.ID = ID;
            this.img = img;
            this.KL128 = KL128;
            this.KL = KL;
            this.KLM = KLM;
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
            this.user = user;
            this.WO = WO;
            this.word = word;
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

        public String getDynamicWords()
        {
            return dynamicWords;
        }

        public void setDynamicWords(String dynamicWords)
        {
            this.dynamicWords = dynamicWords;
        }

        public int getE()
        {
            return E;
        }

        public void setE(int e)
        {
            E = e;
        }

        public String getGD()
        {
            return GD;
        }

        public void setGD(String GD)
        {
            this.GD = GD;
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

        public String[] getImg()
        {
            return img;
        }

        public void setImg(String[] img)
        {
            this.img = img;
        }

        public String getKL128()
        {
            return KL128;
        }

        public void setKL128(String KL128)
        {
            this.KL128 = KL128;
        }

        public String getKL()
        {
            return KL;
        }

        public void setKL(String KL)
        {
            this.KL = KL;
        }

        public String getKLM()
        {
            return KLM;
        }

        public void setKLM(String KLM)
        {
            this.KLM = KLM;
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

        public User getUser()
        {
            return user;
        }

        public void setUser(User user)
        {
            this.user = user;
        }

        public String getWO()
        {
            return WO;
        }

        public void setWO(String WO)
        {
            this.WO = WO;
        }

        public String getWord()
        {
            return word;
        }

        public void setWord(String word)
        {
            this.word = word;
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

        public static class User
        {
            private int ID;
            private String NN;
            private String I;
            private int YCRQ;
            private int FCRQ;

            public User(int FCRQ, String i, int ID, String NN, int YCRQ)
            {
                this.FCRQ = FCRQ;
                I = i;
                this.ID = ID;
                this.NN = NN;
                this.YCRQ = YCRQ;
            }

            public int getFCRQ()
            {
                return FCRQ;
            }

            public void setFCRQ(int FCRQ)
            {
                this.FCRQ = FCRQ;
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

            public int getYCRQ()
            {
                return YCRQ;
            }

            public void setYCRQ(int YCRQ)
            {
                this.YCRQ = YCRQ;
            }
        }
    }

}
