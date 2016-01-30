/**
 * Class: Epi_Activity
 * This class Epi_Activity extends the super class Activity, so this class will override the
 * method defined in the super class Activity. This class will implement the functionaility of
 * update the personnel information of a user.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.epi;

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
import com.example.apple.buyoldbooksunit3.ui.buyer_home.Buyer_HomeActivity;
import com.example.apple.buyoldbooksunit3.ui.seller_home.Seller_Home_Activity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Epi_Activity extends Activity {

    //private instance variable
    private Bundle bundlelast;
    private Bundle bundle;
    private String roll;
    private String account;
    private EditText epiNNEditText;
    private RadioButton epiGenderMRadioButton;
    private RadioButton epiGenderFRadioButton;
    private EditText epiCPNEditText;
    private EditText epiHAEditText;
    private boolean jumpflag;

    @Override
    /*
     * onCreate method:
     * This method is overriden the onCreate method defined in the super class Activity.
     * This method can listen on different buttons to implement the functionality of updating the
     * personnel information of a user.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.epi_activity);
        jumpflag=true;
        bundlelast = this.getIntent().getExtras();
        roll = bundlelast.getString("roll");
        account = bundlelast.getString("account");
        bundle = new Bundle();
        bundle.putString("roll",roll);
        bundle.putString("account",account);
        epiNNEditText = (EditText)findViewById(R.id.epiNNEditText);
        epiGenderMRadioButton = (RadioButton) findViewById(R.id.epiGenderMRadioButton);
        epiGenderFRadioButton = (RadioButton) findViewById(R.id.epiGenderFRadioButton);
        epiCPNEditText = (EditText) findViewById(R.id.epiCPNEditText);
        epiHAEditText = (EditText) findViewById(R.id.epiHAEditText);
        Button epiDoneButton = (Button)findViewById(R.id.epiDoneButton);
        Button epiCancelButton = (Button)findViewById(R.id.epiCancelButton);

        //listen on the "DONE" button
        epiDoneButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = epiNNEditText.getText().toString();
                String gender = "";
                if (epiGenderMRadioButton.isChecked()) {
                    gender = "M";
                } else {
                    gender = "F";
                }
                String cellPhone = epiCPNEditText.getText().toString();
                String homeAddress = epiHAEditText.getText().toString();
                if (roll.equals("seller")) {
                    Seller seller = new Seller();
                    seller.updateInfo(Epi_Activity.this, account, nickName, gender, cellPhone,
                            homeAddress, roll);
                } else {
                    Buyer buyer = new Buyer();
                    buyer.updateInfo(Epi_Activity.this, account, nickName, gender, cellPhone,
                            homeAddress, roll);
                }
                try {
                    if (nickName.equals("") || gender.equals("") || cellPhone.equals("") || homeAddress.equals("")) {
                        jumpflag = false;
                        CustomException e = new CustomException(1, "Missing Information!");
                        String str = "Error " + e.getErrorno() + ": " + e.getErrormsg().toString();
                        Timestamp t = new Timestamp(new Date().getTime());
                        writeToLog(str, t);
                        throw e;
                    }

                } catch (CustomException e) {
                    e.fix(Epi_Activity.this, e.getErrorno(), e.getErrormsg());
                }
                if (jumpflag == true) {
                    AlertDialog dialog = new AlertDialog.Builder(Epi_Activity.this).
                            setTitle("Done!").setMessage("Update your Personnel Infomation successfully")
                            .create();
                    Window window = dialog.getWindow();
                    window.setGravity(Gravity.TOP);
                    dialog.show();
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        public void run() {
                            Intent intent = new Intent();
                            if (roll.equals("seller")) {
                                intent.setClass(Epi_Activity.this, Seller_Home_Activity.class);
                            } else {
                                intent.setClass(Epi_Activity.this, Buyer_HomeActivity.class);
                            }
                            intent.putExtras(bundle);
                            Epi_Activity.this.startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    };
                    timer.schedule(task, 2000);

                }
            }
        });

        //listen on the "CANCEL" button
        epiCancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                if(roll.equals("seller")){
                    intent.setClass(Epi_Activity.this, Seller_Home_Activity.class);
                }else{
                    intent.setClass(Epi_Activity.this, Buyer_HomeActivity.class);
                }
                intent.putExtras(bundle);
                Epi_Activity.this.startActivity(intent);
            }
        });
    }

    @Override
    /*
     * onCreateOptionsMenu method:
     * This method is overridden the onCreateOptionsMenu method defined in the super class Activity.
     */
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_epi, menu);
        return true;
    }

    @Override
    /*
     * onOptionsItemSelected method:
     * This method is overridden the onOptionsItemSelected method defined in the super class Activity.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
