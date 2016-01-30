/**
 * Class: Buyer_HomeActivity
 * The class Buyer_HomeActivity extends the super class Activity, so this class will override the
 * methods defined in the super class Activity. This class will implement the functionality of
 * showing the main menu of the Buyer Account.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.buyer_home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.ui.Ui_LogoutActivity;
import com.example.apple.buyoldbooksunit3.ui.buyer_cart.Buyer_Cart_Activity;
import com.example.apple.buyoldbooksunit3.ui.buyer_search.Buyer_Search_Activity;
import com.example.apple.buyoldbooksunit3.ui.epi.Epi_Activity;
import com.example.apple.buyoldbooksunit3.ui.cn.Cn_Activity;
import com.example.apple.buyoldbooksunit3.ui.vol.Vol_Activity;

public class Buyer_HomeActivity extends Activity {

    //private instance variable
    private Bundle bundlelast;
    private Bundle bundle;
    private String roll;
    private String account;
    private Button editButton;
    private Button searchButton;
    private Button orderButton;
    private Button noticeButton;
    private Button cartButton;
    private Button buyerLogout;

    @Override
    /*
     * onCreate method:
     * This method is overriden the onCreate method defined in the super class Activity.
     * This method can listen on the different buttons to jump to the different relative page.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_home_activity);
        bundlelast = getIntent().getExtras();
        roll = bundlelast.getString("roll");
        account = bundlelast.getString("account");
        bundle = new Bundle();
        bundle.putString("roll", roll);
        bundle.putString("account",account);
        editButton=(Button)findViewById(R.id.editbuyerbutton);
        searchButton=(Button)findViewById(R.id.searchbutton);
        orderButton=(Button)findViewById(R.id.buyerorderbutton);
        noticeButton=(Button)findViewById(R.id.buyernoticebutton);
        cartButton=(Button)findViewById(R.id.cartbutton);
        buyerLogout = (Button)findViewById(R.id.buyerLogoutButton);

        //listen on the "EDIT PERSONNEL INFORMATION" button
        editButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Buyer_HomeActivity.this, Epi_Activity.class);
                intent.putExtras(bundle);
                Buyer_HomeActivity.this.startActivity(intent);

            }
        });

        //listen on the "SEARCH BOOK" button
        searchButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Buyer_HomeActivity.this, Buyer_Search_Activity.class);
                intent.putExtras(bundle);
                Buyer_HomeActivity.this.startActivity(intent);

            }
        });

        //listen on the "VIEW ORDER LIST" button
        orderButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Buyer_HomeActivity.this, Vol_Activity.class);
                intent.putExtras(bundle);
                Buyer_HomeActivity.this.startActivity(intent);

            }
        });

        //listen on the "CHECK NOTICE" button
        noticeButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Buyer_HomeActivity.this, Cn_Activity.class);
                intent.putExtras(bundle);
                Buyer_HomeActivity.this.startActivity(intent);
            }
        });

        //listen on the "VIEW CART" button
        cartButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Buyer_HomeActivity.this, Buyer_Cart_Activity.class);
                intent.putExtras(bundle);
                Buyer_HomeActivity.this.startActivity(intent);

            }
        });

        //listen on the "LOGOUT" button
        buyerLogout.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(Buyer_HomeActivity.this, Ui_LogoutActivity.class);
                Buyer_HomeActivity.this.startActivity(intent);

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
        getMenuInflater().inflate(R.menu.menu_buyer_home, menu);
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
