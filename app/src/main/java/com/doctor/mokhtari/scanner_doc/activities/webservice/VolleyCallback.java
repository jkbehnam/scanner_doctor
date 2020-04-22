package com.doctor.mokhtari.scanner_doc.activities.webservice;

import org.json.JSONException;

public interface VolleyCallback{
    void onSuccess(String result) throws JSONException;
}