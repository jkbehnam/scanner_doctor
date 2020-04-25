package com.doctor.mokhtari.scanner_doc.activities.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patient {
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("bday")
    @Expose
    String bday;
    @SerializedName("gender")
    @Expose
    String gender;
    @SerializedName("address")
    @Expose
    String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
