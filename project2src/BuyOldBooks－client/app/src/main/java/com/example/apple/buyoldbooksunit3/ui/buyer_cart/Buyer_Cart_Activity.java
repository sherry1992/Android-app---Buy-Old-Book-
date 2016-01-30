/**
 * Class: Buyer_Cart_Activity
 * This class Buyer_Cart_Activity extends the super class Activity, so this class will override the
 * method defined in the super class Activity. This class will implement the operation of viewing
 * the cart book list and createing a new order for a Buyer.
 *
 * This class contains two Fragment: Buyer_Cart_MainFragment and Buyer_Cart_CreateOrderFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.buyer_cart;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.apple.buyoldbooksunit3.R;
import com.example.apple.buyoldbooksunit3.entities.book.BookList;

public class Buyer_Cart_Activity extends Activity {

    //private instance variable
    private Buyer_Cart_MainFragment mBuyer_Cart_Main_Fragment;
    private Buyer_Cart_CreateOrderFragment mBuyer_Create_Order_Fragment;
    private String roll;
    private String account;
    private BookList cartList;
    private String cart;

    //getter method for the private instance variable
    public Buyer_Cart_MainFragment getmBuyer_Cart_Main_Fragment() {
        return mBuyer_Cart_Main_Fragment;
    }

    public void setmBuyer_Cart_Main_Fragment(Buyer_Cart_MainFragment mBuyer_Cart_Main_Fragment) {
        this.mBuyer_Cart_Main_Fragment = mBuyer_Cart_Main_Fragment;
    }

    //getter method for the private instance variable
    public Buyer_Cart_CreateOrderFragment getmBuyer_Create_Order_Fragment() {
        return mBuyer_Create_Order_Fragment;
    }

    //setter method for the private instance variable
    public void setmBuyer_Create_Order_Fragment(Buyer_Cart_CreateOrderFragment mBuyer_Create_Order_Fragment) {
        this.mBuyer_Create_Order_Fragment = mBuyer_Create_Order_Fragment;
    }

    //getter method for the private instance variable
    public String getRoll() {
        return roll;
    }

    //setter method for the private instance variable
    public void setRoll(String roll) {
        this.roll = roll;
    }

    //getter method for the private instance variable
    public String getAccount() {
        return account;
    }

    //setter method for the private instance variable
    public void setAccount(String account) {
        this.account = account;
    }

    //getter method for the private instance variable
    public String getCart() {
        return cart;
    }

    //setter method for the private instance variable
    public void setCart(String cart) {
        this.cart = cart;
    }

    //getter method for the private instance variable
    public BookList getCartList() {
        return cartList;
    }

    //setter method for the private instance variable
    public void setCartList(BookList cartList) {
        this.cartList = cartList;
    }

    @Override
    /*
     * onCreate method:
     * This method is overriden the onCreate method defined in the super class Activitiy.
     * This method can set the default fragment for this Buyer_Cart_Activity.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_cart_activity);
        setDefaultFragment();
    }

    /*
     * setDefaultFragment method:
     * This method is the helper method to set the default Fragment of this Buyer_Cart_Activity.
     */
    public void setDefaultFragment(){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mBuyer_Cart_Main_Fragment=new Buyer_Cart_MainFragment();
        transaction.replace(R.id.id_content, mBuyer_Cart_Main_Fragment);
        transaction.commit();
    }

    @Override
    /*
     * onCreateOptionsMenu method:
     * This method is overridden the onCreateOptionsMenu method defined in the super class Activity.
     */
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buyer_cart, menu);
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
