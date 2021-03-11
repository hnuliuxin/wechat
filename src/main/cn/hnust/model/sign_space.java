package main.cn.hnust.model;

public class sign_space {
    private String ID;
    private String space_name;
    private String user_ID;

    public sign_space() {
    }

    public sign_space(String ID, String space_name, String user_ID) {
        this.ID = ID;
        this.space_name = space_name;
        this.user_ID = user_ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSpace_name() {
        return space_name;
    }

    public void setSpace_name(String space_name) {
        this.space_name = space_name;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    @Override
    public String toString() {
        return "sign_space{" +
                "ID=" + ID +
                ", space_name='" + space_name + '\'' +
                ", user_ID='" + user_ID + '\'' +
                '}';
    }
}
