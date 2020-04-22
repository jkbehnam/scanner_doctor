package com.doctor.mokhtari.scanner_doc.activities;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.doctor.mokhtari.scanner_doc.R;
import com.doctor.mokhtari.scanner_doc.activities.LoginRegistration.login.LoginActivity;
import com.doctor.mokhtari.scanner_doc.activities.base.BaseActivity;
import com.doctor.mokhtari.scanner_doc.activities.helper.PrefManager;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Main extends BaseActivity {
    public static String user_id;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        PrefManager pm = new PrefManager(this.getBaseContext());

        String session = pm.getUserDetails().get("session");
        if (pm.getUserDetails().get("name") .equals("")) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            this.finish();
        } else {
            user_id = pm.getUserDetails().get("u_id");
        }
        if (savedInstanceState == null) {

            Fragment fragment = Frag_request_list.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();}


    }


}
