/**
 * Class: Vol_Activity
 * This class Vol_Activity extends the super class Activity, so this class will override the
 * method defined in the super class Activity. This class will implement the functionality of
 * checking all the order a User has recently.
 *
 * This class contains three Fragment: Vol_MainFragment, Vol_OrderDetailsFragment and
 * the Vol_SellerOrderDetailsFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.vol;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.example.apple.buyoldbooksunit3.R;
import java.util.Date;

public class Vol_Activity extends Activity {

    //private instance variable
    private Vol_MainFragment mVol_Main_Fragment;
    private Vol_SellerOrderDetailsFragment mVol_Seller_OrderDetails_Fragment;
    private Vol_OrderDetailsFragment mVol_Buyer_OrderDetails_Fragment;
    private String roll;
    private String account;
    private int orderNumber;
    private String orderSeller;
    private String orderBuyer;
    private float orderTotalPrice;
    private String orderBook;
    private Date orderDate;
    private String bookDepreciation;
    private String paymentMethod;
    private String expressMethod;
    private String orderState;

    //getter method for the private instance variable
    public Vol_MainFragment getmVol_Main_Fragment() {
        return mVol_Main_Fragment;
    }

    //getter method for the private instance variable
    public void setmVol_Main_Fragment(Vol_MainFragment mVol_Main_Fragment) {
        this.mVol_Main_Fragment = mVol_Main_Fragment;
    }

    //getter method for the private instance variable
    public Vol_SellerOrderDetailsFragment getmVol_Seller_OrderDetails_Fragment() {
        return mVol_Seller_OrderDetails_Fragment;
    }

    //getter method for the private instance variable
    public void setmVol_Seller_OrderDetails_Fragment(Vol_SellerOrderDetailsFragment mVol_Seller_OrderDetails_Fragment) {
        this.mVol_Seller_OrderDetails_Fragment = mVol_Seller_OrderDetails_Fragment;
    }

    //getter method for the private instance variable
    public Vol_OrderDetailsFragment getmVol_Buyer_OrderDetails_Fragment() {
        return mVol_Buyer_OrderDetails_Fragment;
    }

    //getter method for the private instance variable
    public void setmVol_Buyer_OrderDetails_Fragment(Vol_OrderDetailsFragment mVol_Buyer_OrderDetails_Fragment) {
        this.mVol_Buyer_OrderDetails_Fragment = mVol_Buyer_OrderDetails_Fragment;
    }

    //getter method for the private instance variable
    public String getRoll() {
        return roll;
    }

    //getter method for the private instance variable
    public void setRoll(String roll) {
        this.roll = roll;
    }

    //getter method for the private instance variable
    public String getAccount() {
        return account;
    }

    //getter method for the private instance variable
    public void setAccount(String account) {
        this.account = account;
    }

    //getter method for the private instance variable
    public int getOrderNumber() {
        return orderNumber;
    }

    //getter method for the private instance variable
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    //getter method for the private instance variable
    public String getOrderSeller() {
        return orderSeller;
    }

    //getter method for the private instance variable
    public void setOrderSeller(String orderSeller) {
        this.orderSeller = orderSeller;
    }

    //getter method for the private instance variable
    public String getOrderBuyer() {
        return orderBuyer;
    }

    //getter method for the private instance variable
    public void setOrderBuyer(String orderBuyer) {
        this.orderBuyer = orderBuyer;
    }

    //getter method for the private instance variable
    public float getOrderTotalPrice() {
        return orderTotalPrice;
    }

    //getter method for the private instance variable
    public void setOrderTotalPrice(float orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    //getter method for the private instance variable
    public String getOrderBook() {
        return orderBook;
    }

    //getter method for the private instance variable
    public void setOrderBook(String orderBook) {
        this.orderBook = orderBook;
    }

    //getter method for the private instance variable
    public Date getOrderDate() {
        return orderDate;
    }

    //getter method for the private instance variable
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    //getter method for the private instance variable
    public String getBookDepreciation() {
        return bookDepreciation;
    }

    //getter method for the private instance variable
    public void setBookDepreciation(String bookDepreciation) {
        this.bookDepreciation = bookDepreciation;
    }

    //getter method for the private instance variable
    public String getPaymentMethod() {
        return paymentMethod;
    }

    //getter method for the private instance variable
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    //getter method for the private instance variable
    public String getExpressMethod() {
        return expressMethod;
    }

    //getter method for the private instance variable
    public void setExpressMethod(String expressMethod) {
        this.expressMethod = expressMethod;
    }

    @Override
    /*
     * onCreate method:
     * This method is overridden the onCreate method defined in the super class Activity.
     * This method can set the default fragment for this Vol_Activity.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vol_activity);
        setDefaultFragment();
    }

    /*
     * setDefaultFragment method:
     * This method is the helper method to set the default Fragment of this Seller_Mbl_Activity.
     */
    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Vol_MainFragment mVol_Main_Fragment = new Vol_MainFragment();
        transaction.replace(R.id.id_content, mVol_Main_Fragment);
        transaction.commit();
    }



}
