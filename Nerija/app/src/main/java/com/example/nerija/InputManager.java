package com.example.nerija;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InputManager implements Serializable {
    String departPlace;
    String arrivalPlace;
    String selected;
    Date date;

    private static final long serialVersionUID = 1L;

    public InputManager() {}

    public InputManager(String departPlace, String arrivalPlace)
    {
        this.departPlace = departPlace;
        this.arrivalPlace = arrivalPlace;
    }

    public InputManager(String departPlace, String arrivalPlace, String selected)
    {
        this.departPlace = departPlace;
        this.arrivalPlace = arrivalPlace;
        this.selected = selected;
    }

    public InputManager(String departPlace, String arrivalPlace, String selected, String date)
    {
        this.departPlace = departPlace;
        this.arrivalPlace = arrivalPlace;
        this.selected = selected;
        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setArrivalPlace(String arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }

    public String getArrivalPlace()
    {
        return arrivalPlace;
    }

    public void setDepartPlace(String departPlace)
    {
        this.departPlace = departPlace;
    }

    public String getDepartPlace() {
        return departPlace;
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

