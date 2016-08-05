package com.singoriginal.model;

/**
 * Created by lanouhn on 16/8/4.
 */
public class HeadIconWork {

    private String message;
    private boolean success;
    private Data[] data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public static class Data {

        private int ID;
        private String SN;
        private String SK;
        private int UID;
        private int ST;
        private int DD;
        private long CT;
        private String GD;
        private String KM5;
        private User user;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getSN() {
            return SN;
        }

        public void setSN(String SN) {
            this.SN = SN;
        }

        public String getSK() {
            return SK;
        }

        public void setSK(String SK) {
            this.SK = SK;
        }

        public int getUID() {
            return UID;
        }

        public void setUID(int UID) {
            this.UID = UID;
        }

        public int getST() {
            return ST;
        }

        public void setST(int ST) {
            this.ST = ST;
        }

        public int getDD() {
            return DD;
        }

        public void setDD(int DD) {
            this.DD = DD;
        }

        public long getCT() {
            return CT;
        }

        public void setCT(long CT) {
            this.CT = CT;
        }

        public String getGD() {
            return GD;
        }

        public void setGD(String GD) {
            this.GD = GD;
        }

        public String getKM5() {
            return KM5;
        }

        public void setKM5(String KM5) {
            this.KM5 = KM5;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public static class User {

            private int ID;
            private String NN;
            private String I;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getNN() {
                return NN;
            }

            public void setNN(String NN) {
                this.NN = NN;
            }

            public String getI() {
                return I;
            }

            public void setI(String i) {
                I = i;
            }
        }
    }
}
