package com.doctor.mokhtari.scanner_doc.activities.LoginRegistration.login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.doctor.mokhtari.scanner_doc.R;
import com.doctor.mokhtari.scanner_doc.activities.base.BaseActivity;
import com.doctor.mokhtari.scanner_doc.activities.Main;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    LoginPresenter loginPresenter;
    @BindView(R.id.editText3)
    EditText activity_login_et_username;

    @BindView(R.id.editText4)
    EditText activity_login_et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        Toolbar toolbar;
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        TextView txttoolbar=(TextView)findViewById(R.id.txttoolbar);
        txttoolbar.setText("ورود");
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "font/vazirbold.ttf");
        txttoolbar.setTypeface(typeface3, Typeface.BOLD);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginPresenter = new LoginPresenter(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin2:
                loginPresenter.login(activity_login_et_username.getText().toString(), activity_login_et_password.getText().toString());

                // Toast.makeText(this, "Button Sign In clicked!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    public void loginSuccessful() {
        Intent intent = new Intent(LoginActivity.this, Main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle b = new Bundle();
        b.putInt("EXTRA_SESSION_ID", 2); //Your id
        intent.putExtras(b);
        startActivity(intent);
        this.finish();

    }
}
