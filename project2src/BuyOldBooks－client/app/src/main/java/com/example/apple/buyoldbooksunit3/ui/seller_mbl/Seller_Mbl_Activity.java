/**
 * Class: Seller_Mbl_Activity
 * This class Seller_Mbl_Activity extends the super class Activity, so this class will override the
 * method defined in the super class Activity. This class will implement the functionality of
 * managing the book list by a Seller.
 *
 * This class contains three Fragment: Seller_Mbl_MainFragment, Seller_Mbl_BookDetailsFragment and
 * the Seller_Mbl_UpdateInfoFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.seller_mbl;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.example.apple.buyoldbooksunit3.R;

public class Seller_Mbl_Activity extends Activity {

    //private instance variable
    private Seller_Mbl_MainFragment mSellerMbl_Main_Fragment;
    private Seller_Mbl_BookDetailsFragment mSellerMbl_BookDetails_Fragment;
    private Seller_Mbl_UpdateInfoFragment mSellerMbl_UpdateInfo_Fragment;
    private String roll;
    private String account;
    private String bookName;
    private String bookPrice;
    private String bookDepreciation;
    private String sellerName;
    private String bookDescription;
    private String payment;
    private String express;

    //getter method for private instance variable
    public Seller_Mbl_MainFragment getmSellerMbl_Main_Fragment() {
        return mSellerMbl_Main_Fragment;
    }

    //setter method for private instance variable
    public void setmSellerMbl_Main_Fragment(Seller_Mbl_MainFragment mSellerMbl_Main_Fragment) {
        this.mSellerMbl_Main_Fragment = mSellerMbl_Main_Fragment;
    }

    //getter method for private instance variable
    public Seller_Mbl_BookDetailsFragment getmSellerMbl_BookDetails_Fragment() {
        return mSellerMbl_BookDetails_Fragment;
    }

    //setter method for private instance variable
    public void setmSellerMbl_BookDetails_Fragment(Seller_Mbl_BookDetailsFragment mSellerMbl_BookDetails_Fragment) {
        this.mSellerMbl_BookDetails_Fragment = mSellerMbl_BookDetails_Fragment;
    }

    //getter method for private instance variable
    public Seller_Mbl_UpdateInfoFragment getmSellerMbl_UpdateInfo_Fragment() {
        return mSellerMbl_UpdateInfo_Fragment;
    }

    //setter method for private instance variable
    public void setmSellerMbl_UpdateInfo_Fragment(Seller_Mbl_UpdateInfoFragment mSellerMbl_UpdateInfo_Fragment) {
        this.mSellerMbl_UpdateInfo_Fragment = mSellerMbl_UpdateInfo_Fragment;
    }

    //getter method for private instance variable
    public String getRoll() {
        return roll;
    }

    //setter method for private instance variable
    public void setRoll(String roll) {
        this.roll = roll;
    }

    //getter method for private instance variable
    public String getAccount() {
        return account;
    }

    //setter method for private instance variable
    public void setAccount(String account) {
        this.account = account;
    }

    //getter method for private instance variable
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    //setter method for private instance variable
    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    //getter method for private instance variable
    public String getExpress() {
        return express;
    }

    //setter method for private instance variable
    public void setExpress(String express) {
        this.express = express;
    }

    //getter method for private instance variable
    public String getBookDepreciation() {
        return bookDepreciation;
    }

    //setter method for private instance variable
    public void setBookDepreciation(String bookDepreciation) {
        this.bookDepreciation = bookDepreciation;
    }

    //setter method for private instance variable
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    //getter method for private instance variable
    public String getBookDescription() {
        return bookDescription;
    }

    //setter method for private instance variable
    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    //getter method for private instance variable
    public String getPayment() {
        return payment;
    }

    //setter method for private instance variable
    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Override
    /*
     * onCreate method:
     * This method is overridden the onCreate method defined in the super class Activity.
     * This method can set the default fragment for this Seller_Mbl_Activity.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.seller_mbl_activity);
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
        Seller_Mbl_MainFragment mSellerMbl_Main_Fragment = new Seller_Mbl_MainFragment();
        transaction.replace(R.id.id_content, mSellerMbl_Main_Fragment);
        transaction.commit();
    }

}
