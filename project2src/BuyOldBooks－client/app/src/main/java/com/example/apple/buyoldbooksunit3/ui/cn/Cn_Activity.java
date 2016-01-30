/**
 * Class: Cn_Activity
 * This class Cn_Activity extends the super class Activity, so this class will override the
 * method defined in the super class Activity. This class will implement the functionaility of checking
 * a user's notice box and viewing the detailed information of a notice.
 *
 * This class contains two Fragment: Cn_MainFragment and Cn_NoticeDetailsFragment.
 *
 * Name: Shurui Li, Yidi Liu
 * andrewID: shuruil, yidil
 */
package com.example.apple.buyoldbooksunit3.ui.cn;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.example.apple.buyoldbooksunit3.R;
import java.util.Date;

public class Cn_Activity extends Activity {

    //private instance variable
    private Cn_MainFragment mCn_Main_Fragment;
    private Cn_NoticeDetailsFragment mCn_NoticeDetails_Fragment;
    private String roll;
    private String account;
    private String noticeContent;
    private String noticeSender;
    private String noticeReceiver;
    private String noticeSeller;
    private String noticeBuyer;
    private Date noticeDate;

    //getter method for the private instance variable
    public Cn_MainFragment getmCn_Main_Fragment() {
        return mCn_Main_Fragment;
    }

    //setter method for the private instance variable
    public void setmCn_Main_Fragment(Cn_MainFragment mCn_Main_Fragment) {
        this.mCn_Main_Fragment = mCn_Main_Fragment;
    }

    //getter method for the private instance variable
    public Cn_NoticeDetailsFragment getmCn_NoticeDetails_Fragment() {
        return mCn_NoticeDetails_Fragment;
    }

    //setter method for the private instance variable
    public void setmCn_NoticeDetails_Fragment(Cn_NoticeDetailsFragment mCn_NoticeDetails_Fragment) {
        this.mCn_NoticeDetails_Fragment = mCn_NoticeDetails_Fragment;
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
    public String getNoticeContent() {
        return noticeContent;
    }

    //setter method for the private instance variable
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    //getter method for the private instance variable
    public String getNoticeSender() {
        return noticeSender;
    }

    //setter method for the private instance variable
    public void setNoticeSender(String noticeSender) {
        this.noticeSender = noticeSender;
    }

    //setter method for the private instance variable
    public void setNoticeReceiver(String noticeReceiver) {
        this.noticeReceiver = noticeReceiver;
    }

    //setter method for the private instance variable
    public void setNoticeSeller(String noticeSeller) {
        this.noticeSeller = noticeSeller;
    }

    //setter method for the private instance variable
    public void setNoticeBuyer(String noticeBuyer) {
        this.noticeBuyer = noticeBuyer;
    }

    //getter method for the private instance variable
    public Date getNoticeDate() {
        return noticeDate;
    }

    //setter method for the private instance variable
    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    @Override
    /*
     * onCreate method:
     * This method is overriden the onCreate method defined in the super class Activitiy.
     * This method can set the default fragment for this Cn_Activity.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cn_activity);
        setDefaultFragment();
    }

    /*
     * setDefaultFragment method:
     * This method is the helper method to set the default Fragment of this Cn_Activity.
     */
    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Cn_MainFragment mCn_Main_Fragment = new Cn_MainFragment();
        transaction.replace(R.id.id_content, mCn_Main_Fragment);
        transaction.commit();
    }
}
