/**
 * project2_Yidil_shuruil:
 *
 * Ui_MainActivity:
 * When a user first open this application, the page shown below will show up and the user can
 * choose to login or to register buy clicking  “LOGIN” button or “REGISTER” button.
 */
package com.example.apple.buyoldbooksunit3.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.entities.user.Administrator;

public class Ui_MainActivity extends Activity {

    /*
     * onCreate:
     * Overrides the method of Activity. On this page, the user can choose to login or to register
     * buy clicking  “LOGIN” button or “REGISTER” button.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Administrator admin = new Administrator();
        admin.createDatebase(Ui_MainActivity.this);
        Button mainLoginButton = (Button)findViewById(R.id.mainLoginButton);
        mainLoginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Ui_MainActivity.this, Ui_LoginActivity.class);
                Ui_MainActivity.this.startActivity(intent);
            }
        });
        Button mainRegisterButton = (Button)findViewById(R.id.mainRegisterButton);
        mainRegisterButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Ui_MainActivity.this,Ui_RegisterActivity.class);
                Ui_MainActivity.this.startActivity(intent);
            }
        });
    }

}

