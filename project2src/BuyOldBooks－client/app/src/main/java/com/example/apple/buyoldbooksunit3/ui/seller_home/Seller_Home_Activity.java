/**
 * Class: Seller_Home_Activity
 * The class Seller_Home_Activity extends the super class Activity, so this class will override the
 * methods defined in the super class Activity. This class will implement the functionality of
 * showing the main menu of the Seller Account.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.seller_home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.ui.Ui_LogoutActivity;
import com.example.apple.buyoldbooksunit3.ui.cn.Cn_Activity;
import com.example.apple.buyoldbooksunit3.ui.epi.Epi_Activity;
import com.example.apple.buyoldbooksunit3.ui.seller_mbl.Seller_Mbl_Activity;
import com.example.apple.buyoldbooksunit3.ui.seller_unb.Seller_Unb_Activity;
import com.example.apple.buyoldbooksunit3.ui.vol.Vol_Activity;


public class Seller_Home_Activity extends Activity {

    //private instance variable
    private Bundle bundle;
    private Bundle bundlelast;
    private String roll;
    private String account;

    @Override
    /*
     * onCreate method:
     * This method is overriden the onCreate method defined in the super class Activity.
     * This method can listen on the different buttons to jump to the different relative page.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_home_activity);

        bundlelast = getIntent().getExtras();
        roll = bundlelast.getString("roll");
        account = bundlelast.getString("account");
        bundle = new Bundle();
        bundle.putString("account",account);
        bundle.putString("roll",roll);
        Button sHomeEPI = (Button)findViewById(R.id.sHomeEPI);
        Button sHomeMBL = (Button)findViewById(R.id.sHomeMBL);
        Button sHomeUNB = (Button)findViewById(R.id.sHomeUNB);
        Button sHomeCN = (Button)findViewById(R.id.sHomeCN);
        Button sHomeVOL = (Button)findViewById(R.id.sHomeVOL);

        //listen on the "EDIT PERSONNEL INFORMATION" button
        sHomeEPI.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent();
                intent.setClass(Seller_Home_Activity.this, Epi_Activity.class);
                intent.putExtras(bundle);
                Seller_Home_Activity.this.startActivity(intent);
            }
        });

        //listen on the "MANAGE BOOK LIST" button
        sHomeMBL.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Seller_Home_Activity.this, Seller_Mbl_Activity.class);
                intent.putExtras(bundle);
                Seller_Home_Activity.this.startActivity(intent);
            }
        });

        //listen on the "UPLOAD NEW BOOK" button
        sHomeUNB.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Seller_Home_Activity.this, Seller_Unb_Activity.class);
                intent.putExtras(bundle);
                Seller_Home_Activity.this.startActivity(intent);
            }
        });

        //listen on the "CHECK NOTICE" button
        sHomeCN.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Seller_Home_Activity.this, Cn_Activity.class);
                intent.putExtras(bundle);
                Seller_Home_Activity.this.startActivity(intent);
            }
        });

        //listen on the "VIEW ORDER LIST" button
        sHomeVOL.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Seller_Home_Activity.this, Vol_Activity.class);
                intent.putExtras(bundle);
                Seller_Home_Activity.this.startActivity(intent);
            }
        });

        //listen on the "LOGOUT" button
        Button sHomeLogout = (Button)findViewById(R.id.sHomeLogout);
        sHomeLogout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Seller_Home_Activity.this, Ui_LogoutActivity.class);
                Seller_Home_Activity.this.startActivity(intent);
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
        getMenuInflater().inflate(R.menu.menu_seller_home, menu);
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
}
