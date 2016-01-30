/**
 * project2_Yidil_shuruil
 *
 * Ui_LogoutActivity:
 * If user click "Logout" button, the page will jump to the Logout page
 */
package com.example.apple.buyoldbooksunit3.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.apple.buyoldbooksunit3.R;

import java.util.Timer;
import java.util.TimerTask;

public class Ui_LogoutActivity extends Activity {

    /*
     * onCreate:
     * This method used jump to the Logout page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_activity);
        Timer timer=new Timer();
        TimerTask task=new TimerTask(){
            public void run(){
                Intent intent = new Intent();
                intent.setClass(Ui_LogoutActivity.this, Ui_LoginActivity.class);
                Ui_LogoutActivity.this.startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        };
        timer.schedule(task, 2000);
    }
}
