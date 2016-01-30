/**
 * project2_Yidil_shuruil
 *
 * Ui_RegisterActivity:
 * When the user clicks “REGISTER” button, app will jump to the page shown below. This page asks
 * the user to input his/her email address, the password, and the confirm password. When the user
 * clicks “REGISTER” button, app will jump to the Login_Page and ask the user to login. If the user
 * clicks “BACK” button, app will jump back to the Home_Page.

 */
package com.example.apple.buyoldbooksunit3.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.custom_exception.CustomException;
import com.example.apple.buyoldbooksunit3.entities.user.Buyer;
import com.example.apple.buyoldbooksunit3.entities.user.Seller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ui_RegisterActivity extends Activity {

    /* Declarations of the EditText used */
    private EditText registerEmailEditView;
    private EditText registerPassEditText;
    private EditText registerCPassEditText;

    /*
     * onCreate:
     * Overrides the method of Activity. This page asks the user to input his/her email address,
     * the password, and the confirm password.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        registerEmailEditView=(EditText)findViewById(R.id.registerEmailEditView);
        registerPassEditText=(EditText)findViewById(R.id.registerPassEditText);
        registerCPassEditText=(EditText)findViewById(R.id.registerCPassEditText);
        Button reRegisterButton = (Button)findViewById(R.id.reRegisterButton);
        reRegisterButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean jumpflag=true;
                boolean flag=true;
                String emailAddress=registerEmailEditView.getText().toString();
                String password=registerPassEditText.getText().toString();
                String passwordC=registerCPassEditText.getText().toString();
                try{
                    if(emailAddress.equals("") ||password.equals("")||passwordC.equals("") ){
                        jumpflag=false;
                        CustomException e=new CustomException(1,"Missing Information!");
                        String str="Error "+e.getErrorno()+": "+e.getErrormsg().toString();
                        Timestamp t = new Timestamp(new Date().getTime());
                        writeToLog(str,t);
                        throw e;
                    }
                }catch(CustomException e){
                    e.fix(Ui_RegisterActivity.this,e.getErrorno(),e.getErrormsg());
                }
                if(jumpflag==true){
                    if(!password.equals(passwordC)){
                        flag=false;
                        AlertDialog dialog = new AlertDialog.Builder(Ui_RegisterActivity.this).setTitle("Done!")
                                .setMessage("The two passwords are not consistent!").create();
                        Window window = dialog.getWindow();
                        window.setGravity(Gravity.TOP);
                        dialog.show();
                    }
                    Buyer buyer=new Buyer();
                    Seller seller=new Seller();
                    if(buyer.register(getApplicationContext(),"Buyer","buyer",emailAddress,password)==0){
                        flag=false;
                        AlertDialog dialog = new AlertDialog.Builder(Ui_RegisterActivity.this).setTitle("Done!")
                                .setMessage("This email address has been registered!").create();
                        Window window = dialog.getWindow();
                        window.setGravity(Gravity.TOP);
                        dialog.show();
                    }
                    if(seller.register(getApplicationContext(),"Seller","seller",emailAddress,password)==0){
                        flag=false;
                        AlertDialog dialog = new AlertDialog.Builder(Ui_RegisterActivity.this).setTitle("Done!")
                                .setMessage("This email address has been registered!").create();
                        Window window = dialog.getWindow();
                        window.setGravity(Gravity.TOP);
                        dialog.show();
                    }
                    if(flag==true) {
                        Intent intent = new Intent();
                        intent.setClass(Ui_RegisterActivity.this, Ui_LoginActivity.class);
                        Ui_RegisterActivity.this.startActivity(intent);
                    }
                }

            }
        });
        Button reBackButton = (Button)findViewById(R.id.reBackButton);
        reBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Ui_RegisterActivity.this, Ui_MainActivity.class);
                Ui_RegisterActivity.this.startActivity(intent);
            }
        });

    }

    /*
     * writeToLog:
     * This method is used to record the error message into ExceptionLog.txt file.
     */
    public void writeToLog(String str, Timestamp t) {
        String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        Date date = new Date(t.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
        String time = sdf.format(date);
        String information = time + "---" + str;
        try {
            FileOutputStream fos = this.openFileOutput("exception_log.txt", Context.MODE_APPEND);
            fos.write(information.getBytes());
            fos.write("\n".getBytes());
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
