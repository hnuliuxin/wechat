package main.cn.hnust.model;

public class participants {
    private String ID;
    private String user_ID;
    private String user_name;
    private boolean sign_status;
    private String sign_record_ID;

    public participants() {
    }

    public participants(String ID, String user_ID, String user_name, boolean sign_status, String sign_record_ID) {
        this.ID = ID;
        this.user_ID = user_ID;
        this.user_name = user_name;
        this.sign_status = sign_status;
        this.sign_record_ID = sign_record_ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public boolean isSign_status() {
        return sign_status;
    }

    public void setSign_status(boolean sign_status) {
        this.sign_status = sign_status;
    }

    public String getSign_record_ID() {
        return sign_record_ID;
    }

    public void setSign_record_ID(String sign_record_ID) {
        this.sign_record_ID = sign_record_ID;
    }

    @Override
    public String toString() {
        return "participants{" +
                "ID='" + ID + '\'' +
                ", user_ID='" + user_ID + '\'' +
                ", user_name='" + user_name + '\'' +
                ", sign_status=" + sign_status +
                ", sign_record_ID=" + sign_record_ID +
                '}';
    }
}
