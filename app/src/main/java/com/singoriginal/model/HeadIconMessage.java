package com.singoriginal.model;

/**
 * Created by lanouhn on 16/8/4.
 */
public class HeadIconMessage {

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

        private Comments[] comments;

        public Comments[] getComments() {
            return comments;
        }

        public void setComments(Comments[] comments) {
            this.comments = comments;
        }

        public static class Comments {

            private String id;
            private String content;
            private int like;
            private String userId;
            private int repliesCount;
            private String createTime;
            private User user;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getLike() {
                return like;
            }

            public void setLike(int like) {
                this.like = like;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public int getRepliesCount() {
                return repliesCount;
            }

            public void setRepliesCount(int repliesCount) {
                this.repliesCount = repliesCount;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
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

                public String getI() {
                    return I;
                }

                public void setI(String i) {
                    I = i;
                }

                public String getNN() {
                    return NN;
                }

                public void setNN(String NN) {
                    this.NN = NN;
                }
            }
        }
    }
}
