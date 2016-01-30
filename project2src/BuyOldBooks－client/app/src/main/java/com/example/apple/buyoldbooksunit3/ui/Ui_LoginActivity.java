/**
 * project2_Yidil_shuruil
 *
 * Ui_LoginActivity:
 * When the user clicks “LOGIN” button at Home_Page or the user has just registered, the app will
 * jump to the Loin_Page shown below. It asks the user to input his/her email address and password
 * and select to login as a Seller or a Buyer. After the user clicking “LOGIN” button, the page will
 * jump to the related account home page. If the user clicks “BACK” button, the page will jump back
 *to the Home_Page.
 */

package com.example.apple.buyoldbooksunit3.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.custom_exception.CustomException;
import com.example.apple.buyoldbooksunit3.entities.user.Buyer;
import com.example.apple.buyoldbooksunit3.entities.user.Seller;
import com.example.apple.buyoldbooksunit3.ui.buyer_home.Buyer_HomeActivity;
import com.example.apple.buyoldbooksunit3.ui.seller_home.Seller_Home_Activity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Ui_LoginActivity extends Activity {

    /* Declarations of components used */
    private Bundle bundle;
    private EditText emailEditText;
    private EditText passWordEditText;
    private RadioButton sellerRadioButton;
    private RadioButton buyerRadioButton;
    private boolean jumpflag;

    /*
     * onCreate:
     * This method overrides the method of Activity.
     * It asks the user to input his/her email address and password and select to login as a
     * Seller or a Buyer. After the user clicking “LOGIN” button, the page will jump to the
     * related account home page.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        bundle = new Bundle();
        sellerRadioButton = (RadioButton)findViewById(R.id.sellerRadioButton);
        buyerRadioButton = (RadioButton)findViewById(R.id.buyerRadioButton);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passWordEditText = (EditText)findViewById(R.id.passWordEditText);
        Button loginLoginButton = (Button)findViewById(R.id.loginLoginButton);
        loginLoginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpflag=true;
                String eMail = emailEditText.getText().toString();
                String pwd = passWordEditText.getText().toString();
                try{
                    if(eMail.equals("") || pwd.equals("")){
                        jumpflag=false;
                        CustomException e=new CustomException(1,"Missing Information!");
                        String str="Error "+e.getErrorno()+": "+e.getErrormsg().toString();
                        Timestamp t = new Timestamp(new Date().getTime());
                        writeToLog(str,t);
                        throw e;
                    }
                }catch(CustomException e){
                    e.fix(Ui_LoginActivity.this,e.getErrorno(),e.getErrormsg());
                }
                if(jumpflag==true) {
                    bundle.putString("account", eMail);
                    try {
                        if (!sellerRadioButton.isChecked() && !buyerRadioButton.isChecked()) {
                            //jumpflag = false;
                            CustomException e = new CustomException(2, "Please select your identity!");
                            String str = "Error " + e.getErrorno() + ": " + e.getErrormsg().toString();
                            Timestamp t = new Timestamp(new Date().getTime());
                            writeToLog(str, t);
                            throw e;

                        } else if (sellerRadioButton.isChecked()) {
                            bundle.putString("roll", "seller");
                            Seller seller = new Seller();
                            int flag = seller.login(Ui_LoginActivity.this, eMail, pwd, "seller");
                            if (flag == 1) {
                                AlertDialog dialog = new AlertDialog.Builder(Ui_LoginActivity.this).
                                        setTitle("Sorry!").setMessage("Account not exist").create();
                                Window window = dialog.getWindow();
                                window.setGravity(Gravity.TOP);
                                dialog.show();
                            } else if (flag == 2) {
                                AlertDialog dialog = new AlertDialog.Builder(Ui_LoginActivity.this).
                                        setTitle("Sorry!").setMessage("Wrong passward").create();
                                Window window = dialog.getWindow();
                                window.setGravity(Gravity.TOP);
                                dialog.show();
                            } else {
                                Intent intent = new Intent();
                                intent.setClass(Ui_LoginActivity.this, Seller_Home_Activity.class);
                                intent.putExtras(bundle);
                                Ui_LoginActivity.this.startActivity(intent);
                            }
                        } else if (buyerRadioButton.isChecked()) {
                            bundle.putString("roll", "buyer");
                            Buyer buyer = new Buyer();
                            int flag = buyer.login(Ui_LoginActivity.this, eMail, pwd, "buyer");
                            if (flag == 1) {
                                AlertDialog dialog = new AlertDialog.Builder(Ui_LoginActivity.this).
                                        setTitle("Sorry!").setMessage("Account not exist").create();
                                Window window = dialog.getWindow();
                                window.setGravity(Gravity.TOP);
                                dialog.show();
                            } else if (flag == 2) {
                                AlertDialog dialog = new AlertDialog.Builder(Ui_LoginActivity.this).
                                        setTitle("Sorry!").setMessage("Wrong passward").create();
                                Window window = dialog.getWindow();
                                window.setGravity(Gravity.TOP);
                                dialog.show();
                            } else {
                                Intent intent = new Intent();
                                intent.setClass(Ui_LoginActivity.this, Buyer_HomeActivity.class);
                                intent.putExtras(bundle);
                                Ui_LoginActivity.this.startActivity(intent);
                            }
                        }
                    } catch (CustomException e) {
                        e.fix(Ui_LoginActivity.this, e.getErrorno(), e.getErrormsg());
                    }
                }

            }
        });
        Button loginBackButton = (Button)findViewById(R.id.loginBackButton);
        loginBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Ui_LoginActivity.this, Ui_MainActivity.class);
                Ui_LoginActivity.this.startActivity(intent);
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
