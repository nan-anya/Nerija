package com.example.nerija;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInputData implements Serializable {
    private String departPlaceID;
    private String departPlaceName;
    private String arrivalPlaceID;
    private Date date;

    private static final long serialVersionUID = 1L;

    public UserInputData() {}

    public void setArrivalPlaceID(String arrivalPlaceID) {
        this.arrivalPlaceID = arrivalPlaceID;
    }

    public String getArrivalPlaceID()
    {
        return arrivalPlaceID;
    }

    public void setDepartPlaceID(String departPlaceID)
    {
        this.departPlaceID = departPlaceID;
    }

    public String getDepartPlaceID() {
        return departPlaceID;
    }

    public String getDepartPlaceName() {
        return departPlaceName;
    }

    public void setDepartPlaceName(String departPlaceName) {
        this.departPlaceName = departPlaceName;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

class AlarmBaseData implements Serializable
{
    Date date;

    String arrivalPlaceName;

    String phoneNum;

    String reciverName;

    private static final long serialVersionUID = 1L;

    public AlarmBaseData(){}

    public AlarmBaseData(Date date, String arrivalPlaceName)
    {
        this.date = new Date(date.getTime());
        this.arrivalPlaceName = arrivalPlaceName;
        this.phoneNum = "";
        this.reciverName = "";
    }

    public AlarmBaseData(Date date, String arrivalPlaceName, String phoneNum, String reciverName)
    {
        this.date = new Date(date.getTime());
        this.arrivalPlaceName = arrivalPlaceName;
        this.phoneNum = phoneNum;
        this.reciverName = reciverName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getArrivalPlaceName() {
        return arrivalPlaceName;
    }

    public void setArrivalPlaceName(String arrivalPlaceName) {
        this.arrivalPlaceName = arrivalPlaceName;
    }
}
