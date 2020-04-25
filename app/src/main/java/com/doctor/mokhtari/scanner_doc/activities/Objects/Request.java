package com.doctor.mokhtari.scanner_doc.activities.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {
    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    @SerializedName("request_id")
    @Expose
    private String request_id;
    @SerializedName("bodypart")
    @Expose
    private String request_bodypart;
    @SerializedName("time")
    @Expose
    private String request_date;
    @SerializedName("patient")
    @Expose
    private String request_patient;
    @SerializedName("diagnosis")
    @Expose
    private String diagnosis;
    @SerializedName("treatment")
    @Expose
    private String treatment;

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("state")
    @Expose
    private String request_state;
    @SerializedName("photo")
    @Expose
    private String request_img;

    public Request(String reqiest_bodypart, String request_date, String request_doctor, String request_state, String request_img){
        this.request_bodypart =reqiest_bodypart;
        this.request_date=request_date;
        this.request_patient =request_doctor;
        this.request_state=request_state;
        this.request_img=request_img;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getRequest_bodypart() {
        return request_bodypart;
    }

    public void setRequest_bodypart(String request_bodypart) {
        this.request_bodypart = request_bodypart;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getRequest_patient() {
        return request_patient;
    }

    public void setRequest_patient(String request_patient) {
        this.request_patient = request_patient;
    }

    public String getRequest_state() {
        return request_state;
    }

    public void setRequest_state(String request_state) {
        this.request_state = request_state;
    }

    public String getRequest_img() {
        return request_img;
    }

    public void setRequest_img(String request_img) {
        this.request_img = request_img;
    }
}
