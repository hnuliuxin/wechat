package main.cn.hnust.model;

public class user {
    private String openID ;
    private String user_name ;
    private String user_ID ;
    private String user_password;

    public user() {
    }

    public user(String openID, String user_name, String user_ID, String user_password) {
        this.openID = openID;
        this.user_name = user_name;
        this.user_ID = user_ID;
        this.user_password = user_password;
    }

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    @Override
    public String toString() {
        return "user{" +
                "openID='" + openID + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_ID='" + user_ID + '\'' +
                ", user_password='" + user_password + '\'' +
                '}';
    }
}
