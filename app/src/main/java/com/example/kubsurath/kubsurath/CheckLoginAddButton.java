package com.example.kubsurath.kubsurath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by dinesh on 12-Apr-15.
 */
public class CheckLoginAddButton extends Activity {
    TextView notNow;
    Button btSignup;
    Button btLogin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpostview);
        notNow = (TextView)findViewById(R.id.NotNow);
        btSignup = (Button)findViewById(R.id.btnSignin);
        btLogin = (Button)findViewById(R.id.Login_btn);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckLoginAddButton.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckLoginAddButton.this,
                        RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        notNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckLoginAddButton.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
