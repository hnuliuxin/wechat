package main.cn.hnust.model;

public class user_infomation {
    private String ID;
    private String user_ID;
    private String user_name;
    private String department;
    private String sign_space_ID;

    public user_infomation() {
    }

    public user_infomation(String ID, String user_ID, String user_name, String department, String sign_space_ID) {
        this.ID = ID;
        this.user_ID = user_ID;
        this.user_name = user_name;
        this.department = department;
        this.sign_space_ID = sign_space_ID;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSign_space_ID() {
        return sign_space_ID;
    }

    public void setSign_space_ID(String sign_space_ID) {
        this.sign_space_ID = sign_space_ID;
    }

    @Override
    public String toString() {
        return "user_infomation{" +
                "ID=" + ID +
                ", user_ID='" + user_ID + '\'' +
                ", user_name='" + user_name + '\'' +
                ", department='" + department + '\'' +
                ", sign_space_ID=" + sign_space_ID +
                '}';
    }
}
