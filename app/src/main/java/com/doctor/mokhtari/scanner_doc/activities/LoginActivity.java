package com.doctor.mokhtari.scanner_doc.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.doctor.mokhtari.scanner_doc.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Toolbar toolbar;
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        TextView txttoolbar=(TextView)findViewById(R.id.txttoolbar);
        txttoolbar.setText("ورود");
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "font/vazirbold.ttf");
        txttoolbar.setTypeface(typeface3, Typeface.BOLD);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin2:
                Intent i=new Intent(this, Main.class);
                startActivity(i);
                // Toast.makeText(this, "Button Sign In clicked!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
