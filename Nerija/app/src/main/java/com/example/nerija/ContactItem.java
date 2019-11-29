package com.example.nerija;

import java.io.Serializable;

public class ContactItem implements Serializable
{
    private String phoneNumber;
    private String name;
    private long photoId=0;
    private long personId=0;
    private int id;

    public ContactItem(){}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public long getPersonId() {
        return personId;
    }

    public int getId() {
        return id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String deleteHyphen()
    {
        return phoneNumber.replace("-", "");
    }

    @Override
    public boolean equals(Object o)
    {

        return false;
    }
}
