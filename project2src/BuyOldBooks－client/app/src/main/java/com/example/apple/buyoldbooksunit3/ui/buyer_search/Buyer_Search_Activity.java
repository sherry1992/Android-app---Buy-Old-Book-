/**
 * Class: Buyer_Search_Activity
 * This class Buyer_Search_Activity extends the super class Activity, so this class will override the
 * method defined in the super class Activity. This class will implement the functionality of
 * searching a book, viewing the detailed information of that particular book and searching a book
 * by voice.
 *
 * This class contains three Fragment: Buyer_Search_MainFragment, Buyer_Search_ResultFragment
 * and Buyer_Search_BookDetailsFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.buyer_search;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.apple.buyoldbooksunit3.R;

public class Buyer_Search_Activity extends Activity {

    //private instance variable
    private Buyer_Search_MainFragment mBuyer_Search_Main_Fragment;
    private Buyer_Search_ResultFragment mBuyer_Search_Result_Fragment;
    private Buyer_Search_BookDetailsFragment mBuyer_Book_Details_Fragment;
    private String roll;
    private String account;
    private String keyword;
    private String sortmethod;
    private String bookSelected;
    private String bookPrice;
    private String bookDepreciation;
    private String sellerName;
    private String bookDescription;
    private String payment;
    private String express;

    //getter method for the private instance variable
    public Buyer_Search_MainFragment getmBuyer_Search_Main_Fragment() {
        return mBuyer_Search_Main_Fragment;
    }

    //setter method for the private instance variable
    public void setmBuyer_Search_Main_Fragment(Buyer_Search_MainFragment mBuyer_Search_Main_Fragment) {
        this.mBuyer_Search_Main_Fragment = mBuyer_Search_Main_Fragment;
    }

    //getter method for the private instance variable
    public Buyer_Search_ResultFragment getmBuyer_Search_Result_Fragment() {
        return mBuyer_Search_Result_Fragment;
    }

    //setter method for the private instance variable
    public void setmBuyer_Search_Result_Fragment(Buyer_Search_ResultFragment mBuyer_Search_Result_Fragment) {
        this.mBuyer_Search_Result_Fragment = mBuyer_Search_Result_Fragment;
    }

    //getter method for the private instance variable
    public Buyer_Search_BookDetailsFragment getmBuyer_Book_Details_Fragment() {
        return mBuyer_Book_Details_Fragment;
    }

    //setter method for the private instance variable
    public void setmBuyer_Book_Details_Fragment(Buyer_Search_BookDetailsFragment mBuyer_Book_Details_Fragment) {
        this.mBuyer_Book_Details_Fragment = mBuyer_Book_Details_Fragment;
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
    public String getKeyword() {
        return keyword;
    }

    //setter method for the private instance variable
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    //getter method for the private instance variable
    public String getSortmethod() {
        return sortmethod;
    }

    //setter method for the private instance variable
    public void setSortmethod(String sortmethod) {
        this.sortmethod = sortmethod;
    }

    //getter method for the private instance variable
    public String getBookSelected() {
        return bookSelected;
    }

    //setter method for the private instance variable
    public void setBookSelected(String bookNameSelected) {
        this.bookSelected = bookNameSelected;
    }

    //getter method for the private instance variable
    public String getBookPrice() {
        return bookPrice;
    }

    //setter method for the private instance variable
    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    //getter method for the private instance variable
    public String getBookDepreciation() {
        return bookDepreciation;
    }

    //setter method for the private instance variable
    public void setBookDepreciation(String bookDepreciation) {
        this.bookDepreciation = bookDepreciation;
    }

    //getter method for the private instance variable
    public String getBookDescription() {
        return bookDescription;
    }

    //setter method for the private instance variable
    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    //getter method for the private instance variable
    public String getPayment() {
        return payment;
    }

    //setter method for the private instance variable
    public void setPayment(String payment) {
        this.payment = payment;
    }

    //getter method for the private instance variable
    public String getExpress() {
        return express;
    }

    //setter method for the private instance variable
    public void setExpress(String express) {
        this.express = express;
    }

    @Override
    /*
     * onCreate method:
     * This method is overriden the onCreate method defined in the super class Activitiy.
     * This method can set the default fragment for this Buyer_Search_Activity.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_search_activity);
        setDefaultFragment();
    }

    /*
     * setDefaultFragment method:
     * This method is the helper method to set the default Fragment of this Buyer_Search_Activity.
     */
    public void setDefaultFragment(){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Buyer_Search_MainFragment mBuyer_Search_Main_Fragment=new Buyer_Search_MainFragment();
        transaction.replace(R.id.id_content,mBuyer_Search_Main_Fragment);
        transaction.commit();
    }

    @Override
    /*
     * onCreateOptionsMenu method:
     * This method is overridden the onCreateOptionsMenu method defined in the super class Activity.
     */
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buyer_search, menu);
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
