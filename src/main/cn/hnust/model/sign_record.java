package main.cn.hnust.model;

import java.sql.Time;
import java.sql.Date;

public class sign_record {
    private String ID;
    private Date record_date ;
    private String user_ID ;
    private int sign_num ;
    private Time start_time ;
    private Time end_time ;
    private double location_Latitude ;
    private double location_Longitude ;
    private double location_Precision ;

    public sign_record() {
    }

    public sign_record(String ID, Date record_date, String user_ID, int sign_num, Time start_time, Time end_time, double location_Latitude, double location_Longitude, double location_Precision) {
        this.ID = ID;
        this.record_date = record_date;
        this.user_ID = user_ID;
        this.sign_num = sign_num;
        this.start_time = start_time;
        this.end_time = end_time;
        this.location_Latitude = location_Latitude;
        this.location_Longitude = location_Longitude;
        this.location_Precision = location_Precision;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getRecord_date() {
        return record_date;
    }

    public void setRecord_date(Date record_date) {
        this.record_date = record_date;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public int getSign_num() {
        return sign_num;
    }

    public void setSign_num(int sign_num) {
        this.sign_num = sign_num;
    }

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }

    public double getLocation_Latitude() {
        return location_Latitude;
    }

    public void setLocation_Latitude(double location_Latitude) {
        this.location_Latitude = location_Latitude;
    }

    public double getLocation_Longitude() {
        return location_Longitude;
    }

    public void setLocation_Longitude(double location_Longitude) {
        this.location_Longitude = location_Longitude;
    }

    public double getLocation_Precision() {
        return location_Precision;
    }

    public void setLocation_Precision(double location_Precision) {
        this.location_Precision = location_Precision;
    }

    @Override
    public String toString() {
        return "sign_record{" +
                "ID='" + ID + '\'' +
                ", record_date=" + record_date +
                ", user_ID='" + user_ID + '\'' +
                ", sign_num=" + sign_num +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", location_Latitude=" + location_Latitude +
                ", location_Longitude=" + location_Longitude +
                ", location_Precision=" + location_Precision +
                '}';
    }
}
